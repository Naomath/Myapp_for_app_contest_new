package com.lifeistech.naoto.myapplication_app_contest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;

import com.orm.SugarRecord;

public class ListActivity extends AppCompatActivity {

    //登録された単語のグループのリストを扱う
    ListView listView;
    ListSetUp adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        //ListViewの設定
        listView = (ListView)findViewById(R.id.listView2);
        adapter = new ListSetUp(this, R.layout.list_set_up);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                GroupTwoWords groupTwoWords = (GroupTwoWords)adapter.getItem(i);
                long id = groupTwoWords.getId();
                Intent intent = new Intent(ListActivity.this,ListGroupWordsActivity.class);
                intent.putExtra("ID_GROUP_TWOWORDS",id);
                startActivity(intent);
            }
        });
        //グループの名前の呼び出し
        for(long i = 1;;i++){
            GroupTwoWords groupTwoWords = GroupTwoWords.findById(GroupTwoWords.class, i);
            if(groupTwoWords != null){
                adapter.add(groupTwoWords);
            }else {
                break;
            }
        }
    }

}