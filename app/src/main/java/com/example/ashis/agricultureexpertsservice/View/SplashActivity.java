package com.example.ashis.agricultureexpertsservice.View;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.ashis.agricultureexpertsservice.DashboardActivity;
import com.example.ashis.agricultureexpertsservice.R;
import com.example.ashis.agricultureexpertsservice.utils.SharedPrefUtils;

public class SplashActivity extends AppCompatActivity {

    int SPLASH_SCREEN_DURATION = 4000;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        context = getApplicationContext();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(SharedPrefUtils.getStringPreference(getApplicationContext(),"localId") == null){

                    startActivity(new Intent(SplashActivity.this,LoginInActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));

                }else{

                    if(SharedPrefUtils.getStringPreference(getApplicationContext(),"userType").equalsIgnoreCase("farmer")){

                        Intent intent = new Intent(SplashActivity.this,DashboardActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                    }else{

                        Intent intent = new Intent(SplashActivity.this,PostNavigationActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }
            }

        },SPLASH_SCREEN_DURATION);
    }
}
