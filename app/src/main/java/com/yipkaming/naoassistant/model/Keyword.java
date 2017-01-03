package com.yipkaming.naoassistant.model;

import android.database.Cursor;
import android.provider.ContactsContract;

import com.yipkaming.naoassistant.NaoAssistant;

import java.io.IOException;
import java.io.InputStream;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.Index;
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
    public static final String REMINDER = "Reminder";

    //word combination
    public static final String SINGLE_WORD = "Single word";
    public static final String BIGRAM = "Bigram";
    public static final String TRIGRAM = "Trigram";
    public static final String ARRAY_OF_WORDS = "Array of words";

    //word category
    public static final String SYSTEM = "System";
    public static final String SCHEDULE = "Schedule";
    public static final String COMMUNICATION = "Communication";
    public static final String CONTACT = "Contact";

    @PrimaryKey
    @Index
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

        readKeywordFromJson(realm);
        readContactList(realm);
    }

    private static void readContactList(Realm realm) {
        Cursor phones = NaoAssistant.getContext().getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        int contactIndex = 100000;   // Starting index from 100000 to be distinguished from the normal keywords
        assert phones != null;
        while (phones.moveToNext()) {
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            Keyword keyword = new Keyword(contactIndex++, name, NOUN, ARRAY_OF_WORDS, CONTACT, 5);
            // for communication purposes, the importance is set to be highest
            keyword.save(realm);
        }
        phones.close();
    }

    private static void readKeywordFromJson(Realm realm) {
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
