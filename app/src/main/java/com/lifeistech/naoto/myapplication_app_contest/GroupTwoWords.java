package com.lifeistech.naoto.myapplication_app_contest;

import com.orm.SugarApp;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by naoto on 2017/07/23.
 */

public class GroupTwoWords extends SugarRecord {
    private String GROUP_NAME;
    //グループの名前
    private List<TwoWordsForArray> LIST = new ArrayList<>();
    // リストのフィールド
    public GroupTwoWords(){}
        //普通のコンストラクタ

    public GroupTwoWords(String GROUP_NAME, List <TwoWords> LIST){
        for(int i =0;i<LIST.size();i++) {
           TwoWords twoWords = LIST.get(i);
            String TITLE = twoWords.getTitle();
            String JAPANESE = twoWords.getJapanese();
            String ENGLISH = twoWords.getEnglish();
            String DATE = twoWords.getDate();
            TwoWordsForArray twoWordsForArray = new TwoWordsForArray(TITLE, JAPANESE, ENGLISH, DATE);
            twoWordsForArray.save();
            this.LIST.add(twoWordsForArray);
        }
        this.GROUP_NAME = GROUP_NAME;
    }

    public String getGROUP_NAME(){return GROUP_NAME;}

    public List<TwoWordsForArray> getList(){return LIST;}
}
