package com.example.organicfarming;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        SharedPreferences sharedPreferences=getPreferences(Context.MODE_PRIVATE);
        Log.d("logged",String.valueOf(sharedPreferences.getBoolean("logged",false)));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences=getSharedPreferences("UserData",Context.MODE_PRIVATE);
                Log.d("logged",String.valueOf(sharedPreferences.getBoolean("logged",false)));
                Log.d("USER",sharedPreferences.getString("UID","AA")+"----"+sharedPreferences.getString("type","00"));
                if(sharedPreferences.getBoolean("logged",true) && sharedPreferences.getString("type","").equals("farmer")){
                    startActivity(new Intent(SplashScreenActivity.this,FarmerActivity.class));
                    SplashScreenActivity.this.finish();
                }
                else if(sharedPreferences.getBoolean("logged",true) && sharedPreferences.getString("type","").equals("buyer")){
                    startActivity(new Intent(SplashScreenActivity.this,BuyerActivity.class));
                    SplashScreenActivity.this.finish();
                }
                else {
                    startActivity(new Intent(SplashScreenActivity.this, RegisterActivity.class));
                    SplashScreenActivity.this.finish();
                }
            }
        },500);
    }
}