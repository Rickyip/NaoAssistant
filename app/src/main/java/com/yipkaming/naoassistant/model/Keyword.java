package com.yipkaming.naoassistant.model;

import com.yipkaming.naoassistant.NaoAssistant;

import java.io.IOException;
import java.io.InputStream;

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
    public static final String NOUN = "Noun";

    //word combination
    public static final String SINGLE_WORD = "Single word";
    public static final String BIGRAM = "Bigram";
    public static final String TRIGRAM = "Trigram";

    @PrimaryKey
    private int id;
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
        clearAll(realm);

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                try {
                    InputStream inputStream = NaoAssistant.getContext().getAssets().open("keywords.json");
                    realm.createOrUpdateAllFromJson(Keyword.class, inputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static RealmResults<Keyword> findAll(Realm realm) {
        return realm.where(Keyword.class).findAll().sort("id");
    }

    public static boolean hasKeyword(String word) {
        RealmResults<Keyword> keywordRealmResults = Realm.getDefaultInstance()
                .where(Keyword.class)
                .equalTo("value", word)
                .findAll()
                .sort("id");
        return !keywordRealmResults.isEmpty();
    }

    public static Keyword getKeywordFromString(String word){
        return Realm.getDefaultInstance()
                .where(Keyword.class)
                .equalTo("value", word)
                .findFirst();
    }

    public void save(Realm realm) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(this);
        realm.commitTransaction();
//        return keyword;
    }

    public static void clearAll(Realm realm) {
        realm.beginTransaction();
        realm.delete(Keyword.class);
        realm.commitTransaction();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }
}
