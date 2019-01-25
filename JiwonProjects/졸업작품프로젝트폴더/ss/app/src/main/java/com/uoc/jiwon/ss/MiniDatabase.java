package com.uoc.jiwon.ss;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class MiniDatabase {

    public String getUserId(Context context){

        SharedPreferences sharedPreferences = context.getSharedPreferences("userInfo",Context.MODE_PRIVATE );
        return sharedPreferences.getString("id", "null");
    }

    public String getHopeTemp(Context context){

        SharedPreferences sharedPreferences = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        return sharedPreferences.getString("hopeTemp", "21");
    }

    public String getTempHighRelay(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        return sharedPreferences.getString(FirebaseConfig.heaterRelay, "null");
    }

    public String getTempLowRelay(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        return sharedPreferences.getString(FirebaseConfig.coolerRelay, "null");
    }

    public String getHumidRelay(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        return sharedPreferences.getString(FirebaseConfig.humidifierRelay, "null");
    }

    public void setInfoIntoPreference(String thing, String value, Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(thing, value); //name이라는 key값으로 로그인한 이름 데이터를 저장한다.
        editor.commit(); //완료한다.
    }
    public String getAirCleanerRelay(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        return sharedPreferences.getString(FirebaseConfig.aircleanerRelay, "null");
    }

    public void setRelayInfo(String relay, String value,Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(relay, value); //name이라는 key값으로 로그인한 이름 데이터를 저장한다.
        editor.commit(); //완료한다.
    }


}
