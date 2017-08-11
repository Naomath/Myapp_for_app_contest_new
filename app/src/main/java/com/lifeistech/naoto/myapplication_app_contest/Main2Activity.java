package com.lifeistech.naoto.myapplication_app_contest;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        DrawerLayout mDrawerLayoutr = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayoutr.openDrawer(GravityCompat.START);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void solve(View view) {
        //今日の問題を解く処理
        showDialog_solve();
    }

    public void list(View view) {
        //リストについて処理
        Intent intent = new Intent(Main2Activity.this, ListActivity.class);
        startActivity(intent);
    }

    public void set_up(View view) {
        // 問題を登録する処理
        showDialog_set_up();
    }

    public void showDialog_solve() {
        //ダイアログを表示するメソッド
        //solveの時
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_title);
        builder.setMessage(R.string.message);
        builder.setNegativeButton(R.string.dialog_today, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //今日の問題を解く時の処理


                Intent intent = new Intent(Main2Activity.this, SolveActivity.class);
                startActivity(intent);
                //これからは引数を加え、今日の問題か、間違えやすい問題かわかるようにする

            }
        });
        builder.setPositiveButton(R.string.dialog_weak, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //間違えやすい問題を解く時の処理
                Intent intent = new Intent(Main2Activity.this, SolveActivity.class);
                startActivity(intent);
                //これからは引数を加え、今日の問題か、間違えやすい問題かわかるようにする

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
        final EditText editText = new EditText(Main2Activity.this);
        //ダイアログで入力用のedittext
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Set Up");
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
                    Intent intent = new Intent(Main2Activity.this, SetUpActivity.class);
                    intent.putExtra("group_name", group_name);
                    startActivity(intent);
                }
            }
        });
        builder.show();
    }

    public void make_Toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }
}