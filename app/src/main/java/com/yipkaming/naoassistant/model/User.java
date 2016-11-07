package com.yipkaming.naoassistant.model;

/**
 * Created by Yip on 7/11/2016.
 */

public class User {

    private final String[] GENDERS = {"Male", "Female"};
    private String name;
    private String gender;
    private int age;
    private String city;
    private String interests;

    public User(String name, String gender, int age, String city, String interests) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.city = city;
        this.interests = interests;
    }
}
