package com.lifeistech.naoto.myapplication_app_contest.sugar;

import com.orm.SugarRecord;

/**
 * Created by naoto on 2017/06/04.
 */

public class TwoWords extends SugarRecord {
    //一単語づつで管理する
    private String title;
    //グループの名前
    private String words_japanese;
    //wordの和訳
    private String words_english;
    //wordのスペルの管理
    private String date;
    //登録した日付の管理
    //stringで管理する
    private int weak;
    //間違えてない問題は0
    //間違えてる問題は1

    public TwoWords() {
        //普通のコンストラクタ
        //使うことはない
    }

    public TwoWords(String title, String words_japanese, String words_english, String date) {
        this.title = title;
        this.words_japanese = words_japanese;
        this.words_english = words_english;
        this.date = date;
        weak = 0;
    }

    public String getJapanese() {
        return words_japanese;
    }

    public String getEnglish() {
        return words_english;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public int getWeak() {
        return weak;
    }

    public void setJapanese(String words_japanese) {
        this.words_japanese = words_japanese;
    }

    public void setEnglish(String words_english) {
        this.words_english = words_english;
    }

    public void setWeak(int weak) {
        this.weak = weak;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
