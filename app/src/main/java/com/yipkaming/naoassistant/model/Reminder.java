package com.yipkaming.naoassistant.model;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import com.yipkaming.naoassistant.NaoAssistant;
import com.yipkaming.naoassistant.R;
import com.yipkaming.naoassistant.helper.DateHelper;
import com.yipkaming.naoassistant.helper.NotificationPublisher;

/**
 * Created by Yip on 5/3/2017.
 */

public class Reminder {

    private static final String TAG = Config.getSimpleName(Reminder.class);
    public static final String TITLE = "Scheduled Notification from Nao";

    private long id;
    private String time;
    private String name;

    private Context context = NaoAssistant.getContext();

    public Reminder() {
    }

    public void scheduleNotification() {
        Notification notification = makeNotification();
        int delay = (int) DateHelper.getReminderTimestamp(time);
        Log.e(TAG, "scheduleNotification: "+ delay);
        Intent notificationIntent = new Intent(context, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, id);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    public Notification makeNotification(){
        Log.e(TAG, "makeNotification: ");
        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentTitle(TITLE);
        builder.setContentText(name);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        return builder.build();
    }

    public boolean isComplete(){
        if(getName() != null && getTime() != null){
            return true;
        }
        return false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}