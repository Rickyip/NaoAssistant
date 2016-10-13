package com.yipkaming.naoassistant.activity;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.service.notification.StatusBarNotification;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.helper.proxies.ALTextToSpeech;
import com.yipkaming.naoassistant.helper.NotificationMonitor;
import com.yipkaming.naoassistant.R;
import com.yipkaming.naoassistant.model.Config;
import com.yipkaming.naoassistant.model.Naoqi;
import com.yipkaming.naoassistant.model.VerbalReminder;


public class AssistantActivity extends AppCompatActivity {

    private static final String TAG = Config.TAG;
    private static final String TAG_PRE = Config.getSimpleName(AssistantActivity.class);

    private static final int EVENT_SHOW_CREATE_NOS = 0;
    private static final int EVENT_LIST_CURRENT_NOS = 1;

    private static final String ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";
    private static final String ACTION_NOTIFICATION_LISTENER_SETTINGS = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS";
    private boolean isEnabled = false;
    private TextView mTextView;

    NotificationManager manager;
    ALTextToSpeech alTextToSpeech;


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case EVENT_SHOW_CREATE_NOS:
                    showCreateNotification();
                    break;
                case EVENT_LIST_CURRENT_NOS:
                    listCurrentNotification();
                    break;

                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assistant);
        mTextView = (TextView) findViewById(R.id.textView);

        manager= (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isEnabled = isEnabled();
        logE("isEnabled = " + isEnabled);
        if (!isEnabled) {
            showConfirmDialog();
        }
    }

    public void buttonOnClicked(View view) {
        mTextView.setTextColor(Color.BLACK);
        switch (view.getId()) {
            case R.id.btnCreateNotify:
                logE("Create notifications...");
                createNotification(this);
                mHandler.sendMessageDelayed(mHandler.obtainMessage(EVENT_SHOW_CREATE_NOS), 50);
                break;
            case R.id.btnClearLastNotify:
                logE("Clear Last notification...");
                clearLastNotification();
                mHandler.sendMessageDelayed(mHandler.obtainMessage(EVENT_LIST_CURRENT_NOS), 50);
                break;
            case R.id.btnClearAllNotify:
                logE("Clear All notifications...");
                clearAllNotifications();
                mHandler.sendMessageDelayed(mHandler.obtainMessage(EVENT_LIST_CURRENT_NOS), 50);
                break;
            case R.id.btnListNotify:
                logE("List notifications...");
                listCurrentNotification();
                break;
            case R.id.btnEnableUnEnableNotify:
                logE("Enable/UnEnable notification...");
                openNotificationAccess();
                break;
            default:
                break;
        }
    }

    private boolean isEnabled() {
        String pkgName = getPackageName();
        final String flat = Settings.Secure.getString(getContentResolver(), ENABLED_NOTIFICATION_LISTENERS);
        if (!TextUtils.isEmpty(flat)) {
            final String[] names = flat.split(":");
            for (int i = 0; i < names.length; i++) {
                final ComponentName cn = ComponentName.unflattenFromString(names[i]);
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.getPackageName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void createNotification(Context context) {
        NotificationCompat.Builder ncBuilder = new NotificationCompat.Builder(context);
        ncBuilder.setContentTitle("My Notification");
        ncBuilder.setContentText("Notification Listener Service Example");
        ncBuilder.setTicker("Notification Listener Service Example");
//        ncBuilder.setSmallIcon(R.drawable.ic_settings_black);
        ncBuilder.setAutoCancel(true);
        manager.notify((int) System.currentTimeMillis(),ncBuilder.build());
    }

    private void cancelNotification(Context context, boolean isCancelAll) {
        Intent intent = new Intent();
        intent.setAction(NotificationMonitor.ACTION_NLS_CONTROL);
        if (isCancelAll) {
            intent.putExtra("command", "cancel_all");
        }else {
            intent.putExtra("command", "cancel_last");
        }
        context.sendBroadcast(intent);
    }

    private String getCurrentNotificationString() {
        String listNos = "";
        StatusBarNotification[] currentNos = NotificationMonitor.getCurrentNotifications();
        if (currentNos != null) {
            VerbalReminder verbalReminders = null;
//            VerbalReminder[] verbalReminders = new VerbalReminder[currentNos.length];
            for (int i = 0; i < currentNos.length; i++) {
                StatusBarNotification currentNo = currentNos[i];
                Bundle extra = currentNo.getNotification().extras;

                listNos = i +" " + currentNo.getPackageName() + "\n" + listNos +
                        "\n" + i + currentNo.getNotification().tickerText +
                        "\n" + i + currentNo.getTag() + "\n" +
                        "\n" + i + currentNo.toString() + "\n" +
                        "\n" + i + extra.getCharSequence("android.text").toString() +
//                        "\n" + i + extra.getCharSequence("android.subText").toString() +
                        "\n" + i + extra.getCharSequence("android.title").toString() + "\n" ;

                if(currentNo.getPackageName().contains("com"))
                    verbalReminders = new VerbalReminder(currentNo.getPostTime()+"", extra.getCharSequence("android.title").toString());
                // contentTitle, contentText, tickerText

                Log.e(TAG, "getCurrentNotificationString: "+ verbalReminders.getReminder() );
                if(verbalReminders != null) {
                    Naoqi naoqi = Naoqi.getInstance();
                    if(naoqi.isRunning()){
                        try {
                            alTextToSpeech = new ALTextToSpeech(naoqi.getSession());
                            alTextToSpeech.say(verbalReminders.getReminder());
                        } catch (CallError callError) {
                            callError.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
//                    if(robot.isConnected()){
//                        new SpeechTask(robot.getSpeech()).execute(verbalReminders.getReminder());
//
//                    }
                }
            }
        }
        return listNos;
    }

    private void listCurrentNotification() {
        String result = "";
        if (isEnabled) {
//            if (NotificationMonitor.getCurrentNotifications() == null) {
//                logE("mCurrentNotifications.get(0) is null");
//                return;
//            }
            int n = NotificationMonitor.mCurrentNotificationsCounts;
            if (n == 0) {
                result = getResources().getString(R.string.active_notification_count_zero);
            }else {
                result = String.format(getResources().getQuantityString(R.plurals.active_notification_count_nonzero, n, n));
            }
            result = result + "\n" + getCurrentNotificationString();
            mTextView.setText(result);
        }else {
            mTextView.setTextColor(Color.RED);
            mTextView.setText("Please Enable Notification Access");
        }
    }

    private void clearLastNotification() {
        if (isEnabled) {
            cancelNotification(this,false);
        }else {
            mTextView.setTextColor(Color.RED);
            mTextView.setText("Please Enable Notification Access");
        }
    }

    private void clearAllNotifications() {
        if (isEnabled) {
            cancelNotification(this,true);
        }else {
            mTextView.setTextColor(Color.RED);
            mTextView.setText("Please Enable Notification Access");
        }
    }

    private void showCreateNotification() {
        if (NotificationMonitor.mPostedNotification != null) {
            String result = NotificationMonitor.mPostedNotification.getPackageName()+"\n"
                    + NotificationMonitor.mPostedNotification.getTag()+"\n"
                    + NotificationMonitor.mPostedNotification.getId()+"\n"+"\n"
                    + mTextView.getText();
            result = "Create notification:"+"\n"+result;
            mTextView.setText(result);
        }
    }

    private void openNotificationAccess() {
        startActivity(new Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS));
    }

    private void showConfirmDialog() {
        new AlertDialog.Builder(this)
                .setMessage("Please enable NotificationMonitor access")
                .setTitle("Notification Access")
                .setIconAttribute(android.R.attr.alertDialogIcon)
                .setCancelable(true)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                openNotificationAccess();
                            }
                        })
                .setNegativeButton(android.R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // do nothing
                            }
                        })
                .create().show();
    }

    private void logE(Object object) {
        Log.e(TAG, TAG_PRE+object);
    }
}
