package com.yipkaming.naoassistant.helper;

import android.util.Log;

import com.yipkaming.naoassistant.model.Config;
import com.yipkaming.naoassistant.model.Keyword;
import com.yipkaming.naoassistant.model.Nao;
import com.yipkaming.naoassistant.model.NotificationMessage;
import com.yipkaming.naoassistant.model.VerbalReminder;

/**
 * Created by Yip on 7/11/2016.
 */

public class SelectionHelper {
    // Singleton pattern

    private static final String TAG = Config.getSimpleName(SelectionHelper.class);

    private static SelectionHelper instance;

    private SelectionHelper(){}

    public void process(NotificationMessage notificationMessage){
        if(Keyword.findKeyWord(notificationMessage.getPackageName())){
            VerbalReminder verbalReminder = new VerbalReminder(notificationMessage);
            try {
                Log.e(TAG, "Say: "+ verbalReminder.getReminder() );
//                Nao.getInstance().say(verbalReminder.getReminder());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 1. important application?
        // 2. if no, any keywords in content/ title?
        // 3. if 1 yes or 2 yes, pass to verbal message class
        // 4. if both no, notificationMessage.setImportance(1), and push to nao
        // 5. notificationMessage.save()   save to db
        // 6. end
    }

    public static SelectionHelper getInstance(){
        if(instance == null){
            instance = new SelectionHelper();
        }
        return instance;
    }
}
