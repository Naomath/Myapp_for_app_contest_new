package com.lifeistech.naoto.myapplication_app_contest;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class AnswerActivity extends AppCompatActivity {

    //問題の答を表示するActivity
    int test;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        TextView textView = (TextView) findViewById(R.id.textView3);
        textView.setPaintFlags(textView.getPaintFlags()
                | Paint.UNDERLINE_TEXT_FLAG);

    }

    public void know(View view) {
        //わかっていた時の処理
        Intent intent = new Intent(AnswerActivity.this, SolveActivity.class);
        intent.putExtra("test", test);
        Intent intent1 = new Intent(AnswerActivity.this, QuestionEndActivity.class);
        startActivity(intent1);

    }

    public void dont_know(View view) {
        //わからなかった時の処理
        Intent intent = new Intent(AnswerActivity.this, SolveActivity.class);
        intent.putExtra("test", test);
        Intent intent1 = new Intent(AnswerActivity.this, QuestionEndActivity.class);
        startActivity(intent1);


    }
}
