package com.lifeistech.naoto.myapplication_app_contest;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class AnswerActivity extends AppCompatActivity {

    //問題の答を表示するActivity
    ArrayList<String> japaneses;
    ArrayList<String> englishes;
    int number;
    int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        Intent intent = getIntent();
        japaneses = intent.getStringArrayListExtra("japaneses");
        englishes = intent.getStringArrayListExtra("englishes");
        number = intent.getIntExtra("number", 0);
        mode = intent.getIntExtra("mode", 0);
        TextView textView = (TextView) findViewById(R.id.textView3);
        SharedPreferences pref = getSharedPreferences("question_mode", MODE_PRIVATE);
        mode = pref.getInt("question_mode", 0);
        if (mode == 0) {
            //先に和訳
            textView.setText(englishes.get(number));
        } else {
            //先にスペル
            textView.setText(japaneses.get(number));
        }
    }

    public void know(View view) {
        //わかっていた時の処理
        if (number + 1 == japaneses.size()) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("終わり");
            builder.setMessage("どうしますか？");
            builder.setNeutralButton("終了", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(AnswerActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });
            builder.setPositiveButton("もう一度やる", new DialogInterface.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    thinkMode();
                }
            });
            builder.show();
        } else {
            Intent intent = new Intent(AnswerActivity.this, Solve2Activity.class);
            intent.putExtra("japaneses", japaneses);
            intent.putExtra("englishes", englishes);
            intent.putExtra("number", number + 1);
            startActivity(intent);
        }

    }

    public void dont_know(View view) {
        //わからなかった時の処理
        //TwowordsWeakの登録
        if(mode == 0){
            TwoWordsWeak twoWordsWeak = new TwoWordsWeak(japaneses.get(number), englishes.get(number));
            twoWordsWeak.save();
        }
        //画面遷移
        if (number + 1 == japaneses.size()) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("終わり");
            builder.setMessage("どうしますか？");
            builder.setNeutralButton("終了", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(AnswerActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });
            builder.setPositiveButton("もう一度やる", new DialogInterface.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    thinkMode();
                }
            });

            builder.show();
        } else {
            Intent intent = new Intent(AnswerActivity.this, Solve2Activity.class);
            intent.putExtra("japaneses", japaneses);
            intent.putExtra("englishes", englishes);
            intent.putExtra("number", number + 1);
            startActivity(intent);
        }
    }

    public void thinkMode(){
        if(mode == 0){
            Intent intent = new Intent(AnswerActivity.this, SolveActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(AnswerActivity.this, SolveWeakActivity.class);
            startActivity(intent);
        }
    }
}
