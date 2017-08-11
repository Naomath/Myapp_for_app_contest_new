package com.lifeistech.naoto.myapplication_app_contest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
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
        GroupTwoWords groupTwoWords = GroupTwoWords.findById(GroupTwoWords.class, id);
        List<TwoWordsForArray> list = groupTwoWords.getList();
        //うまく呼び出しはされている
        for(int i = 0;; i++){
            TwoWordsForArray twoWordsForArray = list.get(i);
            if(twoWordsForArray != null){
                adapter.add(twoWordsForArray);
            }else {
                break;
            }
            //twowordsをadapterにセットしている
        }
    }
}
