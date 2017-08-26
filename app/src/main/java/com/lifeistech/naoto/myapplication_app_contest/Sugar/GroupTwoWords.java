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
   // private int ADD;
    //単語が追加されたかどうか
    //0だったら追加されてない
    //デフォルトは0

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

   // public int getADD() {
   //     return ADD;
  //  }

    public void setArrayList(ArrayList<TwoWords> arrayList) {
        this.arrayList = arrayList;
    }

  //  public void setADD(int ADD) {
   //     this.ADD = ADD;
   // }
}
