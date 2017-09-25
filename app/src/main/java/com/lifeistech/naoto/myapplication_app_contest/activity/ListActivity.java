package com.lifeistech.naoto.myapplication_app_contest.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lifeistech.naoto.myapplication_app_contest.R;
import com.lifeistech.naoto.myapplication_app_contest.sugar.GroupTwoWords;
import com.lifeistech.naoto.myapplication_app_contest.sugar.TwoWords;
import com.lifeistech.naoto.myapplication_app_contest.sugar.TwoWordsAdd;
import com.lifeistech.naoto.myapplication_app_contest.adapters.ListDownLoadAdapter;
import com.lifeistech.naoto.myapplication_app_contest.adapters.ListDownLoadDialogAdapter;
import com.lifeistech.naoto.myapplication_app_contest.adapters.ListSetUp;
import com.lifeistech.naoto.myapplication_app_contest.adapters.ListUploadAdapter;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    //登録された単語のグループのリストを扱う
    ListView g_listView;
    ListSetUp g_adapter;
    int g_numberSize;
    Toolbar g_toolbar;
    SearchView g_searchView;
    int g_mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_true);
        //toolbarの設定
        g_toolbar = (Toolbar) findViewById(R.id.toolbar_list);
        setSupportActionBar(g_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setTitle("バカ天");
        //Intentの設定
        Intent intent = getIntent();
        g_mode = intent.getIntExtra("mode", 0);
        if (g_mode == 1) {
            //ダウンロードの時の処理
            downLoadTime();
        } else if (g_mode == 2) {
            //アップロードの処理
            g_listView = (ListView) findViewById(R.id.listView);
            g_adapter = new ListSetUp(this, R.layout.list_set_up);
            g_listView.setAdapter(g_adapter);
            upLoadTime();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem menuItem = menu.findItem(R.id.toolbar_menu_search);
        g_searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        g_searchView.setIconifiedByDefault(true);
        g_searchView.setSubmitButtonEnabled(false);
        g_searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (g_mode == 1) {
                    searchGroupDown(query);
                } else {
                    searchGroupUp(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        boolean result = true;
        switch (id) {
            case android.R.id.home:
                finish();
                break;
            default:
                result = super.onOptionsItemSelected(item);
        }
        return result;
    }

    public void downLoadTime() {
        //listviewの設定
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        g_listView = (ListView) findViewById(R.id.listView);
        final ListDownLoadAdapter adapter = new ListDownLoadAdapter(this, R.layout.list_down_load_set_up);
        g_listView.setAdapter(adapter);
        // visibilityの設定
        TextView textView = (TextView) findViewById(R.id.text_description);
        textView.setText("ダウンロードしたいグループをタッチしてください");
        TextView textView1 = (TextView) findViewById(R.id.textView5);
        textView1.setText("ダウンロードできるもの");
        //ダウンロードの時の処理
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("group");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //変更された時
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    GroupTwoWords groupTwoWords = snapshot.getValue(GroupTwoWords.class);
                    adapter.add(groupTwoWords);
                }
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //キャンセルされた時
            }
        });
        g_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final GroupTwoWords groupTwoWords = (GroupTwoWords) adapter.getItem(i);
                AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);
                builder.setTitle("ダウンロード");
                final StringBuffer buffer = new StringBuffer();
                buffer.append("『");
                buffer.append(groupTwoWords.getGroupName());
                buffer.append("』をダウンロードしていいですか");
                builder.setMessage(buffer.toString());
                builder.setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                //listviewの設定
                ListView listView = new ListView(ListActivity.this);
                ListDownLoadDialogAdapter adapter1 = new ListDownLoadDialogAdapter(ListActivity.this, R.layout.list_upload_dialog);
                listView.setAdapter(adapter1);
                for(TwoWords twoWords:groupTwoWords.getArrayList()){
                    adapter1.add(twoWords);
                }
                builder.setView(listView).create();
                builder.setPositiveButton("ダウンロード", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //ダウンロードの処理
                        ArrayList<TwoWords> arrayList = groupTwoWords.getArrayList();
                        Calendar calendar = Calendar.getInstance();
                        int year = calendar.get(Calendar.YEAR);
                        int day = calendar.get(Calendar.DAY_OF_YEAR);
                        StringBuffer buffer1 = new StringBuffer();
                        buffer1.append(String.valueOf(year));
                        buffer1.append(String.valueOf(day));
                        String calendar_str = buffer1.toString();
                        for(TwoWords twoWords:arrayList){
                            twoWords.setDate(calendar_str);
                            twoWords.setWeak(0);
                            twoWords.save();
                        }
                        groupTwoWords.setDown(1);
                        groupTwoWords.setFirstId(arrayList.get(0).getId());
                        groupTwoWords.setSize(arrayList.size());
                        groupTwoWords.save();
                        Intent intent = new Intent(ListActivity.this, MainActivity.class);
                        intent.putExtra("download_end",1);
                        startActivity(intent);
                    }
                });
                builder.show();
            }
        });
    }

    public void upLoadTime() {
        //アップロードの時の処理
        //先ずはlayoutのvisibilityについての設定
        TextView textView = (TextView) findViewById(R.id.text_description);
        textView.setText("アップロードしたいグループをタッチしてください");
        //firebaseの設定
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference reference = database.getReference("group");
        adpter_groupaTwoWords_all();
        g_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final GroupTwoWords groupTwoWords = (GroupTwoWords) g_adapter.getItem(i);
                //ダイアログの設定
                AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);
                builder.setTitle("アップロード");
                StringBuffer buffer = new StringBuffer();
                buffer.append("アップロードするグループは『");
                buffer.append(groupTwoWords.getGroupName());
                buffer.append("』でいいですか？");
                builder.setMessage(buffer.toString());
                //listviewの設定
                ListView listView = new ListView(ListActivity.this);
                ListUploadAdapter adapter = new ListUploadAdapter(ListActivity.this, R.layout.list_upload_adapter);
                listView.setAdapter(adapter);
                final ArrayList<TwoWords> twoWordsArrayList = new ArrayList<TwoWords>();
                int size = groupTwoWords.getSize();
                long first_id = groupTwoWords.getFirstId();
                for (int i2 = 0; i2 < size; i2++) {
                    TwoWords twoWords = TwoWords.findById(TwoWords.class, first_id + i2);
                    if (twoWords.getJapanese() == null) {
                        return;
                    } else {
                        adapter.add(twoWords);
                        twoWordsArrayList.add(twoWords);
                    }
                }
                List<TwoWordsAdd> twoWordsAddList = SugarRecord.listAll(TwoWordsAdd.class);
                for(TwoWordsAdd twoWordsAdd:twoWordsAddList){
                    if(twoWordsAdd.getSubTitle().equals(groupTwoWords.getGroupName())){
                        TwoWords twoWords = TwoWords.findById(TwoWords.class,twoWordsAdd.getTwo_words_id());
                        adapter.add(twoWords);
                        twoWordsArrayList.add(twoWords);
                    }
                }
                //終わり
                builder.setView(listView).create();
                builder.setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setPositiveButton("アップロード", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int size = groupTwoWords.getSize();
                        long first_id = groupTwoWords.getFirstId();
                        groupTwoWords.setArrayList(twoWordsArrayList);
                        SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
                        String maker = preferences.getString("user", null);
                        groupTwoWords.setMaker(maker);
                        reference.push().setValue(groupTwoWords);
                        Intent intent = new Intent(ListActivity.this, MainActivity.class);
                        intent.putExtra("upload", 1);
                        startActivity(intent);
                    }
                });
                builder.show();
            }
        });

    }

    public void adpter_groupaTwoWords_all() {
        List<GroupTwoWords> list = SugarRecord.listAll(GroupTwoWords.class);
        g_numberSize = list.size();
        SharedPreferences preferences = getSharedPreferences("weak_id", MODE_PRIVATE);
        long weka_id = preferences.getLong("weak_id", 0);
        for (GroupTwoWords groupTwoWords : list) {
            if (weka_id != groupTwoWords.getId()) {
                g_adapter.add(groupTwoWords);
            } else {
                continue;
            }
        }
    }

    public void makeToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT);
    }

    public void searchGroupUp(String query) {
        //検索機能のところのアップロードのところ
        int first = g_listView.getFirstVisiblePosition();
        int last = g_listView.getLastVisiblePosition();
        int size = last - first + 1;
        for (int i = 0; i < size; i++) {
            GroupTwoWords item = (GroupTwoWords) g_listView.getItemAtPosition(first + i);
            g_adapter.remove(item);
        }
        List<GroupTwoWords> list = SugarRecord.listAll(GroupTwoWords.class);
        SharedPreferences preferences = getSharedPreferences("weak_id", MODE_PRIVATE);
        long weak_id = preferences.getLong("weak_id", 0);
        for (GroupTwoWords groupTwoWords : list) {
            if (groupTwoWords.getId() == weak_id) {
                continue;
            } else if (groupTwoWords.getGroupName().startsWith(query)) {
                g_adapter.add(groupTwoWords);
            }
        }
    }

    public void searchGroupDown(String query) {
        //検索機能のダウンロード
        final ArrayList<GroupTwoWords> arrayList = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("group");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //変更された時
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    GroupTwoWords groupTwoWords = snapshot.getValue(GroupTwoWords.class);
                    arrayList.add(groupTwoWords);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //キャンセルされた時
            }
        });
        int first = g_listView.getFirstVisiblePosition();
        int last = g_listView.getLastVisiblePosition();
        int size = last - first + 1;
        for (int i = 0; i < size; i++) {
            GroupTwoWords item = (GroupTwoWords) g_listView.getItemAtPosition(first + i);
            g_adapter.remove(item);
        }
        for (GroupTwoWords groupTwoWords : arrayList) {
            if (groupTwoWords.getGroupName().startsWith(query)) {
                g_adapter.add(groupTwoWords);
            }
        }
    }
}