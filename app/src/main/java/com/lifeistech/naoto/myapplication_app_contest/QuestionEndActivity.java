package com.lifeistech.naoto.myapplication_app_contest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class QuestionEndActivity extends AppCompatActivity {

    //問題が終わった後に来る画面

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_end);
    }

    public void go_to_home(View view){
        //HOME画面に行く
        Intent intent = new Intent(QuestionEndActivity.this,MainActivity.class);
        startActivity(intent);
    }
}
