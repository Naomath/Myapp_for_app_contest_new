package com.lifeistech.naoto.myapplication_app_contest.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lifeistech.naoto.myapplication_app_contest.Class.TwoWordsSet;
import com.lifeistech.naoto.myapplication_app_contest.R;
import com.lifeistech.naoto.myapplication_app_contest.Sugar.GroupTwoWords;
import com.lifeistech.naoto.myapplication_app_contest.Sugar.TwoWords;
import com.lifeistech.naoto.myapplication_app_contest.Sugar.TwoWordsAdd;
import com.lifeistech.naoto.myapplication_app_contest.adapters.ListGroupWordsListViewSetUp;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ListGroupWordsActivity extends AppCompatActivity {
    ListGroupWordsListViewSetUp adapter;
    EditText editText;
    EditText editText1;
    GroupTwoWords groupTwoWords;
    TwoWords twoWords;
    TwoWordsAdd twoWordsAdd;
    ArrayList<TwoWordsSet> listSet;
    long id;
    Boolean isFabOpen = false;
    FloatingActionButton fab, fab1, fab3, fab2, fab4;
    Animation fab_open, fab_close, rotate_forward, rotate_backward;
    TextView textView, textView1, textView2, textView3;
    LinearLayout linearLayout, linearLayout1, linearLayout2, linearLayout3;

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setTitle(groupTwoWords.getGROUP_NAME());
        //toolbar
        final ListView listView = (ListView) findViewById(R.id.listview_LGW);
        adapter = new ListGroupWordsListViewSetUp(this, R.layout.list_group_words_set_up);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TwoWordsSet twoWordsSet = (TwoWordsSet) adapter.getItem(i);
                int mode = twoWordsSet.getMode();
                switch (mode) {
                    case 0:
                        twoWords = SugarRecord.findById(TwoWords.class, twoWordsSet.getId());
                        wordSetting(0, twoWordsSet);
                        break;
                    case 1:
                        twoWordsAdd = SugarRecord.findById(TwoWordsAdd.class, twoWordsSet.getId());
                        wordSetting(1, twoWordsSet);
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
            if (twoWords.getJapanese() != null) {
                TwoWordsSet twoWordsSet = new TwoWordsSet(twoWords.getId(), twoWords.getJapanese(), twoWords.getEnglish(), 0);
                listSet.add(twoWordsSet);
                adapter.add(twoWordsSet);
            }
        }
        List<TwoWordsAdd> list = TwoWordsAdd.listAll(TwoWordsAdd.class);
        for (TwoWordsAdd twoWordsAdd : list) {
            if (twoWordsAdd.getSubTitle().equals(group_name)) {
                TwoWordsSet twoWordsSet = new TwoWordsSet(twoWordsAdd.getId(), twoWordsAdd.getJapanese(), twoWordsAdd.getEnglish(), 1);
                listSet.add(twoWordsSet);
                adapter.add(twoWordsSet);
            }
        }
        createFab();
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

    public void fab_open(View view) {
        animateFAB();
    }

    public void fab_edit(View view) {
        groupSolve();
    }

    public void fab_add(View view) {
        groupAdd();
    }

    public void fab_delete(View view) {
        groupDelete();
    }

    public void groupSolve() {
        Intent intent = new Intent(this, SolveGroupActivity.class);
        intent.putExtra("id_group", id);
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
                                        for (int i2 = 0; i2 < groupTwoWords.getSIZE(); i2++, id++) {
                                            TwoWords twoWords = SugarRecord.findById(TwoWords.class, id);
                                            twoWords.delete();
                                        }
                                        groupTwoWords.delete();
                                        Intent intent = new Intent(ListGroupWordsActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                })
                                .show();
                    }
                })
                .show();
    }

    public void groupAdd() {
        //単語を追加
        editText = new EditText(ListGroupWordsActivity.this);
        editText1 = new EditText(ListGroupWordsActivity.this);
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
                        TwoWords twoWords = new TwoWords("追加されたものたち", editText.getText().toString(), editText1.getText().toString(), date);
                        if (twoWords.getJapanese().equals("")) {
                            makeToast("和訳が書かれていません");
                        } else if (twoWords.getEnglish().equals("")) {
                            makeToast("スペルが書かれていません");
                        } else {
                            twoWords.save();
                            TwoWordsAdd twoWordsAdd = new TwoWordsAdd("add", editText.getText().toString(), editText1.getText().toString(), date, group_name, twoWords.getId());
                            twoWordsAdd.save();
                            TwoWordsSet twoWordsSet = new TwoWordsSet(twoWordsAdd.getId(), twoWordsAdd.getJapanese(), twoWordsAdd.getEnglish(), 1);
                            adapter.add(twoWordsSet);
                            changeCalender();
                            makeToast("追加しました");
                        }
                    }
                })
                .show();

    }

    public void wordSetting(final int mode, final TwoWordsSet twoWordsSet) {
        //単語のセッティング
        editText = new EditText(ListGroupWordsActivity.this);
        editText1 = new EditText(ListGroupWordsActivity.this);
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
        if (mode == 0) {
            editText.setText(twoWords.getJapanese());
            editText1.setText(twoWords.getEnglish());
        } else {
            editText.setText(twoWordsAdd.getJapanese());
            editText1.setText(twoWordsAdd.getEnglish());
        }
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
                            adapter.remove(twoWordsSet);
                        } else {
                            TwoWords twoWords = TwoWords.findById(TwoWords.class, twoWordsAdd.getTwo_words_id());
                            twoWords.setJapanese(null);
                            twoWords.setEnglish(null);
                            twoWordsAdd.delete();
                            adapter.remove(twoWordsSet);
                        }
                        changeCalender();
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
                    if (twoWords.getJapanese().equals("")) {
                        makeToast("和訳が書かれていません");
                    } else if (twoWords.getEnglish().equals("")) {
                        makeToast("スペルが書かれていません");
                    } else {
                        twoWords.setJapanese(editText.getText().toString());
                        twoWords.setEnglish(editText1.getText().toString());
                        twoWords.save();
                        makeToast("登録しました");
                    }
                } else {
                    TwoWords twoWords = TwoWords.findById(TwoWords.class, twoWordsAdd.getTwo_words_id());
                    twoWords.setJapanese(editText.getText().toString());
                    twoWords.setEnglish(editText1.getText().toString());
                    twoWords.save();
                    twoWordsAdd.setJapanese(editText.getText().toString());
                    twoWordsAdd.setEnglish(editText1.getText().toString());
                    twoWordsAdd.save();
                    changeCalender();
                    makeToast("登録しました");
                }
            }
        });
        builder.show();
    }

    public void makeToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void changeCalender() {
        Calendar calendar1 = Calendar.getInstance();
        int year = calendar1.get(Calendar.YEAR);
        int month = calendar1.get(Calendar.MONTH);
        int day_str = calendar1.get(Calendar.DAY_OF_MONTH);
        StringBuffer buf3 = new StringBuffer();
        buf3.append(String.valueOf(year));
        buf3.append("-");
        buf3.append(String.valueOf(month + 1));
        buf3.append("/");
        buf3.append(String.valueOf(day_str));
        groupTwoWords.setCalendar(buf3.toString());
    }

    public void createFab() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab2 = (FloatingActionButton) findViewById(R.id.fab31);
        fab3 = (FloatingActionButton) findViewById(R.id.fab21);
        fab1 = (FloatingActionButton) findViewById(R.id.fab11);
        fab4 = (FloatingActionButton)findViewById(R.id.fab41);
        linearLayout = (LinearLayout) findViewById(R.id.line1);
        linearLayout1 = (LinearLayout) findViewById(R.id.line2);
        linearLayout2 = (LinearLayout) findViewById(R.id.line3);
        linearLayout3 = (LinearLayout)findViewById(R.id.line4);
        textView1 = (TextView) findViewById(R.id.text1);
        textView = (TextView) findViewById(R.id.text);
        textView2 = (TextView) findViewById(R.id.text0);
        textView3 = (TextView)findViewById(R.id.text4);
        fab_open = AnimationUtils.loadAnimation(this, R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(this, R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(this, R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(this, R.anim.rotate_backward);
    }


    public void animateFAB() {
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.fab_back_lg);
        if (isFabOpen) {
            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab3.startAnimation(fab_close);
            fab4.startAnimation(fab_close);
            textView1.startAnimation(fab_close);
            textView.startAnimation(fab_close);
            textView2.startAnimation(fab_close);
            textView3.startAnimation(fab_close);
            linearLayout.startAnimation(fab_close);
            linearLayout2.startAnimation(fab_close);
            linearLayout1.startAnimation(fab_close);
            linearLayout3.startAnimation(fab_close);
            isFabOpen = false;
            relativeLayout.setVisibility(View.INVISIBLE);

        } else {
            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab3.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab4.startAnimation(fab_open);
            linearLayout.startAnimation(fab_open);
            textView.startAnimation(fab_open);
            textView1.startAnimation(fab_open);
            textView2.startAnimation(fab_open);
            textView3.startAnimation(fab_close);
            linearLayout2.startAnimation(fab_open);
            linearLayout3.startAnimation(fab_open);
            linearLayout1.startAnimation(fab_open);
            isFabOpen = true;
            relativeLayout.setVisibility(View.VISIBLE);
        }
    }

}
