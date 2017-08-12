package com.lifeistech.naoto.myapplication_app_contest;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;

public class SolveActivity extends AppCompatActivity {

    //問題を解く画面の提供
    int test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve);
        Calendar calendar = Calendar.getInstance();
        Calendar [] calendars = new Calendar[4];
        //この場合、今日、昨日、9日前、35日前になる
        calendars[0] = calendar;
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        calendars[1] = calendar;
        calendar.add(Calendar.DAY_OF_YEAR, -8);
        calendars[2] = calendar;
        calendar.add(Calendar.DAY_OF_YEAR, -27);
        calendars[3] = calendar;


    }

    public void go_to_answer(View view){
        // 答えの画面に行く処理
        Intent intent = new Intent(SolveActivity.this, AnswerActivity.class);
        startActivity(intent);

    }
}
