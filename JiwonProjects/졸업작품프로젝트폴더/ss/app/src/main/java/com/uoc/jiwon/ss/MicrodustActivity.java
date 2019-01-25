package com.uoc.jiwon.ss;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
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


public class MicrodustActivity extends AirActivity {
    SwitchingStrategy switchingStrategy;
    MicrodustActivity microdustActivity = this;

    TextView cleanView;
    ImageView cleanWhatView;
    TextView valueView;
    TextView recommendView;

    private boolean airCleanerToggle = false;
    private boolean autoToggle = false;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_microdust);

        setTextView(cleanView,R.id.clean);
        setImageView(cleanWhatView,R.id.cleanWhat);
        setTextView(valueView,R.id.microdustValue);
        setTextView(recommendView,R.id.recomBest);

        setBackgroundAnimation();
        setTextAnimation(R.id.clean,getApplicationContext());
        setReturnBtn(R.id.returnBtn);
        setInfoBtn(R.id.infoBtn,R.layout.microdustinfo_dialog);

        setAirCleanerBtns(R.id.autoBtn,R.id.airCleanerBtnMicroDust);
        setAircleanerBtn(R.id.autoBtn,R.id.airCleanerBtnMicroDust);
        setAutoBtn(R.id.autoBtn,R.id.airCleanerBtnMicroDust);
    }

    public void onStart(){
        super.onStart();
        super.tipListener(R.id.recomBest,"MICRODUST");
        super.valueListener(R.id.clean,R.id.microdustValue,R.id.cleanWhat,"MICRODUST");

    }
    public void setAutoToggle(Boolean toggle){
        autoToggle = toggle;
    }
    public void setAirCleanerToggle(Boolean toggle){
        airCleanerToggle = toggle;
    }
    @Override
    public void setAutoBtn(int autoBtnId,int airCleanerBtnId){
        ImageView autoBtn = (ImageView)findViewById(autoBtnId);
        ImageView aircleanerBtn = (ImageView)findViewById(airCleanerBtnId) ;
        autoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aircleanerBtn.setImageResource(R.drawable.switchoff);
                if(autoToggle) switchingStrategy = new autoBtnOnStrategy(autoBtn,getApplicationContext(),microdustActivity);
                else switchingStrategy = new autoBtnOffStrategy(autoBtn,getApplicationContext(),microdustActivity);
            }
        });

    }
    @Override
    public void setAircleanerBtn(int autoBtnId,int airCleanerBtnId){
        ImageView autoBtn = (ImageView)findViewById(autoBtnId);
        ImageView aircleanerBtn = (ImageView)findViewById(airCleanerBtnId) ;
        aircleanerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoBtn.setImageResource(R.drawable.autooff);
                if(airCleanerToggle) switchingStrategy = new aircleanerBtnOnStrategy(aircleanerBtn,getApplicationContext(),microdustActivity);
                else switchingStrategy = new aircleanerBtnOffStrategy(aircleanerBtn,getApplicationContext(),microdustActivity);
            }
        });
    }
}


