package com.yipkaming.naoassistant.activity;

import android.app.AlertDialog;
import android.app.Notification;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.service.notification.StatusBarNotification;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.yipkaming.naoassistant.fragment.ConnectionFragment;
import com.yipkaming.naoassistant.helper.NotificationMonitor;
import com.yipkaming.naoassistant.R;
import com.yipkaming.naoassistant.helper.SelectionHelper;
import com.yipkaming.naoassistant.model.Config;
import com.yipkaming.naoassistant.model.Keyword;
import com.yipkaming.naoassistant.model.Nao;
import com.yipkaming.naoassistant.model.NotificationMessage;
import io.realm.Realm;


public class AssistantActivity extends AppCompatActivity implements ConnectionFragment.OnConnectionListener{

    private static final String TAG = Config.getSimpleName(AssistantActivity.class);
    private boolean isEnabled = false;

    private Realm realm = Realm.getDefaultInstance();
    private Nao nao;

    TextView messages;
//    Button showList;
    MenuItem disconnectBtn;

    //    private NotificationManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assistant);

        initFragment();

        messages = (TextView) findViewById(R.id.messages);
//        showList = (Button) findViewById(R.id.showList);
//        manager = NaoAssistant.getNotificationManager();

//        setupBtnListener();
    }


    private void initFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.connectFragment, new ConnectionFragment())
                .commit();
    }

//    private void setupBtnListener() {
//        showList.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                listCurrentNotification();
//            }
//        });
//    }


    @Override
    protected void onResume() {
        super.onResume();
        isEnabled = isEnabled();
        Log.e(TAG, "isEnabled = " + isEnabled );
        if (!isEnabled) {
            showConfirmDialog();
        }else {
            listCurrentNotification();
        }
    }

    private String show() {
        // todo: clear all data for testing purpose
        NotificationMessage.clearAll(realm);
        Keyword.clearAll(realm);
        Keyword.init();

        String listNos = "";
        StatusBarNotification[] currentNos = NotificationMonitor.getCurrentNotifications();
        if (currentNos != null) {
            for (int i = 0; i < currentNos.length; i++) {
                StatusBarNotification currentNo = currentNos[i];
                Bundle extra = currentNo.getNotification().extras;
                String androidText = "", title = "", multiline = "", ticker = "";
                if( extra != null ){
                    if(extra.getCharSequence("android.text") != null){
                        androidText = extra.getCharSequence("android.text").toString();
                    }
                    if(extra.getCharSequence("android.title") != null){
                        title = extra.getCharSequence("android.title").toString();
                    }
                    if(extra.getCharSequence("android.textLines") != null){
                        multiline = extra.getCharSequence("android.textLines").length() +", "+ extra.getCharSequence("android.textLines");
                        Log.e(TAG, "show: "+multiline );
                    }
                }

                if(currentNo.getNotification().tickerText != null){
                    ticker = currentNo.getNotification().tickerText.toString();
                }
                // cannot solve multi line problem in API 18
                // read one first and then dismiss
                Object content, test;
                content =  extra.get(Notification.EXTRA_BIG_TEXT);
                test =  extra.get(Notification.EXTRA_TEXT);

                if(content != null){
                    Log.e(TAG, "show: content, "+content.toString());
                }
                Log.e(TAG, "show: " );

                if(test != null){
                    Log.e(TAG, "show: test "+test.toString());
                }


                NotificationMessage notificationMessage = new NotificationMessage(title
                        , currentNo.getPackageName()
                        , androidText // content
                        , currentNo.getTag() // tag
                        , currentNo.getPostTime()
                        , ticker ); // ticker text

                notificationMessage.save();

                SelectionHelper.getInstance().process(notificationMessage);

                try {
//                    nao.endVoiceRecognition();
//                    nao.startVoiceRecognition();
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                cancelNotification(this, false);
            }
        }

//        nao = Nao.getInstance();
//        try {
//            nao.startVoiceRecognition();
//            nao.wait();
//            nao.endVoiceRecognition();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        return listNos;
    }

    private void listCurrentNotification() {
        String result = "";
        if (isEnabled) {
            int counts = NotificationMonitor.currentNotificationsCounts;
            if (counts == 0) {
                result = getResources().getString(R.string.active_notification_count_zero);
            }else {
                result = String.format(getResources().getQuantityString(R.plurals.active_notification_count_nonzero, counts, counts));
            }
            result = result + "\n" + show();
            messages.setText(result);
        }else {
            messages.setTextColor(Color.RED);
            messages.setText(R.string.PleaseEnableNotificationAccess);
        }
    }

    private void openNotificationSetting() {
        startActivity(new Intent(Config.ACTION_NOTIFICATION_LISTENER_SETTINGS));
    }

    private void showConfirmDialog() {
        // todo change to material dialog

        new AlertDialog.Builder(this)
                .setMessage("Please enable NotificationMonitor access")
                .setTitle("NotificationMessage Access")
                .setIconAttribute(android.R.attr.alertDialogIcon)
                .setCancelable(true)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                openNotificationSetting();
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


    private boolean isEnabled() {
        String packageName = getPackageName();
        final String flat = Settings.Secure.getString(getContentResolver(), Config.ENABLED_NOTIFICATION_LISTENERS);
        if (!TextUtils.isEmpty(flat)) {
            final String[] names = flat.split(":");
            for (int i = 0; i < names.length; i++) {
                final ComponentName componentName = ComponentName.unflattenFromString(names[i]);
                if (componentName != null) {
                    if (TextUtils.equals(packageName, componentName.getPackageName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    protected void cancelNotification(Context context, boolean isCancelAll) {
        Intent intent = new Intent();
        intent.setAction(NotificationMonitor.ACTION_NLS_CONTROL);
        if (isCancelAll) {
            intent.putExtra("command", "cancel_all");
        }else {
            intent.putExtra("command", "cancel_last");
        }
        context.sendBroadcast(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assistant, menu);
        disconnectBtn = menu.findItem(R.id.Disconnect);

        displayAndHideDisconnectBtn();
        return super.onCreateOptionsMenu(menu);
    }

    private void displayAndHideDisconnectBtn() {
        nao = Nao.getInstance();
        disconnectBtn.setVisible(nao.isRunning());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.Disconnect:
                disconnect();
                return true;
        }
        return false;

    }

    private void disconnect() {
        nao = Nao.getInstance();
        nao.stop();
        ConnectionFragment.setInstance(null);
        finish();

    }

    @Override
    public void onConnected() {
        // do nothing
    }

    @Override
    public void onBackPressed() {
        // forbid user to disconnect with nao
    }

}
