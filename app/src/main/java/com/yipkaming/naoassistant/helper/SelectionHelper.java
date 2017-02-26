package com.yipkaming.naoassistant.helper;

import android.util.Log;

import com.yipkaming.naoassistant.model.Config;
import com.yipkaming.naoassistant.model.Keyword;
import com.yipkaming.naoassistant.model.Nao;
import com.yipkaming.naoassistant.model.NotificationMessage;
import com.yipkaming.naoassistant.model.VerbalReminder;

import java.util.List;

import io.realm.Realm;

/**
 * Created by Yip on 7/11/2016.
 */

public class SelectionHelper {

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
        // Get the highest importance of word to see if it is valuable to present

        String[] titleFragments = notificationMessage.getTitle().split(VerbalReminder.SPACE);
        int titleImportance = getImportanceOfEachWords(titleFragments);
        importance = (importance < titleImportance) ? titleImportance : importance;

        String[] contentFragments = notificationMessage.getContent().split(VerbalReminder.SPACE);
        int contentImportance = getImportanceOfEachWords(contentFragments);
        importance = (importance < contentImportance) ? contentImportance : importance;

        // Set the importance into the notification and save to db

//        Log.e(TAG, "process: "+ notificationMessage.getContent() + ": "+ importance );
        notificationMessage.setImportance(importance);
        notificationMessage.save();


        // Change of flow

        // If the importance passes the threshold, it will be presented
//        if(importance >= getImportanceThreshold()){
//            VerbalReminder verbalReminder = new VerbalReminder(notificationMessage);
//            try {
//                Log.e(TAG, "Say: "+ verbalReminder.getReminder() );
//                if( nao != null && nao.isRunning()){
//                    nao.say(verbalReminder.getReminder());
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

        // 1. important application?
        // 2. if no, any keywords in content/ title?
        // 3. if 1 yes or 2 yes, pass to verbal message class
        // 4. if both no, notificationMessage.setImportance(1), and push to nao
        // 5. notificationMessage.save()   save to db
        // 6. end
    }

    private int getImportanceOfEachWords(String[] arrayOfWords) {
        int importance = 1;
        if(arrayOfWords.length != 0){
            // single word
            for (String word : arrayOfWords) {
//                Log.e(TAG, "process: " + word);
                if (Keyword.hasKeyword(word)) {
                    Keyword contentFrag = Keyword.getKeywordFromString(word);
                    if (importance < contentFrag.getImportance()) {
                        importance = contentFrag.getImportance();
                    }
                }
            }
            // bigram
            if(arrayOfWords.length > 1) {
                for (int i = 0; i < arrayOfWords.length - 1; i++) {
                    String bigram = arrayOfWords[i] + " " + arrayOfWords[i + 1];
//                    Log.e(TAG, "bigram: "+ bigram );
                    if (Keyword.hasKeyword(bigram)) {
                        Keyword contentFrag = Keyword.getKeywordFromString(bigram);
                        if (importance < contentFrag.getImportance()) {
                            importance = contentFrag.getImportance();
                        }
                    }
                }
            }
            // trigram
            if(arrayOfWords.length > 2) {
                for (int i = 0; i < arrayOfWords.length - 2; i++) {
                    String trigram = arrayOfWords[i] + " " + arrayOfWords[i + 1] + " " + arrayOfWords[i + 2];
//                    Log.e(TAG, "trigram: "+ trigram );
                    if (Keyword.hasKeyword(trigram)) {
                        Keyword contentFrag = Keyword.getKeywordFromString(trigram);
                        if (importance < contentFrag.getImportance()) {
                            importance = contentFrag.getImportance();
                        }
                    }
                }
            }
        }
        return importance;
    }

    public static void read(Nao nao){
        List<NotificationMessage> aboutToRead = NotificationMessage.findReadable(Realm.getDefaultInstance(), importanceThreshold);

        if(aboutToRead.isEmpty()){
            try {
                if(nao.isRunning()){
                    nao.say(VerbalReminder.NO_NOTIFICATION);
                }
                Log.e(TAG, "read: "+ VerbalReminder.NO_NOTIFICATION );
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else {
            for (NotificationMessage notification : aboutToRead) {
                VerbalReminder reminder = new VerbalReminder(notification);
                try {
                    if(nao.isRunning()){
                        nao.say(reminder.getReminder());
                    }
                    Log.e(TAG, "read: "+reminder.getReminder() );
                    notification.setHasRead(Realm.getDefaultInstance());
                    notification.save();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static SelectionHelper getInstance(){
        if(instance == null){    // Singleton pattern
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
