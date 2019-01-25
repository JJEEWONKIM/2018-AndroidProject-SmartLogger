package com.uoc.jiwon.ss;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class VocActivity extends AirActivity {
    TextView cleanView;
    ImageView cleanWhatView;
    TextView valueView;
    TextView recommendView;

    private boolean airCleanerToggle = false;
    private boolean autoToggle = false;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voc);

        setTextView(cleanView,R.id.clean);
        setImageView(cleanWhatView,R.id.cleanWhat);
        setTextView(valueView,R.id.vocValue);
        setTextView(recommendView,R.id.recomBest);

        setBackgroundAnimation();
        setTextAnimation(R.id.clean,getApplicationContext());
        setReturnBtn(R.id.returnBtn);
        setInfoBtn(R.id.infoBtn,R.layout.vocinfo_dialog);

        setAirCleanerBtns(R.id.autoBtnVoc,R.id.airCleanerBtnVoc);
        setAircleanerBtn(R.id.autoBtnVoc,R.id.airCleanerBtnVoc);
        setAutoBtn(R.id.autoBtnVoc,R.id.airCleanerBtnVoc);
    }


    public void onStart() {
        super.onStart();
        super.tipListener(R.id.recomBest,"VOC");
        super.valueListener(R.id.clean,R.id.vocValue,R.id.cleanWhat,"VOC");

    }



    @Override
    public void setAutoBtn(int autoBtnId,int airCleanerBtnId){
        MiniDatabase miniDatabase = new MiniDatabase();
        ImageView autoBtn = (ImageView)findViewById(autoBtnId);
        ImageView aircleanerBtn = (ImageView)findViewById(airCleanerBtnId) ;
        autoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aircleanerBtn.setImageResource(R.drawable.switchoff);
                airCleanerToggle = false;
                if(autoToggle){

                    autoBtn.setImageResource(R.drawable.autooff);
                    resources.writeNewRelay(FirebaseConfig.aircleanerRelay, "Off");
                    miniDatabase.setRelayInfo(FirebaseConfig.aircleanerRelay,"Off",getApplicationContext());
                    autoToggle = false;
                }else{
                    autoBtn.setImageResource(R.drawable.auto);
                    resources.writeNewRelay(FirebaseConfig.aircleanerRelay, "power");
                    miniDatabase.setRelayInfo(FirebaseConfig.aircleanerRelay,"power",getApplicationContext());
                    autoToggle = true;
                }
            }
        });

    }

    @Override
    public void setAircleanerBtn(int autoBtnId,int airCleanerBtnId){
        MiniDatabase miniDatabase = new MiniDatabase();
        ImageView autoBtn = (ImageView)findViewById(autoBtnId);
        ImageView aircleanerBtn = (ImageView)findViewById(airCleanerBtnId) ;
        aircleanerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoBtn.setImageResource(R.drawable.autooff);
                autoToggle = false;
                if(airCleanerToggle){
                    aircleanerBtn.setImageResource(R.drawable.switchoff);
                    resources.writeNewRelay(FirebaseConfig.aircleanerRelay, "Off");
                    miniDatabase.setRelayInfo(FirebaseConfig.aircleanerRelay,"Off",getApplicationContext());
                    airCleanerToggle = false;
                }else{
                    aircleanerBtn.setImageResource(R.drawable.switchon);
                    resources.writeNewRelay(FirebaseConfig.aircleanerRelay, "On");
                    miniDatabase.setRelayInfo(FirebaseConfig.aircleanerRelay,"On",getApplicationContext());
                    airCleanerToggle = true;
                }
            }
        });
    }
}

