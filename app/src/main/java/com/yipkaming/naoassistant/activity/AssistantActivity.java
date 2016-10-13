package com.yipkaming.naoassistant.activity;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.service.notification.StatusBarNotification;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.helper.proxies.ALTextToSpeech;
import com.yipkaming.naoassistant.helper.NotificationMonitor;
import com.yipkaming.naoassistant.R;
import com.yipkaming.naoassistant.model.Config;
import com.yipkaming.naoassistant.model.Naoqi;
import com.yipkaming.naoassistant.model.VerbalReminder;


public class AssistantActivity extends AppCompatActivity {

    private static final String TAG = Config.getSimpleName(AssistantActivity.class);
    private boolean isEnabled = false;


    TextView messages;
    Button showList, create;

    NotificationManager manager;
    ALTextToSpeech alTextToSpeech;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assistant);

        messages = (TextView) findViewById(R.id.messages);
        showList = (Button) findViewById(R.id.showList);
        create = (Button) findViewById(R.id.create);
        manager= (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);

        setupBtnListener();
    }

    private void setupBtnListener() {
        showList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listCurrentNotification();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        isEnabled = isEnabled();
        Log.e(TAG, "isEnabled = " + isEnabled );
        if (!isEnabled) {
            showConfirmDialog();
        }
    }


    private boolean isEnabled() {
        String pkgName = getPackageName();
        final String flat = Settings.Secure.getString(getContentResolver(), Config.ENABLED_NOTIFICATION_LISTENERS);
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


    private String getCurrentNotificationString() {
        String listNos = "";
        StatusBarNotification[] currentNos = NotificationMonitor.getCurrentNotifications();
        if (currentNos != null) {
            VerbalReminder verbalReminders = null;
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

                }
            }
        }
        return listNos;
    }

    private void listCurrentNotification() {
        String result = "";
        if (isEnabled) {
            int n = NotificationMonitor.mCurrentNotificationsCounts;
            if (n == 0) {
                result = getResources().getString(R.string.active_notification_count_zero);
            }else {
                result = String.format(getResources().getQuantityString(R.plurals.active_notification_count_nonzero, n, n));
            }
            result = result + "\n" + getCurrentNotificationString();
            messages.setText(result);
        }else {
            messages.setTextColor(Color.RED);
            messages.setText("Please Enable Notification Access");
        }
    }
    
    private void openNotificationAccess() {
        startActivity(new Intent(Config.ACTION_NOTIFICATION_LISTENER_SETTINGS));
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



}
