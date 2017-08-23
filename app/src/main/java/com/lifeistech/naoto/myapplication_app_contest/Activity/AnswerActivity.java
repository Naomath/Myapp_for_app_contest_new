package com.lifeistech.naoto.myapplication_app_contest.Activity;

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
import com.lifeistech.naoto.myapplication_app_contest.Sugar.TwoWords;
import com.lifeistech.naoto.myapplication_app_contest.Sugar.TwoWordsWeak;
import com.orm.SugarRecord;

import java.util.ArrayList;

public class AnswerActivity extends AppCompatActivity {

    //問題の答を表示するActivity
    ArrayList<String> japaneses;
    ArrayList<String> englishes;
    ArrayList<String> ids;
    int number;
    int mode;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        Intent intent = getIntent();
        japaneses = intent.getStringArrayListExtra("japaneses");
        englishes = intent.getStringArrayListExtra("englishes");
        ids = intent.getStringArrayListExtra("ids");
        number = intent.getIntExtra("number", 0);
        mode = intent.getIntExtra("mode", 0);
        id = intent.getLongExtra("id_group", 0);
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
            show_dialog_end();
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
        if (mode == 0) {
            long id = Long.parseLong(ids.get(number));
            TwoWords twoWords = SugarRecord.findById(TwoWords.class, id);
            if (twoWords.getWeak() == 1) {
                //もうすでに間違えている場合の処理
                show_dialog_end();
            } else {
                twoWords.setWeak(1);
                twoWords.save();
                TwoWordsWeak twoWordsWeak = new TwoWordsWeak(japaneses.get(number), englishes.get(number));
                twoWordsWeak.save();
            }

        }
        //画面遷移
        if (number + 1 == japaneses.size()) {
            show_dialog_end();
        } else {
            Intent intent = new Intent(AnswerActivity.this, Solve2Activity.class);
            intent.putExtra("japaneses", japaneses);
            intent.putExtra("englishes", englishes);
            intent.putExtra("number", number + 1);
            startActivity(intent);
        }
    }

    public void thinkMode() {
        if (mode == 0) {
            Intent intent = new Intent(AnswerActivity.this, SolveActivity.class);
            startActivity(intent);
        } else if (mode == 1) {
            Intent intent = new Intent(AnswerActivity.this, SolveWeakActivity.class);
            startActivity(intent);
        } else if (mode == 2) {
            Intent intent = new Intent(AnswerActivity.this, SolveGroupActivity.class);
            intent.putExtra("id_group", id);
            startActivity(intent);
        }
    }

    public void thinkModeEnd() {
        if (mode == 2) {
            Intent intent = new Intent(this, ListGroupWordsActivity.class);
            intent.putExtra("ID_GROUP_TWOWORDS", id);
            startActivity(intent);
        } else {
            Intent intent = new Intent(AnswerActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void show_dialog_end() {
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
