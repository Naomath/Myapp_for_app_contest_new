package com.lifeistech.naoto.myapplication_app_contest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lifeistech.naoto.myapplication_app_contest.R;
import com.lifeistech.naoto.myapplication_app_contest.sugar.TwoWordsWeak;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SolveWeakActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve_weak);
        List<TwoWordsWeak> list = SugarRecord.listAll(TwoWordsWeak.class);
        Collections.shuffle(list);
        ArrayList<String> japaneses = new ArrayList<>();
        ArrayList<String> englishes = new ArrayList<>();
        ArrayList<String> ids = new ArrayList<>();
        Collections.shuffle(list);
        for (TwoWordsWeak twoWordsWeak : list) {
            japaneses.add(twoWordsWeak.getWORDS_JAPANESE());
            englishes.add(twoWordsWeak.getWORDS_ENGLISH());
            ids.add(String.valueOf(twoWordsWeak.getId()));
        }
        Intent intent = new Intent(SolveWeakActivity.this, Solve2Activity.class);
        intent.putExtra("japaneses", japaneses);
        intent.putExtra("englishes", englishes);
        intent.putExtra("ids", ids);
        intent.putExtra("number", 0);
        intent.putExtra("mode", 1);
        startActivity(intent);

    }
}
