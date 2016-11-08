package com.yipkaming.naoassistant.model;

/**
 * Created by Yip on 7/10/2016.
 */

public class VerbalReminder {

    private static final String SPACE = " ";
    private String timeHeader = "You have a message at ";
    private String time;
    private String content;
    private String app = "from ";
    private String reminder;

    public VerbalReminder(String time, String content) {
        this.time = time;
        this.content = content;
    }

    public VerbalReminder(Notification notification) {
        this.time = String.valueOf(notification.getTime());
        this.content = notification.getContent();
        this.app += notification.getPackageName();
    }

    public String getReminder() {
        reminder = timeHeader+timeToDate()+content+app;
        return reminder;
    }


    private String timeToDate(){
        return DateFormat.getDateTime(time);
    }

}
