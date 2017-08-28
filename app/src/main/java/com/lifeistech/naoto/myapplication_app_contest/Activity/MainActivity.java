package com.lifeistech.naoto.myapplication_app_contest.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lifeistech.naoto.myapplication_app_contest.R;
import com.lifeistech.naoto.myapplication_app_contest.Sugar.GroupTwoWords;
import com.lifeistech.naoto.myapplication_app_contest.Sugar.TwoWords;
import com.lifeistech.naoto.myapplication_app_contest.Sugar.TwoWordsWeak;
import com.lifeistech.naoto.myapplication_app_contest.adapters.ListSetUp;
import com.orm.SugarRecord;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("バカ天");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_list);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
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
        List<GroupTwoWords> list = SugarRecord.listAll(GroupTwoWords.class);
        number_size = list.size();
        for (GroupTwoWords groupTwoWords : list) {
            adapter.add(groupTwoWords);
        }
        createFab();
        SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
        int which = preferences.getInt("which", 0);
        if (which == 0) {
            //初めての場合
            showDialogUser(preferences);
        } else {
            View header = ((NavigationView) findViewById(R.id.nav_view)).getHeaderView(0);
            TextView textView = (TextView) header.findViewById(R.id.textView4);
            StringBuffer buffer = new StringBuffer();
            buffer.append("User:");
            String user = preferences.getString("user", null);
            buffer.append(user);
            textView.setText(buffer.toString());
        }
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
        SharedPreferences preferences1 = getSharedPreferences("weak_id", 0);
        long id = preferences1.getLong("weak_id", 0);
        GroupTwoWords groupTwoWords = GroupTwoWords.findById(GroupTwoWords.class, id);
        adapterWeak.add(groupTwoWords);
        //listview(download)

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
            alert.setTitle("Title");
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
            down_load();
        } else if (id == R.id.nav_registration) {
            showDialog_set_up();
        } else if (id == R.id.nav_solve) {
            showDialog_solve();
        } else if (id == R.id.nav_upload) {
            //アップロード
            up_load();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void make_Toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

    public void showDialog_solve() {
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
                Intent intent = new Intent(MainActivity.this, SolveWeakActivity.class);
                startActivity(intent);
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

    public void showDialog_set_up() {
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
                    make_Toast("グループの名前が登録されていません");
                } else {
                    Intent intent = new Intent(MainActivity.this, SetUpActivity.class);
                    intent.putExtra("group_name", group_name);
                    startActivity(intent);
                }
            }
        });
        builder.show();
    }

    public void showDialogUser(final SharedPreferences preferences) {
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
            }
        });
        builder.setCancelable(false).show();
        //後weakの設定
        GroupTwoWords groupTwoWords = new GroupTwoWords();
        groupTwoWords.setGROUP_NAME("間違えやすい");
        groupTwoWords.setCalendar(Calendar.getInstance());
        groupTwoWords.save();
        SharedPreferences preferences1 = getSharedPreferences("weak_id", 0);
        SharedPreferences.Editor editor = preferences1.edit();
        editor.putLong("weak_id", groupTwoWords.getId());
        editor.commit();
    }

    public void down_load() {
        //ダウンロードの処理
        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra("mode", 1);
        startActivity(intent);
    }

    public void up_load() {
        //アップロードの処理
        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra("mode", 2);
        startActivity(intent);
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

        if (isFabOpen) {
            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            textView1.startAnimation(fab_close);
            textView.startAnimation(fab_close);
            linearLayout.startAnimation(fab_close);
            linearLayout1.startAnimation(fab_close);
            fab3.startAnimation(fab_close);
            fab1.setClickable(false);
            isFabOpen = false;

        } else {

            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            linearLayout.startAnimation(fab_open);
            textView.startAnimation(fab_open);
            linearLayout1.startAnimation(fab_open);
            textView1.startAnimation(fab_open);
            fab3.startAnimation(fab_open);
            fab1.setClickable(true);
            isFabOpen = true;

        }
    }

    public void fab(View view) {
        animateFAB();
    }

    public void fab1(View view) {
        showDialog_set_up();
    }

    public void fab2(View view) {
        showDialog_solve();
    }

}
