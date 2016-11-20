package com.yipkaming.naoassistant.activity;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.service.notification.StatusBarNotification;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.yipkaming.naoassistant.fragment.ConnectionFragment;
import com.yipkaming.naoassistant.helper.KeyboardHelper;
import com.yipkaming.naoassistant.helper.NotificationMonitor;
import com.yipkaming.naoassistant.R;
import com.yipkaming.naoassistant.model.Config;
import com.yipkaming.naoassistant.model.DateFormat;
import com.yipkaming.naoassistant.model.Keyword;
import com.yipkaming.naoassistant.model.NotificationMessage;
import io.realm.Realm;


public class AssistantActivity extends AppCompatActivity implements ConnectionFragment.OnConnectionListener{

    private static final String TAG = Config.getSimpleName(AssistantActivity.class);
    private boolean isEnabled = false;

    TextView messages;
    Button showList;

    NotificationManager manager;
    Realm realm = Realm.getDefaultInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assistant);

        initFragment();

        messages = (TextView) findViewById(R.id.messages);
        showList = (Button) findViewById(R.id.showList);
        manager= (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);

//        setupBtnListener();
    }


    private void initFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.connectFragment, new ConnectionFragment())
                .commit();
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
        KeyboardHelper.closeKeyboardFromActivity(this);
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
                String androidText = "", title = "";
                if( extra != null ){
                    if(extra.getCharSequence("android.text") != null){
                        androidText = extra.getCharSequence("android.text").toString();
                    }
                    if(extra.getCharSequence("android.title") != null){
                        title = extra.getCharSequence("android.title").toString();
                    }
                }


                NotificationMessage notificationMessage = new NotificationMessage(title
                        , currentNo.getPackageName()
                        , androidText
                        , currentNo.getTag()
                        , currentNo.getPostTime()
                        , (String) currentNo.getNotification().tickerText );

                notificationMessage.save(realm);

//                Selection.getInstance().process(notificationMessage);


                Log.e(TAG, "!@#$%^&*(): "+ DateFormat.getDaysHoursMinutes(notificationMessage.getTime()) );

            }
        }

        return listNos;
    }

    private void listCurrentNotification() {
        String result = "";
        if (isEnabled) {
            int counts = NotificationMonitor.mCurrentNotificationsCounts;
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

    private void openNotificationAccess() {
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

    @Override
    public void onConnected() {
        // do nothing
    }

    @Override
    public void onBackPressed() {

    }
}
