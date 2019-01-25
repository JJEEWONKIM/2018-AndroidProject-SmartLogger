package com.uoc.jiwon.ss;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Looper;
import android.provider.ContactsContract;
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

public abstract class AirActivity extends Activity {
    Resources resources;
    View layout;

    private Double value;
    private String recommendValue;

    private DatabaseReference rootRef =  FirebaseDatabase.getInstance().getReference().child(FirebaseConfig.routeRoot).child(FirebaseConfig.route1);
    private DatabaseReference databaseReference = rootRef.child(FirebaseConfig.route2);
    private DatabaseReference TipReference;
    private ValueEventListener mListener;
    private ValueEventListener TipListener;
    private MiniDatabase miniDatabase = new MiniDatabase();

    public void setTextView(TextView view, int id){
        view = (TextView)findViewById(id);
    }
    public void setImageView(ImageView view, int id){
        view = (ImageView)findViewById(id);
    }
    public void setBackgroundAnimation(){
        LinearLayout MainRootLayout = (LinearLayout)findViewById(R.id.MainRootLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) MainRootLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(4500);
        animationDrawable.start();
    }
    public void setTextAnimation(int cleanViewId,Context context){
        TextView cleanView = (TextView)findViewById(cleanViewId);
        Animation startAnimation = AnimationUtils.loadAnimation(context, R.anim.blink_animation);
        cleanView.startAnimation(startAnimation);
    }
    public void setReturnBtn(int id){
        ImageView returnBtn = (ImageView)findViewById(id);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }

    public void setInfoBtn(int id,int layoutId){
        ImageView infoBtn = (ImageView)findViewById(id);
        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoDialog(layoutId);

            }
        });

    }

    public void infoDialog(int layoutId){
        Context mContext = getApplicationContext();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        layout = inflater.inflate(layoutId,(ViewGroup) findViewById(R.id.layout_root));
        AlertDialog.Builder aDialog = new AlertDialog.Builder(this);

        aDialog.setView(layout);
        aDialog.setNegativeButton("닫기", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog ad = aDialog.create();
        ad.show();
    }

   public void tipListener(int id,String kind){

        TipReference = rootRef.child(miniDatabase.getUserId(getApplicationContext())).child(FirebaseConfig.route3);
        ValueEventListener tipListener = new ValueEventListener() { //voc 리스너
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Tip tip  = dataSnapshot.getValue(Tip.class);
                switch (kind){
                    case "CO2":
                        recommendValue = tip.co2Tip;
                        break;
                    case "MICRODUST":
                        recommendValue = tip.microdustTip;
                        break;
                    case "VOC":
                        recommendValue = tip.vocTip;
                        break;
                    case "TEMP":
                        recommendValue = tip.tempTip;
                        break;
                    case "HUMID":
                        recommendValue = tip.humidTip;
                    default:
                        recommendValue = "";
                }
                TextView recommendView = (TextView)findViewById(id);
                recommendView.setText(recommendValue);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
            }
        };
       TipReference.addValueEventListener(tipListener);
       TipListener= tipListener;
   }


    public void valueListener(int cleanId,int valueId,int cleanWhatId, String kind){
        ValueEventListener valueListener = new ValueEventListener() { //voc 리스너
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                TextView cleanView = (TextView)findViewById(cleanId);
                TextView valueView = (TextView)findViewById(valueId);
                ImageView cleanWhatView = (ImageView) findViewById(cleanWhatId);
                LatestData latestData = dataSnapshot.getValue(LatestData.class);
                switch (kind){
                    case "CO2":
                        resources = new ResourceCo2();
                        value =  ConvertData.UNIQUE.stringToDouble(latestData.co2);
                        break;
                    case "MICRODUST":
                        resources = new ResourceMicroDust();
                        value =  ConvertData.UNIQUE.stringToDouble(latestData.microdust);
                        break;
                    case "VOC":
                        resources = new ResourceVoc();
                        value =  ConvertData.UNIQUE.stringToDouble(latestData.voc);
                        break;
                    case "TEMP":
                        resources = new ResourceTempHumid();
                        value =  ConvertData.UNIQUE.stringToDouble(latestData.temp);
                        break;
                    case "HUMID":
                        resources = new ResourceTempHumid();
                        value =  ConvertData.UNIQUE.stringToDouble(latestData.humid);
                    default:
                        recommendValue = "0.0";
                }

                Log.d("special",value+"");
                new Thread(new Runnable() { @Override public void run() { for(int i = 0; i<=100; i++) {
                    // 현재 UI 스레드가 아니기 때문에 메시지 큐에 Runnable을 등록 함
                    runOnUiThread(new Runnable() { public void run() { // 메시지 큐에 저장될 메시지의 내용
                        cleanView.setText(ConvertData.UNIQUE.doubleToString(ConvertData.UNIQUE.getCleanValue(kind,value)));
                        if(kind.equals("MICRODUST")) valueView.setText("           "+ConvertData.UNIQUE.doubleToString(value));
                        else valueView.setText("   "+ConvertData.UNIQUE.doubleToString(value));
                        cleanWhatView.setImageResource(resources.setImage(value)); } }); } } }).start();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
            }
        };
        databaseReference.addValueEventListener(valueListener);
        mListener= valueListener;
    }
    public void setAirCleanerBtns(int autoBtnId, int airCleanerBtnId){
        ImageView autoBtn = (ImageView)findViewById(autoBtnId);
        ImageView aircleanerBtn = (ImageView)findViewById(airCleanerBtnId) ;
        MiniDatabase miniDatabase = new MiniDatabase();
        String currentState = miniDatabase.getAirCleanerRelay(getApplicationContext());
        switch (currentState){
            case "power" :
                autoBtn.setImageResource(R.drawable.auto);
                aircleanerBtn.setImageResource(R.drawable.switchoff);
                break;
            case "On" :
                autoBtn.setImageResource(R.drawable.autooff);
                aircleanerBtn.setImageResource(R.drawable.switchon);
                break;
            case "Off":
                autoBtn.setImageResource(R.drawable.autooff);
                aircleanerBtn.setImageResource(R.drawable.switchoff);
                break;
            default:
                break;

        }
    }

    public void setImage(int btnId, String value, int autoBtnId){
        ImageView btn = (ImageView)findViewById(btnId);
        ImageView autoBtn = (ImageView)findViewById(autoBtnId);
        if(value=="On"){
            btn.setImageResource(R.drawable.switchon);
            autoBtn.setImageResource(R.drawable.autooff);
        }else if(value == "Off"){
            btn.setImageResource(R.drawable.switchoff);
            autoBtn.setImageResource(R.drawable.autooff);
        }else{
            btn.setImageResource(R.drawable.switchoff);
            autoBtn.setImageResource(R.drawable.auto);
        }
    }


    public abstract void setAircleanerBtn(int autoBtnId,int airCleanerBtnId);
    public abstract void setAutoBtn(int autoBtnId,int airCleanerBtnId);

}



