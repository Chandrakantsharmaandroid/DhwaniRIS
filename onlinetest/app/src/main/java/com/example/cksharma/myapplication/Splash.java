package com.example.cksharma.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;


public class Splash extends AppCompatActivity {

    private static int DISPLAY_DURATION = 1000;
    int dataBaseExist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub

                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                if(pref.getBoolean("success",false)) {
                    Intent intent = new Intent(Splash.this, MainActivity.class);
                    startActivity(intent);
                    Splash.this.finish();
                }else {
                    Intent intent = new Intent(Splash.this, LoginActivity.class);
                    startActivity(intent);
                    Splash.this.finish();
                }



            }
        }, DISPLAY_DURATION);
}

}
