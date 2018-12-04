package com.example.praba.prakmob;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.praba.prakmob.Activity.LoginActivity;
import com.example.praba.prakmob.Activity.MainActivity;
import com.example.praba.prakmob.Helper.PreferenceHelper;

public class Splash extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    PreferenceHelper preferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        preferencesHelper = new PreferenceHelper(this);
        boolean login = preferencesHelper.getLogin();
        if(login){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }else{
            setContentView(R.layout.activity_splash);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Splash.this,LoginActivity.class));
                    finish();
                }
            },2000);
        }
    }
}
