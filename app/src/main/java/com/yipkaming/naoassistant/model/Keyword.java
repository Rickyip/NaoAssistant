package com.yipkaming.naoassistant.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yip on 7/11/2016.
 */

public class Keyword {
    //word type
    public final String SEARCH = "Search Word";
    public final String PACKAGE_NAME = "Package Name";
    public final String COMMAND = "Command";
    public final String QUESTION = "Question";
    public final String NOUN = "Normal noun";

    //word combination
    public final String SINGLE_WORD = "Single word";
    public final String BIGRAM = "Bigram";
    public final String TRIGRAM = "Trigram";



    private static List<Keyword> keywordList = new ArrayList<Keyword>();
    private String value;
    private String wordType;
    private String wordCombination;
    private String category;
    private int importance; // 5 is highest 1 is lowest

}
