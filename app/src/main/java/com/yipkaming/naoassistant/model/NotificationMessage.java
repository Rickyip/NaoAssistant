package com.yipkaming.naoassistant.model;

/**
 * Created by Yip on 7/11/2016.
 */

public class NotificationMessage {
    // this class can combine info from bundle.extra and notification
    private String title;
    private String packageName;
    private String tickerText;
    private String content;
    private String tag;
    private long time;
    private int importance = 0;

    public NotificationMessage(String title, String packageName, String content, String tag, long time, String tickerText) {
        this.title = title;
        this.packageName = packageName;
        this.tickerText = tickerText;
        this.content = content;
        this.tag = tag;
        this.time = time;
    }

    public void save(){}

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
