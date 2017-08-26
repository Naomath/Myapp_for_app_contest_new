package com.lifeistech.naoto.myapplication_app_contest.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lifeistech.naoto.myapplication_app_contest.R;
import com.lifeistech.naoto.myapplication_app_contest.Sugar.GroupTwoWords;
import com.lifeistech.naoto.myapplication_app_contest.Sugar.TwoWords;
import com.lifeistech.naoto.myapplication_app_contest.Sugar.TwoWordsAdd;
import com.lifeistech.naoto.myapplication_app_contest.Sugar.TwoWordsSet;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SolveGroupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve_group);
        Intent intent = getIntent();
        long id = intent.getLongExtra("id_group", 0);
        GroupTwoWords groupTwoWords = SugarRecord.findById(GroupTwoWords.class, id);
        List<TwoWordsSet> listSet = new ArrayList<>();
        long firdt_id = groupTwoWords.getFIRST_ID();
        for (int i = 0; i < groupTwoWords.getSIZE(); i++, firdt_id++) {
            TwoWords twoWords = SugarRecord.findById(TwoWords.class, firdt_id);
            if (twoWords.getJapanese() == null) {
                break;
            } else {
                TwoWordsSet twoWordsSet = new TwoWordsSet(twoWords.getId(), twoWords.getJapanese(), twoWords.getEnglish(), 0);
                listSet.add(twoWordsSet);
            }
        }
        List<TwoWordsAdd> list = TwoWordsAdd.listAll(TwoWordsAdd.class);
        for (TwoWordsAdd twoWordsAdd : list) {
            if (twoWordsAdd.getSubTitle().equals(groupTwoWords.getGROUP_NAME())) {
                TwoWordsSet twoWordsSet = new TwoWordsSet(twoWordsAdd.getId(), twoWordsAdd.getJapanese(), twoWordsAdd.getEnglish(), 1);
                listSet.add(twoWordsSet);
            }
            Collections.shuffle(listSet);
            ArrayList<String> japaneses = new ArrayList<>();
            ArrayList<String> englishes = new ArrayList<>();
            ArrayList<String> ids = new ArrayList<>();
            for (TwoWordsSet twoWordsSet : listSet) {
                japaneses.add(twoWordsSet.getJapanese());
                englishes.add(twoWordsSet.getEnglish());
                ids.add(String.valueOf(twoWordsSet.getId()));
            }
            Intent intent1 = new Intent(this, Solve2Activity.class);
            intent.putExtra("japaneses", japaneses);
            intent.putExtra("englishes", englishes);
            intent.putExtra("number", 0);
            intent.putExtra("ids", ids);
            intent.putExtra("mode", 2);
            startActivity(intent);
        }
    }
}
