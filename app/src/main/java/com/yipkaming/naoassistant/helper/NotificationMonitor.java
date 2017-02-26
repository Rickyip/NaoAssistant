package com.yipkaming.naoassistant.helper;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.Log;

import com.yipkaming.naoassistant.NaoAssistant;
import com.yipkaming.naoassistant.model.Config;
import com.yipkaming.naoassistant.model.Nao;
import com.yipkaming.naoassistant.model.NotificationMessage;
import com.yipkaming.naoassistant.model.VerbalReminder;
import com.yipkaming.naoassistant.strategy.ReadNotification;

import java.util.ArrayList;
import java.util.List;

public class NotificationMonitor extends NotificationListenerService {

    private static final String TAG = Config.getSimpleName(NotificationMonitor.class);

    private static final int EVENT_UPDATE_CURRENT_NOS = 0;
    public static final String ACTION_NLS_CONTROL = "com.yipkaming.naoassistant.NLSCONTROL";

    public static List<StatusBarNotification[]> currentNotifications = new ArrayList<StatusBarNotification[]>();
    public static int currentNotificationsCounts = 0;
    public static StatusBarNotification postedNotification;
    public static StatusBarNotification removedNotification;
    private CancelNotificationReceiver cancelNotificationReceiver = new CancelNotificationReceiver();
    // String a;
    private static NotificationMonitor notificationMonitor;

    private Handler monitorHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case EVENT_UPDATE_CURRENT_NOS:
                    updateCurrentNotifications();
                    break;
                default:
                    break;
            }
        }
    };

    class CancelNotificationReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action;
            if (intent != null && intent.getAction() != null) {
                action = intent.getAction();
                if (action.equals(ACTION_NLS_CONTROL)) {
                    String command = intent.getStringExtra("command");
                    if (TextUtils.equals(command, "cancel_last")) {
                        if (currentNotifications != null && currentNotificationsCounts >= 1) {
                            StatusBarNotification sbnn = getCurrentNotifications()[currentNotificationsCounts - 1];
                            cancelNotification(sbnn.getPackageName(), sbnn.getTag(), sbnn.getId());
                        }
                    } else if (TextUtils.equals(command, "cancel_all")) {
                        cancelAllNotifications();
                    }
                }
            }
        }

    }

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_NLS_CONTROL);
        registerReceiver(cancelNotificationReceiver, filter);
        monitorHandler.sendMessage(monitorHandler.obtainMessage(EVENT_UPDATE_CURRENT_NOS));
        notificationMonitor = this;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(cancelNotificationReceiver);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // a.equals("b");
        return super.onBind(intent);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        updateCurrentNotifications();
        Log.e(TAG, "onNotificationPosted: " );
        Log.i(TAG, "have " + currentNotificationsCounts + " active notifications");
        postedNotification = sbn;

        NotificationMessage.initMessages(sbn);
        try {
            Nao nao = Nao.getInstance();
            if(nao.isRunning()){
                if(!nao.isInit()) {
                    nao.say(VerbalReminder.NEW_NOTIFICATION);
                }
                nao.setConfirmAction(new ReadNotification());
            }else{
                Log.e(TAG, "onNotificationPosted: " );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

//        Bundle extras = sbn.getNotification().extras;
//        String notificationTitle = extras.getString(Notification.EXTRA_TITLE);
//        CharSequence notificationText = extras.getCharSequence(Notification.EXTRA_TEXT);
//        CharSequence notificationSubText = extras.getCharSequence(Notification.EXTRA_SUB_TEXT);
////        Log.i(TAG, "notificationTitle:"+notificationTitle);
////        Log.i(TAG, "notificationText:"+notificationText);
////        Log.i(TAG, "notificationSubText:"+notificationSubText);



//        NaoAssistant.getNotificationManager().cancel(sbn.getExtraContent(), sbn.getId());
    }



    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        updateCurrentNotifications();
        removedNotification = sbn;
    }

    private void updateCurrentNotifications() {
        try {
            StatusBarNotification[] activeNos = getActiveNotifications();
            if (currentNotifications.size() == 0) {
                currentNotifications.add(null);
            }
            currentNotifications.set(0, activeNos);
            currentNotificationsCounts = activeNos.length;
        } catch (Exception e) {
            Log.i(TAG, "Should not be here!!");
            e.printStackTrace();
        }
    }

    public static StatusBarNotification[] getCurrentNotifications() {
        if (currentNotifications.size() == 0) {
            Log.i(TAG, "currentNotifications size is ZERO!!");
            return null;
        }
        return currentNotifications.get(0);
    }


    public static NotificationMonitor getInstance(){
        return notificationMonitor;
    };

}
