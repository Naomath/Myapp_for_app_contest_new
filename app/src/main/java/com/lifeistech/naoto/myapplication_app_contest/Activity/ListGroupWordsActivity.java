package com.lifeistech.naoto.myapplication_app_contest.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lifeistech.naoto.myapplication_app_contest.R;
import com.lifeistech.naoto.myapplication_app_contest.Sugar.GroupTwoWords;
import com.lifeistech.naoto.myapplication_app_contest.Sugar.TwoWords;
import com.lifeistech.naoto.myapplication_app_contest.Sugar.TwoWordsAdd;
import com.lifeistech.naoto.myapplication_app_contest.Sugar.TwoWordsSet;
import com.lifeistech.naoto.myapplication_app_contest.adapters.ListGroupWordsListViewSetUp;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class ListGroupWordsActivity extends AppCompatActivity {

    EditText editText;
    EditText editText1;
    GroupTwoWords groupTwoWords;
    TwoWords twoWords;
    TwoWordsAdd twoWordsAdd;
    LinearLayout layout;
    ArrayList<TwoWordsSet> listSet;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_group_words);
        final Intent intent = getIntent();
        id = intent.getLongExtra("ID_GROUP_TWOWORDS", 0L);
        groupTwoWords = GroupTwoWords.findById(GroupTwoWords.class, id);
        listSet = new ArrayList<>();
        //受け渡し終了
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        setTitle(groupTwoWords.getGROUP_NAME());
        final ListView listView = (ListView) findViewById(R.id.listview_LGW);
        final ListGroupWordsListViewSetUp adapter = new ListGroupWordsListViewSetUp(this, R.layout.list_group_words_set_up);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                editText = new EditText(ListGroupWordsActivity.this);
                editText1 = new EditText(ListGroupWordsActivity.this);
                TextView textView = new TextView(ListGroupWordsActivity.this);
                textView.setText("和訳");
                TextView textView1 = new TextView(ListGroupWordsActivity.this);
                textView1.setText("スペル");
                layout = new LinearLayout(ListGroupWordsActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.addView(textView);
                layout.addView(editText);
                layout.addView(textView1);
                layout.addView(editText1);
                TwoWordsSet twoWordsSet = (TwoWordsSet) adapter.getItem(i);
                int mode = twoWordsSet.getMode();
                switch (mode){
                    case 0:
                        TwoWords twoWords = SugarRecord.findById(TwoWords.class, twoWordsSet.getId());
                        editText.setText(twoWords.getJapanese());
                        wordSetting(0);
                        break;
                    case 1:
                        TwoWordsAdd twoWordsAdd = SugarRecord.findById(TwoWordsAdd.class, twoWordsSet.getId());
                        editText1.setText(twoWordsAdd.getEnglish());
                        wordSetting(1);
                        break;
                }
            }
        });
        //listviewとadapterの設定終了
        //grouptwowordsの設定
        String group_name = groupTwoWords.getGROUP_NAME();
        int size = groupTwoWords.getSIZE();
        long first_id = groupTwoWords.getFIRST_ID();
        for (int i = 0; i < size; i++) {
            TwoWords twoWords = TwoWords.findById(TwoWords.class, first_id + i);
            if (twoWords.getJapanese() == null) {
                break;
            }
            TwoWordsSet twoWordsSet = new TwoWordsSet(twoWords.getId(), twoWords.getJapanese(), twoWords.getEnglish(), 0);
            listSet.add(twoWordsSet);
            adapter.add(twoWordsSet);
        }
       List<TwoWordsAdd> list = TwoWordsAdd.listAll(TwoWordsAdd.class);
        for(TwoWordsAdd twoWordsAdd:list){
            if(twoWordsAdd.getSubTitle().equals(group_name)){
                TwoWordsSet twoWordsSet = new TwoWordsSet(twoWordsAdd.getId(), twoWordsAdd.getJapanese(), twoWordsAdd.getEnglish(), 1);
                listSet.add(twoWordsSet);
                adapter.add(twoWordsSet);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_listgroupwords, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.group_delete:
                //消去
                groupDelete();
                break;
            case R.id.group_solve:
                //解く
                groupSolve();
                break;
            case R.id.group_add:
                //追加
                groupAdd();
                break;
        }
        return true;
    }

    public void groupSolve() {
        Intent intent = new Intent(this, SolveGroupActivity.class);
        intent.putExtra("id_group",id);
        startActivity(intent);
    }

    public void groupDelete() {
        new AlertDialog.Builder(ListGroupWordsActivity.this)
                .setTitle("消去")
                .setMessage("このグループを消去していいですか？")
                .setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new AlertDialog.Builder(ListGroupWordsActivity.this)
                                .setTitle("確認")
                                .setMessage("本当に消去していいですか")
                                .setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                })
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        long id = groupTwoWords.getFIRST_ID();
                                        for (int i2 = 0; i < groupTwoWords.getSIZE(); i++, id++) {
                                            TwoWords twoWords = SugarRecord.findById(TwoWords.class, id);
                                            twoWords.delete();
                                            groupTwoWords.delete();
                                        }
                                    }
                                })
                                .show();
                    }
                })
                .show();
    }

    public void groupAdd() {
        //単語を追加
        new AlertDialog.Builder(this)
                .setTitle("単語の追加")
                .setView(layout)
                .setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton("追加", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //追加の処理
                        String group_name = groupTwoWords.getGROUP_NAME();
                        int size = groupTwoWords.getSIZE();
                        long first_id = groupTwoWords.getFIRST_ID();
                        TwoWords twoWords = TwoWords.findById(TwoWords.class, first_id + i);
                        Calendar calendar = Calendar.getInstance();
                        final int year = calendar.get(Calendar.YEAR);
                        String year_string = Integer.toString(year);
                        final int day = calendar.get(Calendar.DAY_OF_YEAR);
                        String day_string = Integer.toString(day);
                        StringBuffer buf = new StringBuffer();
                        buf.append(year_string);
                        buf.append(day_string);
                        String date = buf.toString();
                        SharedPreferences preferences = getSharedPreferences(date, MODE_PRIVATE);
                        int number_of_day = preferences.getInt("number_of_day", 0);
                        number_of_day++;
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putInt("number_of_day", number_of_day);
                        editor.commit();
                        TwoWordsAdd twoWords1 = new TwoWordsAdd("add", editText.getText().toString(), editText1.getText().toString(), date);
                        twoWords1.save();
                        TwoWords twoWords2 = new TwoWords("追加されたものたち",editText.getText().toString(), editText1.getText().toString(), date);
                        twoWords2.save();
                        makeToast("追加しました");
                    }
                })
                .show();

    }

    public void wordSetting(final int mode) {
        //単語のセッティング
        final AlertDialog.Builder builder = new AlertDialog.Builder(ListGroupWordsActivity.this);
        builder.setTitle("Settings");
        builder.setView(layout);
        builder.setNegativeButton("消去", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(ListGroupWordsActivity.this);
                builder1.setTitle("消去");
                builder1.setMessage("本当に消去していいですか？");
                builder1.setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder1.setPositiveButton("消去", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (mode == 0) {
                            twoWords.setJapanese(null);
                            twoWords.setEnglish(null);
                            twoWords.save();
                        }else {
                            twoWordsAdd.delete();
                        }
                        Toast.makeText(ListGroupWordsActivity.this, "消去しました", Toast.LENGTH_SHORT).show();
                    }
                });
                builder1.show();
            }
        });
        builder.setNeutralButton("キャンセル", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton("登録", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (mode == 0) {
                    twoWords.setJapanese(editText.getText().toString());
                    twoWords.setEnglish(editText1.getText().toString());
                    twoWords.save();
                }else {
                    twoWordsAdd.setJapanese(editText.getText().toString());
                    twoWordsAdd.setEnglish(editText1.getText().toString());
                    twoWordsAdd.save();
                }
                Toast.makeText(ListGroupWordsActivity.this, "登録しました", Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }

    public void makeToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
