package com.example.passwordvault;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {


    String password;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences settings = getSharedPreferences("PREFS",0);
        password = settings.getString("password","");


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (password.equals("")) {
                    Intent intent = new Intent(getApplicationContext(),CreatePassActivity.class);
                    startActivity(intent);
                    finish();
                } else
                {
                    Intent intent = new Intent(getApplicationContext(),EnterPassActivity.class);
                    startActivity(intent);
                    finish();
                }


            }
        }, 4000);
        getSupportActionBar().hide();

    }

}
