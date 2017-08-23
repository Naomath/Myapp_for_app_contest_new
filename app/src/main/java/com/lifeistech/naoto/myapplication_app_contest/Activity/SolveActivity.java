package com.lifeistech.naoto.myapplication_app_contest.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lifeistech.naoto.myapplication_app_contest.Sugar.TwoWords;
import com.orm.SugarRecord;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class SolveActivity extends AppCompatActivity {

    //問題を解く画面の提供

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Calendar calendar = Calendar.getInstance();
        Calendar[] calendars = new Calendar[4];
        String calendar_str[] = new String[4];
        //この場合、今日、昨日、9日前、35日前になる
        calendars[0] = calendar;
        calendar_str[0] = make_string_from_calendar(calendars[0]);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        calendars[1] = calendar;
        calendar_str[1] = make_string_from_calendar(calendars[1]);
        calendar.add(Calendar.DAY_OF_YEAR, -8);
        calendars[2] = calendar;
        calendar_str[2] = make_string_from_calendar(calendars[2]);
        calendar.add(Calendar.DAY_OF_YEAR, -27);
        calendars[3] = calendar;
        calendar_str[3] = make_string_from_calendar(calendars[3]);
        //年越しについては心配する必要はない
        //文字列の設定をしている
        //次はTwoWordsの呼び出し
        List<TwoWords> twoWordses = new ArrayList<>();
        ArrayList<String> japaneses = new ArrayList<>();
        ArrayList<String> englishes = new ArrayList<>();
        ArrayList<String> ids = new ArrayList<>();
        List<TwoWords> list = SugarRecord.listAll(TwoWords.class);
        for (TwoWords twoWords : list) {
            String date_twowords = twoWords.getDate();
            if (date_twowords.startsWith(calendar_str[0])) {
                twoWordses.add(twoWords);
            } else if (date_twowords.startsWith(calendar_str[1])) {
                twoWordses.add(twoWords);
            } else if (date_twowords.startsWith(calendar_str[2])) {
                twoWordses.add(twoWords);
            } else if (date_twowords.startsWith(calendar_str[3])) {
                twoWordses.add(twoWords);
            }
        }
        if(twoWordses.size() == 0){
            show_dialog_end();
        }else {
            Collections.shuffle(twoWordses);
            for(TwoWords twoWords:twoWordses){
                japaneses.add(twoWords.getJapanese());
                englishes.add(twoWords.getEnglish());
                ids.add(String.valueOf(twoWords.getId()));
            }
            //プリファレンスの設定
            Intent intent = new Intent(SolveActivity.this, Solve2Activity.class);
            intent.putExtra("japaneses", japaneses);
            intent.putExtra("englishes", englishes);
            intent.putExtra("ids", ids);
            intent.putExtra("number", 0);
            intent.putExtra("mode", 0);
            startActivity(intent);
        }
    }

    public String make_string_from_calendar(Calendar calendar) {
        int year = calendar.get(Calendar.YEAR);
        int day = calendar.get(Calendar.DAY_OF_YEAR);
        String year_str = Integer.toString(year);
        String day_str = Integer.toString(day);
        StringBuffer buf = new StringBuffer();
        buf.append(year_str);
        buf.append(day_str);
        String calendar_str = buf.toString();
        return calendar_str;
    }
    public void show_dialog_end(){
        Intent intent = new Intent(SolveActivity.this, MainActivity.class);
        intent.putExtra("end",1);
        startActivity(intent);
    }

}
