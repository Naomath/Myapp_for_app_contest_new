package com.lifeistech.naoto.myapplication_app_contest.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lifeistech.naoto.myapplication_app_contest.R;
import com.lifeistech.naoto.myapplication_app_contest.adapters.ListDownLoadAdapter;
import com.lifeistech.naoto.myapplication_app_contest.adapters.ListSetUp;
import com.lifeistech.naoto.myapplication_app_contest.sugar.GroupTwoWords;
import com.lifeistech.naoto.myapplication_app_contest.sugar.TwoWords;
import com.lifeistech.naoto.myapplication_app_contest.sugar.TwoWordsWeak;
import com.orm.SugarRecord;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    int mCheckedItem;
    int number_size;
    Boolean isFabOpen = false;
    FloatingActionButton fab, fab1, fab3;
    Animation fab_open, fab_close, rotate_forward, rotate_backward;
    TextView textView, textView1;
    LinearLayout linearLayout, linearLayout1;
    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Languagous");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_list);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, -R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Intent intent = getIntent();
        int end_number = intent.getIntExtra("end", 0);
        if (end_number == 1) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("終了");
            builder.setMessage("出す問題がありません");
            builder.setPositiveButton("終了", new DialogInterface.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.show();
        }
        int upload_end = intent.getIntExtra("upload", 0);
        if (upload_end == 1) {
            makeToast("アップロードしました");
        }
        int download = intent.getIntExtra("download_end", 0);
        if (download == 1) {
            makeToast("ダウンロードが終わりました");
        }
        //リストビューの設定(自分で作成したもの)
        ListView listView = (ListView) findViewById(R.id.listView2);
        final ListSetUp adapter = new ListSetUp(this, R.layout.list_set_up);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                GroupTwoWords groupTwoWords = (GroupTwoWords) adapter.getItem(i);
                long id = groupTwoWords.getId();
                Intent intent = new Intent(MainActivity.this, ListGroupWordsActivity.class);
                intent.putExtra("ID_GROUP_TWOWORDS", id);
                startActivity(intent);
            }
        });
        //userの設定
        SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
        int which = preferences.getInt("which", 0);
        if (which == 0) {
            //初めての場合
            showDialogUserFirst(preferences);
        } else {
            View header = ((NavigationView) findViewById(R.id.nav_view)).getHeaderView(0);
            TextView textView = (TextView) header.findViewById(R.id.textView4);
            StringBuffer buffer = new StringBuffer();
            buffer.append("User:");
            String user = preferences.getString("user", null);
            buffer.append(user);
            textView.setText(buffer.toString());
            makeChart();
        }
        //リストビュー間違えやすいの
        List<GroupTwoWords> list = SugarRecord.listAll(GroupTwoWords.class);
        number_size = list.size();
        SharedPreferences preferences1 = getSharedPreferences("weak_id", 0);
        long weak_id = preferences1.getLong("weak_id", 0);
        int there = 0;
        for (GroupTwoWords groupTwoWords : list) {
            if (groupTwoWords.getId() == weak_id) {
            } else {
                if (groupTwoWords.getDown() == 0) {
                    adapter.add(groupTwoWords);
                    there++;
                }
            }

        }
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View item = adapter.getView(i, null, listView);
            item.measure(View.MeasureSpec.UNSPECIFIED,
                    View.MeasureSpec.UNSPECIFIED);
            totalHeight += item.getMeasuredHeight();
        }
        ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
        layoutParams.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(layoutParams);
        if (there == 0) {
            TextView textView = (TextView) findViewById(R.id.textView7);
            textView.setVisibility(View.VISIBLE);
        }
        createFab();
        //listview(間違えやすいもの)
        ListView listView1 = (ListView) findViewById(R.id.listViewWeak);
        ListSetUp adapterWeak = new ListSetUp(this, R.layout.list_set_up);
        listView1.setAdapter(adapterWeak);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent1 = new Intent(MainActivity.this, ListWeakActivity.class);
                startActivity(intent1);
            }
        });
        SharedPreferences preferences5 = getSharedPreferences("weak_id", 0);
        long id = preferences5.getLong("weak_id", 0);
        GroupTwoWords groupTwoWords = GroupTwoWords.findById(GroupTwoWords.class, id);
        adapterWeak.add(groupTwoWords);
        //listview(download)
        ListView listDown = (ListView) findViewById(R.id.listDown);
        final ListDownLoadAdapter listDownLoadAdapter = new ListDownLoadAdapter(this, R.layout.list_down_load_set_up);
        listDown.setAdapter(listDownLoadAdapter);
        listDown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                GroupTwoWords groupTwoWords1 = (GroupTwoWords) listDownLoadAdapter.getItem(i);
                long id = groupTwoWords1.getId();
                Intent intent = new Intent(MainActivity.this, ListGroupWordsActivity.class);
                intent.putExtra("ID_GROUP_TWOWORDS", id);
                startActivity(intent);
            }
        });
        int there2 = 0;
        for (GroupTwoWords groupTwoWords1 : list) {
            if (groupTwoWords1.getDown() == 1) {
                listDownLoadAdapter.add(groupTwoWords1);
                there2++;
            }
        }
        if (there2 == 0) {
            TextView textView = (TextView) findViewById(R.id.textView8);
            textView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_delete) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("消去");
            builder.setMessage("全件消去しますか？");
            builder.setNeutralButton("キャンセル", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.setPositiveButton("消去", new DialogInterface.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("消去");
                    builder.setMessage("全部消去しますか？");
                    builder.setNeutralButton("キャンセル", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.setPositiveButton("消去", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            TwoWords.deleteAll(TwoWords.class);
                            TwoWordsWeak.deleteAll(TwoWordsWeak.class);
                            GroupTwoWords.deleteAll(GroupTwoWords.class);
                        }
                    });
                    builder.show();
                }
            });
            builder.show();
        } else if (id == R.id.nav_settings) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            final String[] items = {" 先に和訳を表示する(デフォルト)", "先にスペルを表示する"};
            final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
            alert.setTitle("出題形式");
            alert.setSingleChoiceItems(items, mCheckedItem, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mCheckedItem = which;
                }
            });
            alert.setPositiveButton("決定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (mCheckedItem == 0) {
                        //先に和訳の時の処理
                        SharedPreferences pref = getSharedPreferences("question_mode", MODE_PRIVATE);
                        int mode = pref.getInt("question_mode", 0);
                        mode = 0;
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putInt("question_mode", mode);
                        editor.commit();
                    } else {
                        //先にスペルの時の処理
                        SharedPreferences pref = getSharedPreferences("question_mode", MODE_PRIVATE);
                        int mode = pref.getInt("question_mode", 0);
                        mode = 1;
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putInt("question_mode", mode);
                        editor.commit();
                    }
                }
            });
            alert.setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    dialog.dismiss();
                }
            });
            alert.show();
        } else if (id == R.id.nav_download) {
            //ダウンロード
            downLoad();
        } else if (id == R.id.nav_registration) {
            showDialogSetUp();
        } else if (id == R.id.nav_solve) {
            showDialogSolve();
        } else if (id == R.id.nav_upload) {
            //アップロード
            upLoad();
        } else if (id == R.id.nav_user) {
            //ユーザー
            showDialogUser();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void makeToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

    public void showDialogSolve() {
        //ダイアログを表示するメソッド
        //solveの時
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("問題を解く");
        builder.setMessage(R.string.message);
        builder.setNegativeButton(R.string.dialog_today, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //今日の問題を解く時の処理
                Intent intent = new Intent(MainActivity.this, SolveActivity.class);
                startActivity(intent);

            }
        });
        builder.setPositiveButton(R.string.dialog_weak, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //間違えやすい問題を解く時の処理
                List<TwoWordsWeak> list = SugarRecord.listAll(TwoWordsWeak.class);
                if (list.size() == 0) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("終了");
                    builder.setMessage("出す問題がありません");
                    builder.setPositiveButton("終了", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.show();
                } else {
                    Intent intent = new Intent(MainActivity.this, SolveWeakActivity.class);
                    startActivity(intent);
                }
            }
        });

        builder.setNeutralButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //キャンセルする時の処理
                dialogInterface.dismiss();
            }
        });

        builder.show();
    }

    public void showDialogSetUp() {
        // 登録のダイアログ
        final EditText editText = new EditText(MainActivity.this);
        //ダイアログで入力用のedittext
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("単語を登録する");
        builder.setMessage("登録するグループの名前を何にしますか？");
        builder.setView(editText);
        builder.setNeutralButton("キャンセル", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //キャンセルの処理
                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton("決定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String group_name = editText.getText().toString();
                //SugarRecord.listAll(TwoWords.class);
                if (group_name.length() == 0) {
                    makeToast("グループの名前が登録されていません");
                } else {
                    Intent intent = new Intent(MainActivity.this, SetUpActivity.class);
                    intent.putExtra("group_name", group_name);
                    startActivity(intent);
                }
            }
        });
        builder.show();
    }

    public void showDialogUserFirst(final SharedPreferences preferences) {
        final EditText editText = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("バカ天にようこそ");
        builder.setMessage("ユーザー名を入力してください");
        builder.setView(editText);
        builder.setPositiveButton("決定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("which", 1);
                editor.putString("user", editText.getText().toString());
                editor.commit();
                TextView textView = (TextView) findViewById(R.id.textView4);
                textView.setText(editText.getText().toString());
                String userId = RandomStringUtils.randomAlphabetic(12);
                SharedPreferences preferencesUserId = getSharedPreferences("user_id", MODE_PRIVATE);
                SharedPreferences.Editor editorUserId = preferencesUserId.edit();
                editorUserId.putString("user_id", userId);
                editorUserId.commit();
                StringBuffer buffer = new StringBuffer();
                buffer.append("UserIdは");
                buffer.append(userId);
                buffer.append("”です");
                makeToast(buffer.toString());
            }
        });
        final AlertDialog dialog = builder.create();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String strEdit = editable.toString();
                if(strEdit.equals("")){
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                }else {
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                }
            }
        });
        dialog.setCancelable(false);
        dialog.show();
        GroupTwoWords groupTwoWords = new GroupTwoWords("間違えた", 1, 1, "", editText.getText().toString());
        groupTwoWords.save();
        SharedPreferences preferences1 = getSharedPreferences("weak_id", MODE_PRIVATE);
        SharedPreferences.Editor editor1 = preferences1.edit();
        editor1.putLong("weak_id", groupTwoWords.getId());
        editor1.commit();
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        StringBuffer buffer = new StringBuffer();
        buffer.append(month + 1);
        buffer.append("/");
        buffer.append(day);
        SharedPreferences preferencesCalendarFirst = getSharedPreferences("first calendar", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencesCalendarFirst.edit();
        editor.putString("calendar", buffer.toString());
        editor.commit();
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getSharedPreferences("chart_questions", MODE_PRIVATE);
        SharedPreferences.Editor editorList = sharedPreferences.edit();
        ArrayList<String> calendars = new ArrayList<>();
        ArrayList<String> questions = new ArrayList<>();
        editorList.putString("calendars", gson.toJson(calendars));
        editorList.putString("questions", gson.toJson(questions));
        editorList.commit();
        makeChartNoMessage();
    }


    public void downLoad() {
        //ダウンロードの処理
        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra("mode", 1);
        startActivity(intent);
    }

    public void upLoad() {
        //アップロードの処理
        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra("mode", 2);
        startActivity(intent);
    }

    public void showDialogUser() {
        //ユーザー情報のダイアログ
        final SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
        SharedPreferences preferencesId = getSharedPreferences("user_id", MODE_PRIVATE);
        String userName = preferences.getString("user", null);
        String userId = preferencesId.getString("user_id", null);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        TextView textViewName = new TextView(this);
        textViewName.setText("ユーザー名");
        final EditText editTextName = new EditText(this);
        editTextName.setText(userName);
        TextView textViewId = new TextView(this);
        textViewId.setText("ユーザーID");
        TextView textViewId2 = new TextView(this);
        textViewId2.setText(userId);
        linearLayout.addView(textViewName);
        linearLayout.addView(editTextName);
        linearLayout.addView(textViewId);
        linearLayout.addView(textViewId2);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("ユーザー情報");
        alertDialogBuilder.setView(linearLayout);
        alertDialogBuilder.setPositiveButton("決定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //okの時の処理
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("user", editTextName.getText().toString());
                editor.commit();
            }
        });
        alertDialogBuilder.setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //キャンセルの時の処理
                dialogInterface.dismiss();
            }
        });
        alertDialogBuilder.show();
    }

    public void createFab() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        linearLayout = (LinearLayout) findViewById(R.id.line1);
        linearLayout1 = (LinearLayout) findViewById(R.id.line2);
        textView1 = (TextView) findViewById(R.id.text2);
        textView = (TextView) findViewById(R.id.text);
        fab3 = (FloatingActionButton) findViewById(R.id.fab3);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward);
    }


    public void animateFAB() {
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.fab_back_m);
        if (isFabOpen) {
            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            textView1.startAnimation(fab_close);
            textView.startAnimation(fab_close);
            linearLayout.startAnimation(fab_close);
            linearLayout1.startAnimation(fab_close);
            fab3.startAnimation(fab_close);
            isFabOpen = false;
            relativeLayout.setVisibility(View.INVISIBLE);

        } else {

            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            linearLayout.startAnimation(fab_open);
            textView.startAnimation(fab_open);
            linearLayout1.startAnimation(fab_open);
            textView1.startAnimation(fab_open);
            fab3.startAnimation(fab_open);
            isFabOpen = true;
            relativeLayout.setVisibility(View.VISIBLE);
        }
    }

    public void fab(View view) {
        animateFAB();
    }

    public void fab1(View view) {
        showDialogSetUp();
    }

    public void fab2(View view) {
        showDialogSolve();
    }

    public void makeChart() {
        //グラフを作る処理
        barChart = (BarChart) findViewById(R.id.chart_home);
        SharedPreferences preferencesFirstDay = getSharedPreferences("first calendar", MODE_PRIVATE);
        String calendarStr = preferencesFirstDay.getString("calendar", null);
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getSharedPreferences("chart_questions", MODE_PRIVATE);
        ArrayList<String> calendars = gson.fromJson(sharedPreferences.getString("calendars", null), new TypeToken<List>() {
        }.getType());
        ArrayList<String> questions = gson.fromJson(sharedPreferences.getString("questions", null), new TypeToken<List>() {
        }.getType());
        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>();
        if (calendars.size() == 0) {
            makeChartNoMessage();
        } else if (!(calendars.get(0).equals(calendarStr))) {
            values.add(calendarStr);
            barChartSystem2(calendars, questions, entries, values);
        } else {
            barChartSystem2(calendars, questions, entries, values);
        }
    }

    public void makeChartNoMessage() {
        barChart = (BarChart) findViewById(R.id.chart_home);
        barChart.setVisibility(View.GONE);
        Button button = (Button) findViewById(R.id.chart_btn);
        button.setVisibility(View.GONE);
        TextView textView = (TextView) findViewById(R.id.chart_text);
        textView.setVisibility(View.VISIBLE);
    }

    public void barChartSystem2(ArrayList<String> calendars, ArrayList<String> questions, ArrayList<BarEntry> entries, ArrayList<String> values) {
        for (String calendar : calendars) {
            values.add(calendar);
        }
        for (int i = 0; i < questions.size(); i++) {
            int entryInteger = Integer.parseInt(questions.get(i));
            entries.add(new BarEntry(entryInteger, i + 1));
        }
        BarDataSet data = new BarDataSet(entries, "解いた問題数");
        data.setColor(Color.CYAN);
        BarData data1 = new BarData(values, data);
        barChart.setData(data1);
        barChart.invalidate();
    }

}
