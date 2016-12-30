package com.yipkaming.naoassistant.model;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Yip on 7/11/2016.
 */

public class NotificationMessage extends RealmObject{

    // this class can combine info from bundle.extra and notification
    @PrimaryKey
    private long time;
    private String title;
    private String packageName;
    private String tickerText;
    private String content;
    private String tag;
    private boolean read;
    private int importance = 0;

    public NotificationMessage() {
    }

    public NotificationMessage(String title, String packageName, String content, String tag, long time, String tickerText) {
        this.title = title;
        this.packageName = packageName;
        this.tickerText = tickerText;
        this.content = content;
        this.tag = tag;
        this.time = time;
    }

    public static RealmResults<NotificationMessage> findAll(Realm realm) {
        return realm.where(NotificationMessage.class).findAll().sort("time");
    }


    public void save() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(this);
        realm.commitTransaction();
    }

    public static void clearAll(Realm realm) {
        realm.beginTransaction();
        realm.delete(NotificationMessage.class);
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

    public void setContent(String content) {
        this.content = content;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }
}
