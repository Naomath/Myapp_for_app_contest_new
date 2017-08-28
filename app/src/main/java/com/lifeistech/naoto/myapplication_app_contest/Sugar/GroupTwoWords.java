package com.lifeistech.naoto.myapplication_app_contest.Sugar;

import com.google.firebase.database.Exclude;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by naoto on 2017/07/23.
 */

public class GroupTwoWords extends SugarRecord {
    private String GROUP_NAME;
    //グループの名前
    private int SIZE;
    // リストのサイズ
    private long FIRST_ID;
    //最初のid
    private ArrayList<TwoWords> arrayList;
    //firebase専用
    private Calendar calendar;
    //登録した時の時間
    private  String maker;
    //作成者

    public GroupTwoWords() {
    }
    //普通のコンストラクタ

    public GroupTwoWords(String GROUP_NAME, int SIZE, long FIRST_ID, Calendar calendar, String maker) {
        this.GROUP_NAME = GROUP_NAME;
        this.SIZE = SIZE;
        this.FIRST_ID = FIRST_ID;
        this.calendar = calendar;
        this.maker = maker;
    }

    public String getGROUP_NAME() {
        return GROUP_NAME;
    }

    public int getSIZE() {
        return SIZE;
    }

    public long getFIRST_ID() {
        return FIRST_ID;
    }

    public Calendar getCalendar(){return calendar;}

    public String getMaker(){return maker;}

    public ArrayList<TwoWords> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<TwoWords> arrayList) {
        this.arrayList = arrayList;
    }

    public void setCalendar(Calendar calendar){
        this.calendar = calendar;
    }

    public void setGROUP_NAME(String GROUP_NAME) {
        this.GROUP_NAME = GROUP_NAME;
    }

}
