package com.yipkaming.naoassistant.helper;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Yip on 10/10/2016.
 */

public class DateHelper {

    private static final String TODAY = "Today ";
    private static final String YESTERDAY = "Yesterday ";
    private static final String TOMORROW = "Tomorrow ";

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
}
