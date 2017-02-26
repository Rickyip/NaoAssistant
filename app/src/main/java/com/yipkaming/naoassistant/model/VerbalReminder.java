package com.yipkaming.naoassistant.model;

import com.yipkaming.naoassistant.helper.DateHelper;
import com.yipkaming.naoassistant.helper.SelectionHelper;

import java.util.List;

import io.realm.Realm;

/**
 * Created by Yip on 7/10/2016.
 */

public class VerbalReminder {

    // Greetings
    public static final String CONNECTION_GREETING = "Hi! I am Nao, I have connected to device " + Config.getBluetoothName();
    public static final String INTRODUCTION = "I am your assistant today, " +
            "You can tell me to read the messages by saying read notifications " +
            "You can also stop this service by saying Stop recognition service!";
    public static final String READ_NOTIFICATION_GREETING = "Do you want me to read the it?";

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
    public static final String EMAIL = "e-mail ";
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
    public static final String WHATSAPP = "com.whatsapp";
    public static final String MISSED_VOICE_CALL = "Missed voice call";



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
        this.app = notificationMessage.getPackageName();
    }

    public String getReminder() {
        if(CALLS.equals(this.app)){  // case of missed calls
            if(tickertext != null && !"".equals(tickertext.trim()) && tickertext.contains(MISSED_CALLS)){
                reminder =  "";
                return reminder;
            }

            String person = "", time = "";
            if(!"".equals(content) && content.contains(COLON)){
                int index = content.indexOf(COLON) - 2;
                person = content.substring(0, index );
                time = content.substring(index);
            }
            reminder = YOU_HAVE_A + MISSED_CALLS + FROM + person + AT + time;
            return reminder;
        }else if(CALENDER.equals(this.app)){  // case of calender schedule
            return YOU_HAVE_A + SCHEDULE + this.title + AT + this.content;

        }else if(GMAIL_PACKAGE.equals(this.app)){  // case of email
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
        }else if(WHATSAPP.equals(this.app) && MISSED_VOICE_CALL.equals(title)){  // case of email
            reminder = YOU_HAVE_A + tickertext;
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

    public static String getNotificationGreeting(int num){
        String greeting = "You have "+ num+ "notification";
        return greeting+READ_NOTIFICATION_GREETING;
    }

    private String timeToDate(){
        return DateHelper.getDaysHoursMinutes(Long.valueOf(time));
    }
}

/*
Cases design:
Missed calls
There are always two notification messages that the system can receive,
which are one notification including the information of “Missed call from someone”
or “n missed call” for each missed call and one notification having the time and number
or contact name of the missed call.
In order to consolidate the verbal messages for Nao to present about missed calls,
this system will be presenting the notification which has contact name or number,
considering the fact that having contact name in the notification should have a higher importance for the user.

Calendar:
For the calendar application, the format is different from other application:
the title is the event of the schedule and the content is the scheduled time.
Therefore, it requires its own way to form the verbal messages.

Email:
For the email application, the format is also different.
The title is the sender’s email or name, the email title is stored in
notification content and the email content is stored in extra content.
Considering the length could be quite long for emails, if the content of
the email has contained more than 45 words, the verbal reminder will not include the email content.
*/