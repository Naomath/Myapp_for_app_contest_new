package com.lifeistech.naoto.myapplication_app_contest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class Solve2Activity extends AppCompatActivity {

    ArrayList<String> japaneses;
    ArrayList<String> englishes;
    ArrayList<String> ids;
    int number;
    int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve2);
        Intent intent = getIntent();
        japaneses = intent.getStringArrayListExtra("japaneses");
        englishes = intent.getStringArrayListExtra("englishes");
        ids = intent.getStringArrayListExtra("ids");
        number = intent.getIntExtra("number", 0);
        mode = intent.getIntExtra("mode", 0);
        TextView textView = (TextView) findViewById(R.id.textView);
        SharedPreferences pref = getSharedPreferences("question_mode", MODE_PRIVATE);
        int mode = pref.getInt("question_mode",0);
        if(mode == 0){
            //先に和訳
            textView.setText(japaneses.get(number));
        }else {
            //先にスペル
            textView.setText(englishes.get(number));
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    public void go_to_answer(View view) {
        //次に行く時のボタン
        Intent intent = new Intent(this, AnswerActivity.class);
        intent.putExtra("japaneses", japaneses);
        intent.putExtra("englishes", englishes);
        intent.putExtra("ids", ids);
        intent.putExtra("number", number);
        intent.putExtra("mode", mode);
        startActivity(intent);
    }
}