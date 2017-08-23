package com.lifeistech.naoto.myapplication_app_contest.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lifeistech.naoto.myapplication_app_contest.adapters.ListGroupWordsListViewSetUp;
import com.lifeistech.naoto.myapplication_app_contest.R;
import com.lifeistech.naoto.myapplication_app_contest.Sugar.GroupTwoWords;
import com.lifeistech.naoto.myapplication_app_contest.Sugar.TwoWords;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListGroupWordsActivity extends AppCompatActivity {

    EditText editText;
    EditText editText1;
    TwoWords twoWords;
    GroupTwoWords groupTwoWords;
    long id;
    long id_solve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_group_words);
        final Intent intent = getIntent();
        id = intent.getLongExtra("ID_GROUP_TWOWORDS", 0L);
        //受け渡し終了
        final ListView listView = (ListView) findViewById(R.id.listview_LGW);
        final ListGroupWordsListViewSetUp adapter = new ListGroupWordsListViewSetUp(this, R.layout.list_group_words_set_up);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                twoWords = (TwoWords) adapter.getItem(i);
                editText = new EditText(ListGroupWordsActivity.this);
                editText.setText(twoWords.getJapanese());
                editText1 = new EditText(ListGroupWordsActivity.this);
                editText1.setText(twoWords.getEnglish());
                TextView textView = new TextView(ListGroupWordsActivity.this);
                textView.setText("和訳");
                TextView textView1 = new TextView(ListGroupWordsActivity.this);
                textView1.setText("スペル");
                LinearLayout layout = new LinearLayout(ListGroupWordsActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.addView(textView);
                layout.addView(editText);
                layout.addView(textView1);
                layout.addView(editText1);
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
                                twoWords.setJapanese(null);
                                twoWords.setEnglish(null);
                                twoWords.save();
                                Toast.makeText(ListGroupWordsActivity.this,"消去しました", Toast.LENGTH_SHORT).show();
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
                        twoWords.setJapanese(editText.getText().toString());
                        twoWords.setEnglish(editText1.getText().toString());
                        twoWords.save();
                        Toast.makeText(ListGroupWordsActivity.this,"登録しました", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
        //listviewとadapterの設定終了
        //grouptwowordsの設定
        int number = 0;
        groupTwoWords = GroupTwoWords.findById(GroupTwoWords.class, id);
        String group_name = groupTwoWords.getGROUP_NAME();
        int size = groupTwoWords.getSIZE();
        long first_id = groupTwoWords.getFIRST_ID();
        for (int i = 0; i < size; i++) {
            TwoWords twoWords = TwoWords.findById(TwoWords.class, first_id + i);
            if (twoWords.getJapanese() == null) {
                break;
            }
            adapter.add(twoWords);
            number++;
        }
        //TextViewの設定
        TextView textView = (TextView) findViewById(R.id.textViewLGW0);
        textView.setText(groupTwoWords.getGROUP_NAME());
        //fabの設定
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_dialog_fab();
            }
        });
    }

    public void show_dialog_fab(){
        final String[] items = {"このグループの確認をする","グループを消去"};
        new AlertDialog.Builder(ListGroupWordsActivity.this)
                .setTitle("どうしますか？")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       if(which == 0){
                           Intent intent = new Intent(ListGroupWordsActivity.this, SolveGroupActivity.class);
                           intent.putExtra("id_group", groupTwoWords.getId());
                           startActivity(intent);
                       } else if(which ==1){
                           group_delete();
                       }
                    }
                })
                .setNegativeButton("キャンセル", null)
                .show();
    }

    public void group_solve(){
        List<TwoWords> list = new ArrayList<>();
        long firdt_id = groupTwoWords.getFIRST_ID();
        for(int i = 0;i<groupTwoWords.getSIZE();i++,firdt_id++){
            TwoWords twoWords = SugarRecord.findById(TwoWords.class, firdt_id);
            if(twoWords.getJapanese() == null){
                break;
            } else {
                list.add(twoWords);
            }
        }
        Collections.shuffle(list);
        ArrayList<String> japaneses = new ArrayList<>();
        ArrayList<String> englishes = new ArrayList<>();
        for (TwoWords twoWords:list){
            japaneses.add(twoWords.getJapanese());
            englishes.add(twoWords.getEnglish());
        }
        Intent intent = new Intent(ListGroupWordsActivity.this, Solve2Activity.class);
        intent.putExtra("japaneses", japaneses);
        intent.putExtra("englishes", englishes);
        intent.putExtra("number", 0);
        intent.putExtra("mode", 2);
        startActivity(intent);
    }
    public void group_delete(){
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

}
