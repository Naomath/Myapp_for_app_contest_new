package com.lifeistech.naoto.myapplication_app_contest;

/**
 * Created by naoto on 2017/06/18.
 */

public class TwoWordsForSet {
    private   String words_japanese;
    //wordの和訳
    private String words_english;
    //wordのスペルの管理

    public TwoWordsForSet(){
        //普通のコンストラクタ
        //使うことはない
    }

    public TwoWordsForSet(String words_japanese, String words_english){
        //使うコンストラクタ
        this.words_japanese = words_japanese;
        this.words_english = words_english;
    }

    public String getJapanese(){

        return words_japanese;
    }

    public String getEnglish(){
        return  words_english;
    }


}
