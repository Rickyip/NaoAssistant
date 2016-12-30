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
    private static Nao nao = Nao.getInstance();

    private static int importanceThreshold = 3;

    private SelectionHelper(){}

    public void process(NotificationMessage notificationMessage){
        int importance = 1;

        // Check if it is from important application
        if(Keyword.hasKeyword(notificationMessage.getPackageName())){
            Keyword packageName = Keyword.getKeywordFromString(notificationMessage.getPackageName());
            importance = packageName.getImportance();
        }

        // Check whether it has important keywords in title or content

        String[] titleFragments = notificationMessage.getTitle().split(VerbalReminder.SPACE);
        int sizeOfTitleFragment = titleFragments.length;

        String[] contentFragments = notificationMessage.getContent().split(VerbalReminder.SPACE);
        int sizeOfContentFragment = contentFragments.length;

        if(sizeOfTitleFragment > 0){
            for (String titleFragment : titleFragments) {
                Log.e(TAG, "process: " + titleFragment);
                if (Keyword.hasKeyword(titleFragment)) {
                    Keyword title = Keyword.getKeywordFromString(titleFragment);
                    if (importance < title.getImportance()) {
                        importance = title.getImportance();
                    }
                }
            }
        }

        if(sizeOfContentFragment != 0){
            for (String contentFragment : contentFragments) {
                Log.e(TAG, "process: " + contentFragment);
                if (Keyword.hasKeyword(contentFragment)) {
                    Keyword contentFrag = Keyword.getKeywordFromString(contentFragment);
                    if (importance < contentFrag.getImportance()) {
                        importance = contentFrag.getImportance();
                    }
                }
            }
        }


        Log.e(TAG, "process: "+ notificationMessage.getContent() + ": "+ importance );
        notificationMessage.setImportance(importance);
        notificationMessage.save();

        if(importance >= getImportanceThreshold()){
            VerbalReminder verbalReminder = new VerbalReminder(notificationMessage);
            try {
                Log.e(TAG, "Say: "+ verbalReminder.getReminder() );
                if( nao != null && nao.isRunning()){
                    nao.say(verbalReminder.getReminder());
                }
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

    public static int getImportanceThreshold() {
        return importanceThreshold;
    }

    public static void setImportanceThreshold(int importanceThreshold) {
        SelectionHelper.importanceThreshold = importanceThreshold;
    }
}
