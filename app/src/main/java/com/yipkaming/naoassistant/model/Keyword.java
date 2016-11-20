package com.yipkaming.naoassistant.model;


import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Yip on 7/11/2016.
 */

public class Keyword extends RealmObject {

    //word type
    public static final String SEARCH = "Search Word";
    public static final String PACKAGE_NAME = "Package Name";
    public static final String COMMAND = "Command";
    public static final String QUESTION = "Question";
    public static final String NOUN = "Normal noun";

    //word combination
    public static final String SINGLE_WORD = "Single word";
    public static final String BIGRAM = "Bigram";
    public static final String TRIGRAM = "Trigram";

    @PrimaryKey
    private int id;
//    private static List<Keyword> keywordList = new ArrayList<Keyword>();
    private String value;
    private String wordType;
    private String wordCombination;
    private String category;
    private int importance; // 5 is highest 1 is lowest

    public Keyword() {
    }

    public Keyword(int id, String value, String wordType, String wordCombination, String category, int importance) {
        this.id = id;
        this.value = value;
        this.wordType = wordType;
        this.wordCombination = wordCombination;
        this.category = category;
        this.importance = importance;
    }

    public static void init(){
        Realm realm = Realm.getDefaultInstance();
        int i = 0;
        new Keyword(i++, "com.whatsapp", Keyword.PACKAGE_NAME, Keyword.SINGLE_WORD, "", 4).save(realm);
        new Keyword(i++, "android", Keyword.PACKAGE_NAME, Keyword.SINGLE_WORD, "", 1).save(realm);
        new Keyword(i++, "com.android.systemui", Keyword.PACKAGE_NAME, Keyword.SINGLE_WORD, "", 1).save(realm);
        new Keyword(i++, "com.android.vending", Keyword.PACKAGE_NAME, Keyword.SINGLE_WORD, "", 1).save(realm);
        new Keyword(i++, "com.google.android.gm", Keyword.PACKAGE_NAME, Keyword.SINGLE_WORD, "", 3).save(realm);
        new Keyword(i++, "com.google.android.calendar", Keyword.PACKAGE_NAME, Keyword.SINGLE_WORD, "", 5).save(realm);
//        new Keyword(i++, "com.google.android.gm", Keyword.PACKAGE_NAME, Keyword.SINGLE_WORD, "", 3).save(realm);

    }

    public static RealmResults<Keyword> findAll(Realm realm) {
        return realm.where(Keyword.class).findAll().sort("id");
    }

    public static boolean findKeyWord(Realm realm, String word) {
        RealmResults<Keyword> keywordRealmResults = realm.where(Keyword.class).equalTo("value", word).findAll().sort("id");
        return !keywordRealmResults.isEmpty();
    }

    public Keyword save(Realm realm) {
        realm.beginTransaction();
        Keyword keyword = realm.copyToRealmOrUpdate(this);
        realm.commitTransaction();
        return keyword;
    }

    public static void clearAll(Realm realm) {
        realm.beginTransaction();
        realm.delete(Keyword.class);
        realm.commitTransaction();
    }

}
