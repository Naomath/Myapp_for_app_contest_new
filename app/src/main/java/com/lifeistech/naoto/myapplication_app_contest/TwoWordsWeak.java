package com.lifeistech.naoto.myapplication_app_contest;

import com.orm.SugarRecord;

/**
 * Created by naoto on 2017/08/13.
 */

public class TwoWordsWeak extends SugarRecord {
    //一単語づつで管理する
    private String TITLE;
    //グループの名前
    private String WORDS_JAPANESE;
    //wordの和訳
    private String WRDS_ENGLISH;
    //wordのスペルの管理
    private int SIZE;
    //なん個目に登録したか

    public TwoWordsWeak() {
        //普通のコンストラクタ
        //使うことはない
    }

    public TwoWordsWeak(String TITLE, String WORDS_JAPANESE, String WORDS_ENGLISH, int SIZE) {
        this.TITLE = TITLE;
        this.WORDS_JAPANESE = WORDS_JAPANESE;
        this.WRDS_ENGLISH = WORDS_ENGLISH;
        this.SIZE = SIZE;
    }

    public String getTITLE() {
        return TITLE;
    }

    public String getWORDS_JAPANESE() {
        return WORDS_JAPANESE;
    }

    public String getWRDS_ENGLISH() {
        return WRDS_ENGLISH;
    }

    public int getSIZE() {
        return SIZE;
    }
}
