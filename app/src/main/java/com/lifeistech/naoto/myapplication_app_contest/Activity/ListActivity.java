package com.lifeistech.naoto.myapplication_app_contest.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lifeistech.naoto.myapplication_app_contest.Sugar.TwoWords;
import com.lifeistech.naoto.myapplication_app_contest.adapters.ListSetUp;
import com.lifeistech.naoto.myapplication_app_contest.R;
import com.lifeistech.naoto.myapplication_app_contest.Sugar.GroupTwoWords;
import com.orm.SugarRecord;

import java.util.ArrayList;
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
        //Intentの設定
        Intent intent = getIntent();
        int mode = intent.getIntExtra("mode",0);
        if(mode == 1) {
            //ダウンロードの時の処理
            down_load_time();
        }else if(mode == 2){
            //アップロードの処理
            up_load_time();
        }else {
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
            adpter_groupaTwoWords_all();
            //TextViewの設定
            TextView textView = (TextView) findViewById(R.id.textViewL0);
            textView.setText("All");
        }
    }

    public void down_load_time(){
        //ダウンロードの時の処理
        final ArrayList<GroupTwoWords> groupTwoWordses = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("group");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //投稿された時
                GroupTwoWords groupTwoWords = dataSnapshot.getValue(GroupTwoWords.class);
                groupTwoWordses.add(groupTwoWords);
                adapter.addAll(groupTwoWordses);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                //変更された時
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                //削除された時
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                //移動された時
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //キャンセルされた時
            }
        });
    }

    public void up_load_time(){
        //アップロードの時の処理
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference reference = database.getReference("group");
        adpter_groupaTwoWords_all();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                GroupTwoWords groupTwoWords = (GroupTwoWords) adapter.getItem(i);
                long id = groupTwoWords.getId();
                int size = groupTwoWords.getSIZE();
                long first_id = groupTwoWords.getFIRST_ID();
                ArrayList<TwoWords> twoWordses = new ArrayList<TwoWords>();
                for (int i2 = 0; i2 < size; i2++) {
                    TwoWords twoWords = TwoWords.findById(TwoWords.class, first_id + i);
                    if (twoWords.getJapanese() == null) {
                        break;
                    }else {
                        twoWordses.add(twoWords);
                    }
                }
                groupTwoWords.setArrayList(twoWordses);
                reference.push().setValue(groupTwoWords);
                make_Toast("Internetにグループをあげました");
                Intent intent = new Intent(ListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void adpter_groupaTwoWords_all(){
        List<GroupTwoWords> list = SugarRecord.listAll(GroupTwoWords.class);
        number_size = list.size();
        for (GroupTwoWords groupTwoWords : list) {
            adapter.add(groupTwoWords);
        }
    }

    public void make_Toast(String str){
        Toast.makeText(this,str,Toast.LENGTH_SHORT);
    }
}