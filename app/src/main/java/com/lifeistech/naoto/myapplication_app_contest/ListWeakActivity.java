package com.lifeistech.naoto.myapplication_app_contest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.orm.SugarRecord;

import java.util.List;

public class ListWeakActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_weak);
        ListView listView = (ListView)findViewById(R.id.listView_LW);
        ListWeakAdapter adapter = new ListWeakAdapter(this, R.layout.list_set_up_weak_adapter);
        listView.setAdapter(adapter);
        List<TwoWordsWeak> list = SugarRecord.listAll(TwoWordsWeak.class);
        for(TwoWordsWeak twoWordsWeak:list){
            adapter.add(twoWordsWeak);
        }
    }
}
