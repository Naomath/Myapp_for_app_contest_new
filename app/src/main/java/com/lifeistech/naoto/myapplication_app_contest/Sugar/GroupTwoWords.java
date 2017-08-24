package com.lifeistech.naoto.myapplication_app_contest.Sugar;

import com.orm.SugarRecord;

import java.util.ArrayList;

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

    public GroupTwoWords() {
    }
    //普通のコンストラクタ

    public GroupTwoWords(String GROUP_NAME, int SIZE, long FIRST_ID) {
        this.GROUP_NAME = GROUP_NAME;
        this.SIZE = SIZE;
        this.FIRST_ID = FIRST_ID;
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

    public ArrayList<TwoWords> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<TwoWords> arrayList) {
        this.arrayList = arrayList;
    }

}
