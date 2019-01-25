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

public class Co2Activity extends AirActivity {
    TextView cleanView;
    ImageView cleanWhatView;
    TextView valueView;
    TextView recommendView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_co2);

        setTextView(cleanView,R.id.clean);
        setImageView(cleanWhatView,R.id.cleanWhat);
        setTextView(valueView,R.id.co2Value);
        setTextView(recommendView,R.id.recomBest);

        setBackgroundAnimation();
        setTextAnimation(R.id.clean,getApplicationContext());
        setReturnBtn(R.id.returnBtn);
        setInfoBtn(R.id.infoBtn,R.layout.co2info_dialog);

    }
    public void onStart(){
        super.onStart();
        super.tipListener(R.id.recomBest,"CO2");
        super.valueListener(R.id.clean,R.id.co2Value,R.id.cleanWhat,"CO2");

    }
    @Override
    public void setAircleanerBtn(int autoBtnId,int airCleanerBtnId){};
    @Override
    public void setAutoBtn(int autoBtnId,int airCleanerBtnId){};

}


