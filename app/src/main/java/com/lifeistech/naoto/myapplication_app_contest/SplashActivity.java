package com.lifeistech.naoto.myapplication_app_contest;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        TextView textView = (TextView)findViewById(R.id.textView4);
        textView.setTypeface(Typeface.createFromAsset(getAssets(), "SignPainter_copy.ttc"));
        textView.setText("Let's learn English!");
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, Main2Activity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        },1*1000);
    }
}
