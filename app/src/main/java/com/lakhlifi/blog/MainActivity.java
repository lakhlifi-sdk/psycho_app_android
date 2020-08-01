package com.lakhlifi.blog;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //this code will pause the app for 1,5s
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                /*SharedPreferences userPref=getApplicationContext().getSharedPreferences("user",Context.MODE_PRIVATE);
                boolean isLogin=userPref.getBoolean("isLogin",false);
                if(isLogin){
                    startActivity(new Intent(MainActivity.this,HomeActivity.class));
                    fileList();
                }else{
                    isFirstTilme();
                }*/
                isFirstTilme();
            }
        },1000);
    }

    private void isFirstTilme() {
        //for checking if the app is running for the first time
        //we need to save a value to sheared preference

        SharedPreferences preferences= getApplication().getSharedPreferences("onBoard", Context.MODE_PRIVATE);
        boolean isFirstTime=preferences.getBoolean("isFirstTime",true);
        if(isFirstTime){
            SharedPreferences.Editor editor=preferences.edit();
            editor.putBoolean("isFirstTime",false);
            editor.apply();
            //shared onBoard Activity
            startActivity(new Intent(MainActivity.this,OnboardActivity.class));
            finish();
        }else{
            startActivity(new Intent(MainActivity.this,AuthActivity.class));
            finish();

        }

    }
}