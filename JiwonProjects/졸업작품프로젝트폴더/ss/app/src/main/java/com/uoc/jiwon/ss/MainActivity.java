package com.uoc.jiwon.ss;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class MainActivity extends Activity {
    private DatabaseReference rootRef =  FirebaseDatabase.getInstance().getReference().child(FirebaseConfig.routeRoot).child(FirebaseConfig.route1);
    private DatabaseReference mainDatabaseReference = rootRef.child(FirebaseConfig.route2);
    private ValueEventListener mMainListener;
    ValueEventListener mainListener;


    LinearLayout MainRootLayout;
    AnimationDrawable animationDrawable ;

    LinearLayout tempHumidLayout;
    RelativeLayout co2Layout;
    RelativeLayout microdustLayout;
    RelativeLayout VocLayout;

    String name;
    ImageView mypageBtn;
    ImageView logoutBtn;
    TextView mainTempHumidView;
    TextView mainCo2View;
    TextView mainMicrodustView;
    TextView mainVocView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainRootLayout = (LinearLayout)findViewById(R.id.MainRootLayout);

        tempHumidLayout = (LinearLayout) findViewById (R.id.tempHumidLayout);
        co2Layout = (RelativeLayout)findViewById(R.id.co2Layout);
        microdustLayout = (RelativeLayout)findViewById(R.id.microdustLayout);
        VocLayout = (RelativeLayout) findViewById(R.id.vocLayout);

        animationDrawable = (AnimationDrawable) MainRootLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(4500);
        animationDrawable.start();



        mainTempHumidView = (TextView)findViewById(R.id.mainTempHumid);
        mainCo2View = (TextView)findViewById(R.id.mainCo2);
        mainMicrodustView = (TextView)findViewById(R.id.mainMicroDust);
        mainVocView = (TextView)findViewById(R.id.mainVoc);
        mypageBtn = (ImageView)findViewById(R.id.mypage);
        logoutBtn = (ImageView)findViewById(R.id.logout);


        tempHumidLayout.setOnClickListener(new LinearLayout.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TempHumidActivity.class);
                startActivity(intent);

            }
        });
        co2Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Co2Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });
        microdustLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MicrodustActivity.class);
                startActivity(intent);


            }
        });
        VocLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), VocActivity.class);
                startActivity(intent);

            }
        });

        mypageBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO : click event
                Intent intent = new Intent(getApplicationContext(), MypageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);


            }
        });


        logoutBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO : click event
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);


            }
        });



    }
    @Override
    public void onPause() {
        super.onPause();
       // mainDatabaseReference.removeEventListener(mainListener);
        // Remove the activity when its off the screen
        finish();
    }

    public void onStart(){
        super.onStart();
        Log.d("real","mainActivityOnStart");

        mainListener = new ValueEventListener() { //voc 리스너
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                LatestData latestData = dataSnapshot.getValue(LatestData.class);
                mainTempHumidView.setText(ConvertData.UNIQUE.doubleToString(ConvertData.UNIQUE.stringToDouble(latestData.temp))+"도, "+ConvertData.UNIQUE.doubleToString(ConvertData.UNIQUE.stringToDouble(latestData.humid))+"%");
                mainMicrodustView.setText(ConvertData.UNIQUE.doubleToString(ConvertData.UNIQUE.stringToDouble(latestData.microdust))+" ㎍/㎥");
                mainCo2View.setText(ConvertData.UNIQUE.doubleToString(ConvertData.UNIQUE.stringToDouble(latestData.co2))+" ppm");
                mainVocView.setText(ConvertData.UNIQUE.doubleToString(ConvertData.UNIQUE.stringToDouble(latestData.voc))+" ppb");
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
            }
        };
        mainDatabaseReference.addValueEventListener(mainListener);
        mMainListener= mainListener;

    }





}
