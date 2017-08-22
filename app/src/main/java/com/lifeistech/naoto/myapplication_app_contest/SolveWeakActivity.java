package com.lifeistech.naoto.myapplication_app_contest;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SolveWeakActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve_weak);
        List<TwoWordsWeak> list = SugarRecord.listAll(TwoWordsWeak.class);
        Collections.shuffle(list);
        ArrayList<String> japaneses = new ArrayList<>();
        ArrayList<String> englishes = new ArrayList<>();
        ArrayList<String> ids = new ArrayList<>();
        if(list.size() == 0){
            show_dialog_end();
        }else {
            Collections.shuffle(list);
            for (TwoWordsWeak twoWordsWeak:list){
                japaneses.add(twoWordsWeak.getWORDS_JAPANESE());
                englishes.add(twoWordsWeak.getWORDS_ENGLISH());
                ids.add(String.valueOf(twoWordsWeak.getId()));
            }
            Intent intent = new Intent(SolveWeakActivity.this, Solve2Activity.class);
            intent.putExtra("japaneses",japaneses);
            intent.putExtra("englishes",englishes);
            intent.putExtra("ids",ids);
            intent.putExtra("number",0);
            intent.putExtra("mode",1);
            startActivity(intent);
        }
    }

    public void show_dialog_end(){
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
