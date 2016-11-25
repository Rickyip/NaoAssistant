package com.yipkaming.naoassistant.model;

/**
 * Created by Yip on 7/10/2016.
 */

public class Config {

    public static final String TAG = "Nao Assistant";
    public static final String ERROR_URL = "999";

    public static final String DEVICE_NAME = android.os.Build.MODEL;

    public static String getSimpleName(Class cls){
        return "[" + cls.getSimpleName() + "] ";
    }


    public static final String ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";
    public static final String ACTION_NOTIFICATION_LISTENER_SETTINGS = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS";


}