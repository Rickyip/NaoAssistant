package com.yipkaming.naoassistant.model;

import com.yipkaming.naoassistant.helper.DateHelper;

/**
 * Created by Yip on 7/10/2016.
 */

public class VerbalReminder {

    public static final String CONNECTION_GREETING = "Hi! I have connected to device " + Config.DEVICE_NAME;

    private static final String SPACE = " ";
    private static final String SAYING = "saying";
    private static final String AGO = "ago";
    private static final String COM_DOT = "com.";
    private static final String HEADER = "You have a message ";
    private String time;
    private String content;
    private String app = "from ";
    private String reminder;

    public VerbalReminder(String time, String content) {
        this.time = time;
        this.content = content;
    }

    public VerbalReminder(NotificationMessage notificationMessage) {
        this.time = String.valueOf(notificationMessage.getTime());
        this.content = notificationMessage.getContent();
        String packageName = notificationMessage.getPackageName();
        if(packageName.contains(COM_DOT)){
            packageName = packageName.substring(4);
        }
        this.app += packageName;
    }

    public String getReminder() {
        reminder = HEADER + timeToDate() + SPACE + SAYING + SPACE + content + SPACE + app;
        return reminder;
    }


    private String timeToDate(){
        return DateHelper.getDaysHoursMinutes(Long.valueOf(time));
    }

}
