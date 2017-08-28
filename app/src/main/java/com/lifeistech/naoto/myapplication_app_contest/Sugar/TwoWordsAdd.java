package com.lifeistech.naoto.myapplication_app_contest.Sugar;

import com.orm.SugarRecord;

/**
 * Created by naoto on 2017/08/26.
 */

public class TwoWordsAdd extends SugarRecord{
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
    private String subTitle;

    public long getTwo_words_id() {
        return two_words_id;
    }

    public void setTwo_words_id(long two_words_id) {
        this.two_words_id = two_words_id;
    }

    //追加登録の時に使う
    private long two_words_id;
    //TwoWordsのほうのid
    //消去に使う

    public TwoWordsAdd() {
        //普通のコンストラクタ
        //使うことはない
    }

    public TwoWordsAdd(String title, String words_japanese, String words_english, String date, String subTitle, long two_words_id) {
        this.title = title;
        this.words_japanese = words_japanese;
        this.words_english = words_english;
        this.date = date;
        weak = 0;
        this.subTitle = subTitle;
        this.two_words_id = two_words_id;
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

    public String getSubTitle() {
        return subTitle;
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
}
