package com.uoc.jiwon.ss;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


public class SplashActivity extends Activity {
    private Intent intent;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        intent = new Intent(getApplicationContext(),LoginActivity.class);
        try{
            Thread.sleep(2000); //대기 초 설정
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        startActivity(intent);
        finish();
    }
}
