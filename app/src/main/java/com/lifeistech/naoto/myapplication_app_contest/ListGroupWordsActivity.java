package com.lifeistech.naoto.myapplication_app_contest;

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

public class ListGroupWordsActivity extends AppCompatActivity {

    EditText editText;
    EditText editText1;
    TwoWords twoWords;
    GroupTwoWords groupTwoWords;
    long id;

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
                            }
                        });
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

                       } else if(which ==1){
                           
                       }
                    }
                })
                .setNegativeButton("キャンセル", null)
                .show();
    }

}
