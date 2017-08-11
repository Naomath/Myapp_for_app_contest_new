package com.lifeistech.naoto.myapplication_app_contest;

import com.orm.SugarRecord;

/**
 * Created by naoto on 2017/08/10.
 */

public class TwoWordsForArray extends SugarRecord {

    private String TITLE;

    private String JAPANESE;

    private String ENGLISH;

    private String DATE;

    public TwoWordsForArray(){
        //普通のコンストラクタ
    }

    public TwoWordsForArray(String TITLE, String JAPANESE, String ENGLISH, String DATE){
        this.TITLE = TITLE;
        this.JAPANESE = JAPANESE;
        this.ENGLISH = ENGLISH;
        this.DATE = DATE;
    }

    public String getJAPANESE(){return JAPANESE;}

    public String getENGLISH(){return  ENGLISH;}


}
