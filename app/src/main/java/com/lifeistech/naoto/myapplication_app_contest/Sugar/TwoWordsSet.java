package com.lifeistech.naoto.myapplication_app_contest.Sugar;

/**
 * Created by naoto on 2017/08/26.
 */

public class TwoWordsSet {

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private long id;

    public String getJapanese() {
        return japanese;
    }

    public void setJapanese(String japanese) {
        this.japanese = japanese;
    }

    private String japanese;

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String englishes) {
        this.english = english;
    }

    private String english;

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    private int mode;
    //0がTwoWords
    //1がTwoWordsSet

    public TwoWordsSet() {
    }

    public TwoWordsSet(long id, String japanese, String english, int mode) {
        this.id = id;
        this.japanese = japanese;
        this.english = english;
        this.mode = mode;
    }
}
