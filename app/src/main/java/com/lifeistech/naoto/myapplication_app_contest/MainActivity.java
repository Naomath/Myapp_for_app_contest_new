package com.lifeistech.naoto.myapplication_app_contest;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    int mCheckedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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
        } else if (id == R.id.nav_sns) {
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
        } else if (id == R.id.nav_registration) {
            showDialog_set_up();
        } else if (id == R.id.nav_list) {
            show_dialog_list();
        } else if (id == R.id.nav_solve) {
            showDialog_solve();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void make_Toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

    public void solve(View view) {
        //今日の問題を解く処理
        showDialog_solve();
    }

    public void set_up(View view) {
        // 問題を登録する処理
        showDialog_set_up();
    }

    public void list(View view) {
        show_dialog_list();
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

    public void show_dialog_list() {
        //リストについて処理
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("単語リスト");
        builder.setMessage("どのリストを見ますか？");
        builder.setNegativeButton("間違えやすい", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //間違えやすいの
                Intent intent = new Intent(MainActivity.this, ListWeakActivity.class);
                startActivity(intent);

            }
        });
        builder.setPositiveButton("全部", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //普通の
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                startActivity(intent);
            }
        });

        builder.setNeutralButton("キャンセル", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //キャンセルする時の処理
                dialogInterface.dismiss();
            }
        });

        builder.show();
    }

}
