package com.yipkaming.naoassistant.model;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Yip on 7/11/2016.
 */

public class User extends RealmObject {

    public static final String[] GENDERS = {"Male", "Female"};
    private static User instance;

    @PrimaryKey
    @Index
    private int id = 0;
    private String name;
    private String gender;
    private int age;
    private String city;
    private String interests;


    public User() {

        instance = this;
    }

    public User(String name, String gender, int age, String city, String interests) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.city = city;
        this.interests = interests;
        instance = this;
    }


    public void save(Realm realm) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(this);
        realm.commitTransaction();
    }

    public static void clearAll(Realm realm) {
        realm.beginTransaction();
        realm.delete(User.class);
        realm.commitTransaction();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        this.interests = interests;
        realm.commitTransaction();
        save(realm);
    }

    public static User getInstance() {
//        if( instance == null){
//            instance = new User();
//        }
        return instance;
    }

    public static void setInstance(User instance) {
        User.instance = instance;
    }
}
