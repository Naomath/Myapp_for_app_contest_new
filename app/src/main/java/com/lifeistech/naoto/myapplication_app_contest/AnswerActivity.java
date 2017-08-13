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
    ArrayList<String> ids;
    int number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        Intent intent = getIntent();
        japaneses = intent.getStringArrayListExtra("japaneses");
        englishes = intent.getStringArrayListExtra("englishes");
        ids = intent.getStringArrayListExtra("ids");
        number = intent.getIntExtra("number", 0);
        TextView textView = (TextView) findViewById(R.id.textView3);
        SharedPreferences pref = getSharedPreferences("question_mode", MODE_PRIVATE);
        int mode = pref.getInt("question_mode",0);
        if(mode == 0){
            //先に和訳
            textView.setText(englishes.get(number));
        }else {
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
            builder.setNeutralButton("やめる", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(AnswerActivity.this, Main2Activity.class);
                    startActivity(intent);
                }
            });
            builder.setPositiveButton("もう一度やる", new DialogInterface.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(AnswerActivity.this, SolveActivity.class);
                    startActivity(intent);
                    }
                });

            builder.show();
        } else {
            Intent intent = new Intent(AnswerActivity.this, Solve2Activity.class);
            intent.putExtra("japaneses", japaneses);
            intent.putExtra("englishes", englishes);
            intent.putExtra("ids", ids);
            intent.putExtra("number", number + 1);
            startActivity(intent);
        }

    }

    public void dont_know(View view) {
        //わからなかった時の処理
        //Twowordsの登録
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        String year_string = Integer.toString(year);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        String day_string = Integer.toString(day);
        StringBuffer buf = new StringBuffer();
        buf.append(year_string);
        buf.append(day_string);
        String date = buf.toString();
        SharedPreferences preferences = getSharedPreferences(date, MODE_PRIVATE);
        int number_of_day = preferences.getInt("number_of_day", 0);
        number_of_day++;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("number_of_day", number_of_day);
        editor.commit();
        SharedPreferences sharedPreferences = getSharedPreferences("Weak", MODE_PRIVATE);
        int number = preferences.getInt("number", 0);
        TwoWordsWeak twoWordsWeak = new TwoWordsWeak("間違えやすい", japaneses.get(number), englishes.get(number), number++);
        twoWordsWeak.save();
        SharedPreferences.Editor editor1 = preferences.edit();
        editor1.putInt("number", number++);
        editor.commit();
        //画面遷移
        if (number + 1 == japaneses.size()) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("終わり");
            builder.setMessage("どうしますか？");
            builder.setNeutralButton("やめる", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(AnswerActivity.this, Main2Activity.class);
                    startActivity(intent);
                }
            });
            builder.setPositiveButton("もう一度やる", new DialogInterface.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(AnswerActivity.this, SolveActivity.class);
                    startActivity(intent);
                }
            });

            builder.show();
        } else {
            Intent intent = new Intent(AnswerActivity.this, Solve2Activity.class);
            intent.putExtra("japanese", japaneses);
            intent.putExtra("englishes", englishes);
            intent.putExtra("ids", ids);
            intent.putExtra("number", number + 1);
            startActivity(intent);
        }

    }
}
