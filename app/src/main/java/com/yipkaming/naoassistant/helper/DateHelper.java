package com.yipkaming.naoassistant.helper;

import android.util.Log;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Yip on 10/10/2016.
 */

public class DateHelper {

    public static final String TODAY = "Today ";
    private static final String YESTERDAY = "Yesterday ";
    public static final String TOMORROW = "Tomorrow ";

    private static final String LOWER_TODAY = "today";
    private static final String LOWER_TOMORROW = "tomorrow";
    private static final String LOWER_EVERYDAY = "everyday";
    public static final String LOWER_MINUTES = "minutes";
    public static final String LOWER_HOURS = "hours";
    public static final String LOWER_HOUR = "hour";


    public static final String ONE = "one";
    public static final String TWO = "two";
    public static final String THREE = "three";
    public static final String FOUR = "four";

    public static final String SIX = "six";
    public static final String SEVEN = "seven";
    public static final String EIGHT = "eight";
    public static final String NINE = "nie";

    public static final String ELEVEN = "eleven";
    public static final String TWELVE= "twelve";

    public static final String FIVE = "five";
    public static final String TEN = "ten";
    public static final String FIFTEEN = "fifteen";
    public static final String TWENTY = "twenty";

    public static final String AT = "at";
    public static final String AM = "am";
    public static final String PM = "pm";


    private static long now = System.currentTimeMillis() ;
//    private final static long oneDay =

    public static String getDateTime(String timestamp){
        String date = getDate(Long.parseLong(timestamp));
        //todo make it contain "now" "yesterday" "tomorrow"
        Log.e( "getDateTime: ", date.substring(0, 10));
        Log.e( "now: ", getNow());
        if(date.substring(0, 10).equals(getNow().substring(0, 10))){
            date = TODAY+date.substring(11);
            Log.e( "getDateTime: ", date);
        }
        return date;
    }

    private static String getDate(long timestamp){
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
        Date date = new Date(timestamp);
        return sf.format(date);
    }

    private static String getReminderDate(boolean isTomorrow){
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd/hh-mm");
        long ts = now;
        if(isTomorrow){
            ts += (24 * 60 * 60 * 1000);
        }
        Date date = new Date(ts);
        return sf.format(date);
    }

    public static String getNow(){
        return getDate(System.currentTimeMillis());
    }

    public static String getDaysHoursMinutes(long timestamp){
        //in milliseconds
        long diff = now - timestamp;

        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);

        String msg = "";
        if(diffDays > 1 ){
            msg = diffDays + " days ago";
        } else if(diffDays == 1 ){
            msg = YESTERDAY;
        } else if (diffDays == 0){
            if(diffHours > 0 ){
                msg = diffHours + " hours ago";
            }else {
                if( diffMinutes > 0){
                    msg = diffMinutes + " minutes ago";
                }else {
                    msg = "now";
                }
            }
        }
        return msg;
    }

    public static long getReminderTimestamp(String timeIndicator){
        long timestamp = 0;
        Parser parser = new Parser();
        List<DateGroup> groups = parser.parse(timeIndicator);
        for(DateGroup group : groups) {
            List<Date> dates = group.getDates();
            int line = group.getLine();
            int column = group.getPosition();
            String matchingValue = group.getText();
            String syntaxTree = group.getSyntaxTree().toStringTree();
            Map parseMap = group.getParseLocations();
            boolean isRecurreing = group.isRecurring();
            Date recursUntil = group.getRecursUntil();
            timestamp = dates.get(0).getTime();
        }

        if(timestamp> now){
            timestamp -= now;
        }



//        long timestamp = 0;
//        boolean onetime = false;
//        String reminderDate = getReminderDate(false);
//        if(!"".equals(timeIndicator)){
//            // if the indicator contains AM/PM, there will not be minutes
//            if(timeIndicator.contains(AM) || timeIndicator.contains(PM)){
//                String hour = "";
//                int numOfHour = 0;
//                // date first, only consider today/tmr for this
//                if(timeIndicator.contains(LOWER_TODAY)){
//                    // no need to add time
//                }else if(timeIndicator.contains(LOWER_TOMORROW)){
//                    reminderDate = getReminderDate(true);
//                }else if (timeIndicator.contains(LOWER_EVERYDAY)){
//                    onetime = true;
//                }
//
//                if(timeIndicator.contains(AT)){
//                    if(timeIndicator.contains(ONE)){
//                        numOfHour = 1;
//                    }else if(timeIndicator.contains(TWO)){
//                        numOfHour = 2;
//                    }else if(timeIndicator.contains(THREE)){
//                        numOfHour = 3;
//                    }else if(timeIndicator.contains(FOUR)){
//                        numOfHour = 4;
//                    }else if(timeIndicator.contains(FIVE)){
//                        numOfHour = 5;
//                    }else if(timeIndicator.contains(SIX)){
//                        numOfHour = 6;
//                    }else if(timeIndicator.contains(SEVEN)){
//                        numOfHour = 7;
//                    }else if(timeIndicator.contains(EIGHT)){
//                        numOfHour = 8;
//                    }else if(timeIndicator.contains(NINE)){
//                        numOfHour = 9;
//                    }else if(timeIndicator.contains(TEN)){
//                        numOfHour = 10;
//                    }else if(timeIndicator.contains(ELEVEN)){
//                        numOfHour = 11;
//                    }else if(timeIndicator.contains(TWELVE)){
//                        numOfHour = 12;
//                    }
//
//                    if(timeIndicator.contains(PM)){
//                        if(numOfHour != 12){
//                            numOfHour += 12;
//                        }
//                    }
//
//                }
//                hour = String.valueOf(numOfHour);
//                if(numOfHour < 10){
//                    hour = "0"+hour;
//                }
//                hour += "-00";
//                //"yyyy-MM-dd/hh-mm"
//                reminderDate = reminderDate.substring(0, reminderDate.indexOf("/"))+hour;
//                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd/hh-mm", Locale.ENGLISH);
//                Date date = null;
//                try {
//                    date = formatter.parse(reminderDate);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//
//                if (date != null) {
//                    timestamp = date.getTime() - now;
//                }
//
////                if(onetime){
////                    timestamp *= -1;
////                }
//
//
//            }else {
//                if (timeIndicator.contains(LOWER_MINUTES)) { // consider minutes
//                    int multiplier = 1;
//                    if (timeIndicator.contains(FIVE)) {
//                        multiplier = 1;
//                    } else if (timeIndicator.contains(TEN)) {
//                        multiplier = 2;
//                    } else if (timeIndicator.contains(FIFTEEN)) {
//                        multiplier = 3;
//                    } else if (timeIndicator.contains(TWENTY)) {
//                        multiplier = 4;
//                    }
//                    timestamp += (5 * 60 * 1000) * multiplier;
//                } else if (timeIndicator.contains(LOWER_HOUR)) {
//                    timestamp += (60 * 60 * 1000);
//                } else if (timeIndicator.contains(LOWER_HOURS)) {
//                    // considering hour will not be long, long hour should be replaced by today at x AM/PM
//                    int multiplier = 1;
//                    if (timeIndicator.contains(TWO)) {
//                        multiplier = 2;
//                    } else if (timeIndicator.contains(THREE)) {
//                        multiplier = 3;
//                    } else if (timeIndicator.contains(FOUR)) {
//                        multiplier = 4;
//                    } else if (timeIndicator.contains(FIVE)) {
//                        multiplier = 5;
//                    }
//                    timestamp += (60 * 60 * 1000) * multiplier;
//                }
//
//            }
//
//        }
        return timestamp;
    }
}
