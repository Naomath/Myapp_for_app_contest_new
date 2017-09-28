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
    private int numberOfSolve;
    //何回解いたか
    private int numberOfWeak;
    //何回間違えたか
    private long weakId;
    //誤答率が70%以上の時のweakのid
    private long groupId;
    //属しているグループのid
    private int weakDecisioon;
    //weakに属しているかどうか


    public TwoWords() {
        //普通のコンストラクタ
        //使うことはない
    }

    public TwoWords(String title, String words_japanese, String words_english, String date, long groupId) {
        this.title = title;
        this.words_japanese = words_japanese;
        this.words_english = words_english;
        this.date = date;
        this.groupId = groupId;
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


    public int getNumberOfSolve() {
        return numberOfSolve;
    }

    public int getNumberOfWeak() {
        return numberOfWeak;
    }

    public long getWeakId() {
        return weakId;
    }

    public long getGroupId() {
        return groupId;
    }

    public int getWeakDecisioon() {
        return weakDecisioon;
    }

    public int getPercent() {
        float percent = this.numberOfWeak / numberOfSolve;
        int percentInteger = (int) percent * 100;
        return percentInteger;
    }

    public void setJapanese(String words_japanese) {
        this.words_japanese = words_japanese;
    }

    public void setEnglish(String words_english) {
        this.words_english = words_english;
    }


    public void setDate(String date) {
        this.date = date;
    }

    public void setNumberOfSolve(int numberOSolve) {
        this.numberOfSolve = numberOSolve;
    }

    public void setNumberOfWeak(int numberOfWeak) {
        this.numberOfWeak = numberOfWeak;
    }

    public void setWeakId(long weakId) {
        this.weakId = weakId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public void setWeakDecisioon(int weakDecisioon) {
        this.weakDecisioon = weakDecisioon;
    }
}
