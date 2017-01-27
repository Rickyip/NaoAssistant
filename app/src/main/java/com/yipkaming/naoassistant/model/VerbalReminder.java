package com.yipkaming.naoassistant.model;

import com.yipkaming.naoassistant.helper.DateHelper;

/**
 * Created by Yip on 7/10/2016.
 */

public class VerbalReminder {

    // Greetings
    public static final String CONNECTION_GREETING = "Hi! I am Nao, I have connected to device " + Config.getBluetoothName();
    public static final String INTRODUCTION = "I am your assistant today, " +
            "You can tell me to read the messages for you by saying read notifications " +
            "You can also stop this service by saying Stop ASR!";

    // Standard message words
    public static final String NO_NOTIFICATION = "You don't have new messages";
    public static final String NEW_NOTIFICATION = "You have new messages, do you want me to read it?";
    public static final String SAYING = "saying";
    public static final String AGO = "ago";
    public static final String GMAIL = "Gmail";
    public static final String YOU_HAVE_A = "You have a ";
    public static final String MESSAGE = "message ";
    public static final String MISSED_CALL = "missed call ";
    public static final String SCHEDULE = "schedule ";
    public static final String EMAIL = "email ";
    public static final String FROM = " from ";
    public static final String AT = " at ";
    public static final String SPACE = " ";
    public static final String COLON = ":";
    public static final String LONG = " long ";
    public static final String TITLED = " titled ";

    // Identifier
    public static final String COM_DOT = "com.";
    public static final String GM = "gm";
    public static final String GOOGLE_ANDROID_GM = "google.android.gm";
    public static final String CALLS = "com.android.server.telecom";
    public static final String MISSED_CALLS = "missed calls";
    public static final String CALENDER = "com.android.calendar";
    public static final String GMAIL_PACKAGE = "com.google.android.gm";



    // Attributes
    private String time;
    private String content;
    private String app = FROM;
    private String reminder;
    private String extraMsg;
    private String title;
    private String tickertext;

    public VerbalReminder(String time, String content) {
        this.time = time;
        this.content = content;
    }

    public VerbalReminder(NotificationMessage notificationMessage) {
        this.time = String.valueOf(notificationMessage.getTime());
        this.content = notificationMessage.getContent();
        this.extraMsg = notificationMessage.getExtraContent();
        this.tickertext = notificationMessage.getTickerText();
        this.title = notificationMessage.getTitle();

        String packageName = notificationMessage.getPackageName();
//        if(packageName.contains(COM_DOT)){
//            packageName = packageName.substring(4);
//            if(GM.equals(packageName)){
//                packageName = GMAIL;
//            }
//        }
//        if(packageName.contains(GOOGLE_ANDROID_GM)){
//            packageName = GMAIL;
//        }
        this.app += packageName;
    }

    public String getReminder() {
        if(CALLS.equals(this.app)){  // case of missed calls
            if(tickertext != null && !"".equals(tickertext) && tickertext.contains(MISSED_CALLS)){
                reminder =  "";
                return reminder;
            }

            String person = "", time = "";
            if(!"".equals(content) && content.contains(COLON)){
                int index = content.indexOf(COLON) - 2;
                person = content.substring(0, index );
                time = content.substring(index);
            }
            reminder = YOU_HAVE_A + MISSED_CALLS + FROM + person + AT + time
            return reminder;
        }else if(CALENDER.equals(this.app)){  // case of calender schedule
            return YOU_HAVE_A + SCHEDULE + this.title + AT + this.content;
        }else if(GMAIL_PACKAGE.equals(this.app)){
            int wordCount = 0;
            if(extraMsg != null && !"".equals(extraMsg)){
                extraMsg = extraMsg.trim();
                if(!extraMsg.isEmpty()){
                    wordCount = extraMsg.split("\\s+").length;
                }
            }
            reminder = YOU_HAVE_A;
            if(wordCount > 45){ // title is sender email / name
                reminder += LONG + EMAIL + FROM + title + TITLED + this.content;
            }else {
                reminder += EMAIL + FROM + title + TITLED + this.extraMsg;
            }

            return reminder;
        }

        reminder = YOU_HAVE_A + MESSAGE + timeToDate() + SPACE ;
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
