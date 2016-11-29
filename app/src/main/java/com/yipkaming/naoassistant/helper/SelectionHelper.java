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
        int importance = 1;
        boolean present = false;
        if(Keyword.findKeyWord(notificationMessage.getPackageName())){
            present = true;
            importance = 3;
        } else {
            String[] titleFragments = notificationMessage.getTitle().split(VerbalReminder.SPACE);
            String[] contentFragments = notificationMessage.getContent().split(VerbalReminder.SPACE);
            int sizeOfTitleFragment = titleFragments.length;
            int sizeOfContentFragment = contentFragments.length;
            if(sizeOfTitleFragment != 0){
                for(int i = 0; i < sizeOfTitleFragment; i++){
                    Log.e(TAG, "process: " + titleFragments[i]);
                    if(Keyword.findKeyWord(titleFragments[i])){
                        present = true;
                        importance = 4;
                    }
                }
            }
            if(sizeOfContentFragment != 0){
                for(int i = 0; i < sizeOfContentFragment; i++){
                    Log.e(TAG, "process: " + contentFragments[i]);
                    if(Keyword.findKeyWord(contentFragments[i])){
                        present = true;
                        importance = 4;
                    }
                }
            }

        }
        Log.e(TAG, "process: "+ notificationMessage.getContent() + ": "+ importance );
        notificationMessage.setImportance(importance);
        notificationMessage.save();

        if(true){
            VerbalReminder verbalReminder = new VerbalReminder(notificationMessage);
            try {
                Log.e(TAG, "Say: "+ verbalReminder.getReminder() );
                Nao.getInstance().say(verbalReminder.getReminder());
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
