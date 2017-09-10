package com.lifeistech.naoto.myapplication_app_contest.activity;

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

import com.lifeistech.naoto.myapplication_app_contest.R;
import com.lifeistech.naoto.myapplication_app_contest.sugar.GroupTwoWords;
import com.lifeistech.naoto.myapplication_app_contest.sugar.TwoWords;
import com.lifeistech.naoto.myapplication_app_contest.sugar.TwoWordsWeak;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.Calendar;

public class AnswerActivity extends AppCompatActivity {

    //問題の答を表示するActivity
    ArrayList<String> g_japaneses;
    ArrayList<String> g_englishes;
    ArrayList<String> g_ids;
    int g_number;
    int g_mode;
    long g_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        Intent intent = getIntent();
        g_japaneses = intent.getStringArrayListExtra("japaneses");
        g_englishes = intent.getStringArrayListExtra("englishes");
        g_ids = intent.getStringArrayListExtra("ids");
        g_number = intent.getIntExtra("number", 0);
        g_mode = intent.getIntExtra("mode", 0);
        g_id = intent.getLongExtra("id_group", 0);
        TextView textView = (TextView) findViewById(R.id.textView3);
        SharedPreferences pref = getSharedPreferences("question_mode", MODE_PRIVATE);
        g_mode = pref.getInt("question_mode", 0);
        if (g_mode == 0) {
            //先に和訳
            textView.setText(g_englishes.get(g_number));
        } else {
            //先にスペル
            textView.setText(g_japaneses.get(g_number));
        }
    }

    public void know(View view) {
        //わかっていた時の処理
        if (g_number + 1 == g_japaneses.size()) {
            showDialogEnd();
        } else {
            Intent intent = new Intent(AnswerActivity.this, Solve2Activity.class);
            intent.putExtra("japaneses", g_japaneses);
            intent.putExtra("englishes", g_englishes);
            intent.putExtra("number", g_number + 1);
            startActivity(intent);
        }

    }

    public void dont_know(View view) {
        //わからなかった時の処理
        //TwowordsWeakの登録
        if (g_mode == 0) {
            String str = g_ids.get(g_number);
            long id = Long.parseLong(str);
            TwoWords twoWords = SugarRecord.findById(TwoWords.class, id);
            if (twoWords.getWeak() == 1) {
                //もうすでに間違えている場合の処理
                showDialogEnd();
            } else {
                twoWords.setWeak(1);
                twoWords.save();
                TwoWordsWeak twoWordsWeak = new TwoWordsWeak(g_japaneses.get(g_number), g_englishes.get(g_number));
                twoWordsWeak.save();
                SharedPreferences preferences = getSharedPreferences("weak_id",MODE_PRIVATE);
                long weak_id = preferences.getLong("weak_id",0);
                GroupTwoWords groupTwoWords = GroupTwoWords.findById(GroupTwoWords.class, weak_id);
                Calendar calendar1 = Calendar.getInstance();
                int year = calendar1.get(Calendar.YEAR);
                int month = calendar1.get(Calendar.MONTH);
                int day_str = calendar1.get(Calendar.DAY_OF_MONTH);
                StringBuffer buf3 = new StringBuffer();
                buf3.append(String.valueOf(year));
                buf3.append("-");
                buf3.append(String.valueOf(month+1));
                buf3.append("/");
                buf3.append(String.valueOf(day_str));
                groupTwoWords.setCalendar(buf3.toString());
                groupTwoWords.save();
            }

        }
        //画面遷移
        if (g_number + 1 == g_japaneses.size()) {
            showDialogEnd();
        } else {
            Intent intent = new Intent(AnswerActivity.this, Solve2Activity.class);
            intent.putExtra("japaneses", g_japaneses);
            intent.putExtra("englishes", g_englishes);
            intent.putExtra("number", g_number + 1);
            startActivity(intent);
        }
    }

    public void thinkMode() {
        if (g_mode == 0) {
            Intent intent = new Intent(AnswerActivity.this, SolveActivity.class);
            startActivity(intent);
        } else if (g_mode == 1) {
            Intent intent = new Intent(AnswerActivity.this, SolveWeakActivity.class);
            startActivity(intent);
        } else if (g_mode == 2) {
            Intent intent = new Intent(AnswerActivity.this, SolveGroupActivity.class);
            intent.putExtra("id_group", g_id);
            startActivity(intent);
        }
    }

    public void thinkModeEnd() {
        if (g_mode == 2) {
            Intent intent = new Intent(this, ListGroupWordsActivity.class);
            intent.putExtra("ID_GROUP_TWOWORDS", g_id);
            startActivity(intent);
        } else {
            Intent intent = new Intent(AnswerActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void showDialogEnd() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("終わり");
        builder.setMessage("どうしますか？");
        builder.setNeutralButton("終了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                thinkModeEnd();
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
    }
}