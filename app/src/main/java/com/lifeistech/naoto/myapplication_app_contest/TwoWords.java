package com.lifeistech.naoto.myapplication_app_contest;

import com.orm.SugarRecord;

/**
 * Created by naoto on 2017/06/04.
 */

public class TwoWords extends SugarRecord {
    //一単語づつで管理する
    private   String title;
    //グループの名前
    private String words_japanese;
     //wordの和訳
    private   String words_english;
    //wordのスペルの管理
    private String date;
    //登録した日付の管理
    //stringで管理する

    public TwoWords(){
        //普通のコンストラクタ
        //使うことはない
    }

    public TwoWords(String title, String words_japanese, String words_english, String date){
        this.title = title;
        this.words_japanese = words_japanese;
        this.words_english = words_english;
        this.date = date;
    }

    public String getJapanese(){return words_japanese;}

    public String getEnglish(){return  words_english;}

    public String getTitle(){return title;}

    public String getDate(){return date;}
}
