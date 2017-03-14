package com.yipkaming.naoassistant.activity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.service.notification.StatusBarNotification;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.yipkaming.naoassistant.NaoAssistant;
import com.yipkaming.naoassistant.fragment.ConnectionFragment;
import com.yipkaming.naoassistant.helper.NotificationMonitor;
import com.yipkaming.naoassistant.R;
import com.yipkaming.naoassistant.helper.NotificationPublisher;
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
    private Thread asr;

    TextView messages;
    MenuItem disconnectBtn;

    //    private NotificationManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assistant);
        initFragment();

        messages = (TextView) findViewById(R.id.messages);

//        manager = NaoAssistant.getNotificationManager();

    }


    private void initFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.connectFragment, new ConnectionFragment())
                .commit();
    }

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

    private void startVoiceService() {
        asr = new Thread(){
            @Override
            public void run() {
                nao = Nao.getInstance();
                if(nao.isRunning()){
                    try {
                        nao.startVoiceRecognition();
                        nao.wait();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        asr.start();
    }

    private String show() {
        // todo: clear all data for testing purpose
        NotificationMessage.clearAll(realm);
        Keyword.clearAll(realm);
        Keyword.init();

        String listNos = "";
        StatusBarNotification[] currentNos = NotificationMonitor.getCurrentNotifications();
        if (currentNos != null) {
            for (StatusBarNotification currentNo : currentNos) {
                NotificationMessage.initMessages(currentNo);
            }
        }
        startVoiceService();
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
//            messages.setText(result);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        asr.interrupt();
    }
}
