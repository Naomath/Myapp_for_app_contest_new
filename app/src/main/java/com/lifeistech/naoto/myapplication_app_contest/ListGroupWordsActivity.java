package com.lifeistech.naoto.myapplication_app_contest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.orm.SugarRecord;
import java.util.ArrayList;
import java.util.List;

public class ListGroupWordsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_group_words);
        Intent intent = getIntent();
        long id = intent.getLongExtra("ID_GROUP_TWOWORDS",0L);
        //受け渡し終了
        ListView listView = (ListView)findViewById(R.id.listview_LGW);
        ListGroupWordsListViewSetUp adapter = new ListGroupWordsListViewSetUp(this, R.layout.list_group_words_set_up);
        listView.setAdapter(adapter);
        //listviewの設定終了
        //grouptwowordsの設定
        GroupTwoWords groupTwoWords = GroupTwoWords.findById(GroupTwoWords.class, id);
        String group_name = groupTwoWords.getGROUP_NAME();
        int size = groupTwoWords.getSIZE();
        long first_id = groupTwoWords.getFIRST_ID();
        for (int i = 0;i<size;i++){
            TwoWords twoWords = TwoWords.findById(TwoWords.class, first_id + i);
            adapter.add(twoWords);
        }
        TextView textView = (TextView)findViewById(R.id.textViewLGW0);
        textView.setText(group_name);
    }
}
