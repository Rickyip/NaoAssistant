package com.yipkaming.naoassistant.model;

import com.yipkaming.naoassistant.helper.DateHelper;

/**
 * Created by Yip on 7/10/2016.
 */

public class VerbalReminder {

    public static final String CONNECTION_GREETING = "Hi! I am Nao, I have connected to device " + Config.getBluetoothName();
    public static final String INTRODUCTION = "I am your assistant today, " +
            "You can tell me to read the messages for you by saying read notifications " +
            "You can also stop this service by saying Stop ASR!";


    public static final String NO_NOTIFICATION = "You don't have new messages";
    public static final String NEW_NOTIFICATION = "You have new messages, do you want me to read it?";

    public static final String SPACE = " ";
    public static final String SAYING = "saying";
    public static final String AGO = "ago";
    public static final String COM_DOT = "com.";
    public static final String GMAIL = "Gmail";
    public static final String GM = "gm";
    public static final String GOOGLE_ANDROID_GM = "google.android.gm";
    public static final String HEADER = "You have a message ";
    public static final String FROM = " from ";


    private String time;
    private String content;
    private String app = FROM;
    private String reminder;
    private String extraMsg;
    private String title;

    public VerbalReminder(String time, String content) {
        this.time = time;
        this.content = content;
    }

    public VerbalReminder(NotificationMessage notificationMessage) {
        this.time = String.valueOf(notificationMessage.getTime());
        this.content = notificationMessage.getContent();
        this.extraMsg = notificationMessage.getExtraContent();
        this.title = notificationMessage.getTickerText();

        String packageName = notificationMessage.getPackageName();
//        if(packageName.contains(COM_DOT)){
//            packageName = packageName.substring(4);
//            if(GM.equals(packageName)){
//                packageName = GMAIL;
//            }
//        }
        if(packageName.contains(GOOGLE_ANDROID_GM)){
            packageName = GMAIL;
        }
        this.app += packageName;
    }

    public String getReminder() {
        reminder = HEADER + timeToDate() + SPACE ;
        if(title != null && !"".equals(title)){
            reminder += FROM + title;
        }
        reminder += SAYING + SPACE + content;
        if(extraMsg != null && !"".equals(extraMsg)){
            reminder += SPACE + extraMsg;
        }

        return reminder;
    }


    private String timeToDate(){
        return DateHelper.getDaysHoursMinutes(Long.valueOf(time));
    }

}
