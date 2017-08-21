package com.lifeistech.naoto.myapplication_app_contest;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.orm.SugarRecord;

import java.util.List;

public class ListWeakActivity extends AppCompatActivity {
    EditText editText;
    EditText editText1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_weak);
        ListView listView = (ListView) findViewById(R.id.listView_LW);
        final ListWeakAdapter adapter = new ListWeakAdapter(this, R.layout.list_set_up_weak_adapter);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //アイテムがクリックされた時の処理
                final TwoWordsWeak twoWordsWeak = (TwoWordsWeak) adapter.getItem(i);
                editText = new EditText(ListWeakActivity.this);
                editText.setText(twoWordsWeak.getWORDS_JAPANESE());
                editText1 = new EditText(ListWeakActivity.this);
                editText1.setText(twoWordsWeak.getWORDS_ENGLISH());
                TextView textView = new TextView(ListWeakActivity.this);
                textView.setText("和訳");
                TextView textView1 = new TextView(ListWeakActivity.this);
                textView1.setText("スペル");
                LinearLayout layout = new LinearLayout(ListWeakActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.addView(textView);
                layout.addView(editText);
                layout.addView(textView1);
                layout.addView(editText1);
                final AlertDialog.Builder builder = new AlertDialog.Builder(ListWeakActivity.this);
                builder.setTitle("Settings");
                builder.setView(layout);
                builder.setNegativeButton("解除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(ListWeakActivity.this);
                        builder1.setTitle("解除");
                        builder1.setMessage("解除すると 間違えやすいものとしてでなくないrますが、いいですか？");
                        builder1.setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder1.setPositiveButton("解除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                twoWordsWeak.delete();
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
                        twoWordsWeak.setWORDS_JAPANESE(editText.getText().toString());
                        twoWordsWeak.setWORDS_ENGLISH(editText1.getText().toString());
                        twoWordsWeak.save();
                    }
                });
                builder.show();
            }
        });
        //アダプターの設定終わり
        List<TwoWordsWeak> list = SugarRecord.listAll(TwoWordsWeak.class);
        int number = 0;
        for (TwoWordsWeak twoWordsWeak : list) {
            adapter.add(twoWordsWeak);
            number++;
        }
        //TextViewの設定
        TextView textView = (TextView)findViewById(R.id.textViewLW0);
        textView.setText("Weak List");
    }
}
