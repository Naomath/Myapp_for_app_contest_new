package com.lifeistech.naoto.myapplication_app_contest.sugar;

import com.orm.SugarRecord;

import java.util.ArrayList;

/**
 * Created by naoto on 2017/07/23.
 */

public class GroupTwoWords extends SugarRecord {
    private String groupName;
    //グループの名前
    private int size;
    // リストのサイズ
    private long firstId;
    //最初のid
    private ArrayList<TwoWords> arrayList;
    //firebase専用
    private String calendar;
    //登録した時の時間
    private String maker;
    //作成者
    private int down;
    //ダウンロードしたものは1である
    private String userId;
    //ユーザーid

    public GroupTwoWords() {
        //普通のコンストラクタ
    }
    public GroupTwoWords(String groupName){
        this.groupName = groupName;
    }
    public GroupTwoWords(String groupName, int size, long firstId, String calendar, String maker) {
        this.groupName = groupName;
        this.size = size;
        this.firstId = firstId;
        this.calendar = calendar;
        this.maker = maker;
    }

    public String getGroupName() {
        return groupName;
    }

    public int getSize() {
        return size;
    }

    public long getFirstId() {
        return firstId;
    }

    public String getCalendar() {
        return calendar;
    }

    public String getMaker() {
        return maker;
    }

    public int getDown() {
        return down;
    }

    public ArrayList<TwoWords> getArrayList() {
        return arrayList;
    }

    public String getUserId() {
        return userId;
    }

    public void setArrayList(ArrayList<TwoWords> arrayList) {
        this.arrayList = arrayList;
    }

    public void setCalendar(String calendar) {
        this.calendar = calendar;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public void setDown(int down) {
        this.down = down;
    }

    public void setFirstId(long firstId) {
        this.firstId = firstId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
