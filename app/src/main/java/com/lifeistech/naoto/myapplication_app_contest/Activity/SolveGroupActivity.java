package com.lifeistech.naoto.myapplication_app_contest.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lifeistech.naoto.myapplication_app_contest.Class.TwoWordsSet;
import com.lifeistech.naoto.myapplication_app_contest.R;
import com.lifeistech.naoto.myapplication_app_contest.Sugar.GroupTwoWords;
import com.lifeistech.naoto.myapplication_app_contest.Sugar.TwoWords;
import com.lifeistech.naoto.myapplication_app_contest.Sugar.TwoWordsAdd;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SolveGroupActivity extends AppCompatActivity {
    ArrayList<String> japaneses;
    ArrayList<String> englishes;
    ArrayList<String> ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve_group);
        Intent intentGet = getIntent();
        long id = intentGet.getLongExtra("id_group", 0);
        GroupTwoWords groupTwoWords = SugarRecord.findById(GroupTwoWords.class, id);
        List<TwoWords> listTwo = new ArrayList<>();
        long firdt_id = groupTwoWords.getFIRST_ID();
        for (int i = 0; i < groupTwoWords.getSIZE(); i++, firdt_id++) {
            TwoWords twoWords = SugarRecord.findById(TwoWords.class, firdt_id);
            if (twoWords.getJapanese() == null) {
                break;
            } else {
               listTwo.add(twoWords);
            }
        }
        List<TwoWordsAdd> listAdd = TwoWordsAdd.listAll(TwoWordsAdd.class);
        for (TwoWordsAdd twoWordsAdd : listAdd) {
            if (twoWordsAdd.getSubTitle().equals(groupTwoWords.getGROUP_NAME())) {
                TwoWords twoWords = TwoWords.findById(TwoWords.class,twoWordsAdd.getTwo_words_id());
                listTwo.add(twoWords);
            }
        }
        Collections.shuffle(listTwo);
        japaneses = new ArrayList<>();
        englishes = new ArrayList<>();
        ids = new ArrayList<>();
        for (TwoWords twoWords:listTwo){
            if(japaneses == null){
                break;
            }else {
                japaneses.add(twoWords.getJapanese());
                englishes.add(twoWords.getEnglish());
                ids.add(String.valueOf(twoWords.getId()));
            }
        }
        Intent intent = new Intent(this, Solve2Activity.class);
        intent.putExtra("japaneses", japaneses);
        intent.putExtra("englishes", englishes);
        intent.putExtra("number", 0);
        intent.putExtra("ids", ids);
        intent.putExtra("mode", 2);
        startActivity(intent);
    }
}
