package com.yipkaming.naoassistant.model;

import android.app.Notification;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import com.yipkaming.naoassistant.helper.SelectionHelper;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.Sort;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Yip on 7/11/2016.
 */

public class NotificationMessage extends RealmObject{

    private static final String TAG = Config.getSimpleName(NotificationMessage.class);

    // this class can combine info from bundle.extra and notification
    @PrimaryKey
    private long time;
    private String title;
    private String packageName;
    private String tickerText;
    private String content;
    private String extraContent;
    private boolean read;
    private int importance = 0;

    public NotificationMessage() {
    }

    public NotificationMessage(String title, String packageName, String content, String extraContent, long time, String tickerText) {
        this.title = title;
        this.packageName = packageName;
        this.tickerText = tickerText;
        this.content = content;
        this.extraContent = extraContent;
        this.time = time;
    }

    public static RealmResults<NotificationMessage> findAll(Realm realm) {
        return realm.where(NotificationMessage.class).findAll().sort("time");
    }


    public static RealmResults<NotificationMessage> findReadable(Realm realm, int threshold) {
        return realm.where(NotificationMessage.class)
                .beginGroup()
                    .greaterThanOrEqualTo("importance", threshold)
                    .equalTo("read", false)
                .endGroup()
                .findAllSorted("importance", Sort.DESCENDING);
    }


    public static void clearAll(Realm realm) {
        realm.beginTransaction();
        realm.delete(NotificationMessage.class);
        realm.commitTransaction();
    }

    public static void initMessages(StatusBarNotification statusBarNotification){

        Bundle extra = statusBarNotification.getNotification().extras;
        String androidText = "", title = "", multiline = "", ticker = "";
        if( extra != null ){
            if(extra.getCharSequence("android.text") != null){
                androidText = extra.getCharSequence("android.text").toString();
                Log.e(TAG, "android text "+ androidText );
            }
            if(extra.getCharSequence("android.title") != null){
                title = extra.getCharSequence("android.title").toString();
            }
            if(extra.getCharSequence("android.textLines") != null){
                multiline = extra.getCharSequence("android.textLines").length() +", "+ extra.getCharSequence("android.textLines");
                Log.e(TAG, "show: multiline     "+multiline );
            }
        }

        if(statusBarNotification.getNotification().tickerText != null){
            ticker = statusBarNotification.getNotification().tickerText.toString();
        }

        // cannot solve multi line problem in API 18
        // read one first and then dismiss
        Object content, extra_text;
        content =  extra.get(Notification.EXTRA_BIG_TEXT);
        extra_text =  extra.get(Notification.EXTRA_TEXT);

        String  contentText = "";
        if(content != null){
            contentText +=  content.toString();
            Log.e(TAG, "show: content, "+content.toString());
        }
        if(extra_text != null){
            contentText += extra_text.toString();
            Log.e(TAG, "show: test "+extra_text.toString());
        }


        NotificationMessage notificationMessage = new NotificationMessage(
                title
                , statusBarNotification.getPackageName()
                , androidText // content
                , contentText // extra
                , statusBarNotification.getPostTime()
                , ticker ); // ticker text

        Log.e(TAG, "ticker:  "+ ticker );
        Log.e(TAG, "tag:  "+ statusBarNotification.getTag() );
        notificationMessage.save();

        SelectionHelper.getInstance().process(notificationMessage);
    }

    public void save() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(this);
        realm.commitTransaction();
    }

    public String getTickerText() {
        return tickerText;
    }

    public void setTickerText(String tickerText) {
        this.tickerText = tickerText;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getContent() {
        return content;
    }

    public String getDetailContent() {
        return content+VerbalReminder.SPACE+tickerText;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getExtraContent() {
        return extraContent;
    }

    public void setExtraContent(String extraContent) {
        this.extraContent = extraContent;
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public void setHasRead(){
        setRead(true);
    }
}
