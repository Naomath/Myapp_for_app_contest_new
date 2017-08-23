package com.lifeistech.naoto.myapplication_app_contest.Sugar;

import com.orm.SugarRecord;

/**
 * Created by naoto on 2017/08/13.
 */

public class TwoWordsWeak extends SugarRecord {
    //一単語づつで管理する
    private String WORDS_JAPANESE;
    //wordの和訳
    private String WORDS_ENGLISH;
    //wordのスペルの管理

    public TwoWordsWeak() {
        //普通のコンストラクタ
        //使うことはない
    }

    public TwoWordsWeak(String WORDS_JAPANESE, String WORDS_ENGLISH) {
        this.WORDS_JAPANESE = WORDS_JAPANESE;
        this.WORDS_ENGLISH = WORDS_ENGLISH;
    }

    public String getWORDS_JAPANESE() {
        return WORDS_JAPANESE;
    }

    public String getWORDS_ENGLISH() {
        return WORDS_ENGLISH;
    }

    public void setWORDS_JAPANESE(String WORDS_JAPANESE) {
        this.WORDS_JAPANESE = WORDS_JAPANESE;
    }

    public void setWORDS_ENGLISH(String WORDS_ENGLISH) {
        this.WORDS_ENGLISH = WORDS_ENGLISH;
    }
}
