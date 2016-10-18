package com.yipkaming.naoassistant;

import android.app.Application;

import com.yipkaming.naoassistant.model.Config;

/**
 * Created by Yip on 18/10/2016.
 */

public class NaoAssistant extends Application {

    private static final String TAG = Config.getSimpleName(NaoAssistant.class);

    private static NaoAssistant context;

    public static NaoAssistant getContext() {
        return context;
    }
}
