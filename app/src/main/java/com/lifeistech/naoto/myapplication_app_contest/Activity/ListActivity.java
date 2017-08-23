package com.lifeistech.naoto.myapplication_app_contest.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.lifeistech.naoto.myapplication_app_contest.adapters.ListSetUp;
import com.lifeistech.naoto.myapplication_app_contest.R;
import com.lifeistech.naoto.myapplication_app_contest.Sugar.GroupTwoWords;
import com.orm.SugarRecord;

import java.util.List;

public class ListActivity extends AppCompatActivity {

    //登録された単語のグループのリストを扱う
    ListView listView;
    ListSetUp adapter;
    int number_size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        //ListViewの設定
        listView = (ListView) findViewById(R.id.listView2);
        adapter = new ListSetUp(this, R.layout.list_set_up);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                GroupTwoWords groupTwoWords = (GroupTwoWords) adapter.getItem(i);
                long id = groupTwoWords.getId();
                Intent intent = new Intent(ListActivity.this, ListGroupWordsActivity.class);
                intent.putExtra("ID_GROUP_TWOWORDS", id);
                startActivity(intent);
            }
        });
        //グループの名前の呼び出し
        List<GroupTwoWords> list = SugarRecord.listAll(GroupTwoWords.class);
        number_size = list.size();
        for (GroupTwoWords groupTwoWords : list) {
            adapter.add(groupTwoWords);
        }
        //TextViewの設定
        TextView textView = (TextView) findViewById(R.id.textViewL0);
        textView.setText("All");
    }
}