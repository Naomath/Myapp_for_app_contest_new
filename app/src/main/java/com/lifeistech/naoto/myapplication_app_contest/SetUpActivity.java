package com.lifeistech.naoto.myapplication_app_contest;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import java.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SetUpActivity extends AppCompatActivity {

    //問題を登録するActivity
    String group_name;
    TextView title;
    ListviewSetUp adapter;
    ListView listView;
    List<TwoWords> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up);
        Intent intent = getIntent();
        group_name = intent.getStringExtra("group_name");
        //グループの名前を受け取っている
        title = (TextView) findViewById(R.id.textView2);
        //グループの名前をだすtextviewを登録した
        title.setText(group_name);
        listView = (ListView) findViewById(R.id.listView);
        adapter = new ListviewSetUp(this, R.layout.listview_set_up);
        listView.setAdapter(adapter);
        list = new ArrayList<>();
    }

    public void dialog_set_up(View view) {
        // 登録するためのダイアログへの受け渡し
        showDialog_set_up();
    }

    public void showDialog_set_up() {
        //登録するためのダイアログを作る
        final EditText japanese_editText = new EditText(this);
        final EditText english_editText = new EditText(this);
        //登録のためのedittextを作る
        TextView japanese_textView = new TextView(this);
        japanese_textView.setText("和訳");
        TextView english_textView = new TextView(this);
        english_textView.setText("スペル");
        //登録の説明のためのtextViewを作る
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("単語を登録する");
        linearLayout.addView(japanese_textView);
        linearLayout.addView(japanese_editText);
        linearLayout.addView(english_textView);
        linearLayout.addView(english_editText);
        builder.setView(linearLayout);
        builder.setNeutralButton("キャンセル", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton("決定", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //単語を登録する時の処理1
                String japanese_string = japanese_editText.getText().toString();
                String english_string = english_editText.getText().toString();
                if (japanese_string == null) {
                    make_Toast("和訳が書かれていません");
                } else if (english_string == null) {
                    make_Toast("スペルが書かれていません");
                } else {
                    TwoWordsForSet twoWordsForSet = new TwoWordsForSet(japanese_string, english_string);
                    adapter.add(twoWordsForSet);
                    /*ここから、保存の概要について書く。
                    まず、TwoWordsというクラス型で保存して、
                    SugarORMを使う説明
                    まず最初にその単語が属するグループの名前
                    ちなみに被るのはできれば回避したいので、
                    登録の時に全件検索して、もし同じのがあれば確認のダイアログを出す
                    そして二つ目は単語
                    次に引数の説明
                    まず最初にその単語が属するグループの名前
                    ちなみに被るのはできれば回避したいので、
                    登録の時に全件検索して、もし同じのがあれば確認のダイアログを出す
                    そして二つ目は単語の和訳
                    三つ目は単語のスペル（英単語想定している）
                    そして最後にそのID
                    付け方は、
                    もし2017年8月27日に最初に登録し単語であれば、
                    20178271
                    となる（CalendarTestにて）
                    IDはファイル名が2017年8月27日であれば、
                    2017827となるようにしてある
                    それで検索してやっている
                    */
                }

            }
        });
        builder.show();
    }

    public void finish(View view) {
        //グループ自体、登録ができた時の処理
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("登録");
        builder.setMessage("登録していいですか？");
        builder.setNeutralButton("キャンセル", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //キャンセルする時の処理
                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton("登録", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //登録する時の処理
                int firstVisibleIndex = listView.getFirstVisiblePosition();
                int lastVisibleIndex = listView.getLastVisiblePosition();
                // 一気に登録している
                for (int i2 = firstVisibleIndex; i2 <= lastVisibleIndex; i2++) {
                    //for文でlistviewのセルの上から登録していく
                    TwoWordsForSet twoWordsForSet = (TwoWordsForSet) adapter.getItem(i2);
                    String japanese_string = twoWordsForSet.getJapanese();
                    String english_string = twoWordsForSet.getEnglish();
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
                    StringBuffer buf2 = new StringBuffer();
                    buf2.append(date);
                    String string_number_of_day = new Integer(number_of_day).toString();
                    buf2.append(string_number_of_day);
                    String date2 = buf2.toString();
                    TwoWords two_words = new TwoWords(group_name, japanese_string, english_string, date2);
                    //ここでlistにtwoewordsを入れる
                    two_words.save();
                    list.add(two_words);
                }
                make_Toast("登録しました");
                int size = list.size();
                TwoWords twoWords = list.get(0);
                long first_id = twoWords.getId();
                GroupTwoWords groupTwoWords = new GroupTwoWords(group_name, size, first_id);
                groupTwoWords.save();
                //ここでグループとしても登録する
                Intent intent = new Intent(SetUpActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        builder.show();
    }

    public void make_Toast(String massage) {
        Toast.makeText(this, massage, Toast.LENGTH_SHORT).show();
    }
}
