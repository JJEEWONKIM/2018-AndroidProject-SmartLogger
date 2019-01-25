package com.uoc.jiwon.ss;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class TempHumidActivity extends AirActivity {
    Resources resources = new ResourceTempHumid();
    ImageView hopeTempBtn;
    ImageView returnBtn;

    LinearLayout MainRootLayout;
    AnimationDrawable animationDrawable ;

    private static final String TEMPERATURE_LEVEL = "level";
    private TextView mTemperatureValue;
    int level;
    String name;

    TextView tempHumidCleanView;
    ImageView tempHumidCleanWhatView;
    TextView tempView;
    TextView humidView;
    TextView tempHumidRecommendView;
    TextView hopeTempView;
    private Double tempHumidCleanValue;
    private Double tempValue;
    private Double humidValue;
    private int hopeTemp=21;
    private String tempRemainTime;
    private String tempRecommend;
    private String humidRecommend;

    ImageView autoBtnTempHigh;
    ImageView autoBtnTempLow;
    ImageView autoBtnHumid;
    ImageView tempHighBtn;
    ImageView tempLowBtn;
    ImageView humidBtn;
    private boolean tempHighToggle = false;
    private boolean tempLowToggle = false;
    private boolean humidToggle = false;
    private boolean tempHighAutoToggle = false;
    private boolean tempLowAutoToggle = false;
    private boolean humidAutoToggle = false;

    private MiniDatabase miniDatabase = new MiniDatabase();
    private DatabaseReference rootRef =  FirebaseDatabase.getInstance().getReference().child(FirebaseConfig.routeRoot).child(FirebaseConfig.route1);
    private DatabaseReference databaseReference = rootRef.child(FirebaseConfig.route2);
    private DatabaseReference tipReference;
    private ValueEventListener mDataListener;
    private ValueEventListener mTipListener;



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temphumid);

        tempHumidCleanView = (TextView)findViewById(R.id.clean);
        tempHumidCleanWhatView = (ImageView)findViewById(R.id.cleanWhat);
        tempView = (TextView)findViewById(R.id.temperaturevalue);
        humidView = (TextView)findViewById(R.id.humidityValue);
        tempHumidRecommendView = (TextView)findViewById(R.id.recomBest);

        setBackgroundAnimation();
        setTextAnimation(R.id.clean,getApplicationContext());
        setReturnBtn(R.id.returnBtn);

        autoBtnTempHigh = (ImageView)findViewById(R.id.autoBtnTempHigh);
        autoBtnTempLow = (ImageView)findViewById(R.id.autoBtnTempLow);
        autoBtnHumid = (ImageView)findViewById(R.id.autoBtnHumid);

        hopeTemp = 21;
        hopeTempView = (TextView)findViewById(R.id.hopeTemp);
        hopeTempView.setText("희망온도 :"+miniDatabase.getHopeTemp(getApplicationContext())+"°C");
        hopeTempBtn = (ImageView)findViewById(R.id.hopeTempIcon);
        hopeTempBtn.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
               showHopeTempDialog();
            }
        });

        tempHighBtn = (ImageView)findViewById(R.id.switchTempHigh);
        setImage(R.id.switchTempHigh,miniDatabase.getTempHighRelay(getApplicationContext()),R.id.autoBtnTempHigh);
        tempHighBtn.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoBtnTempHigh.setImageResource(R.drawable.autooff);
                if(tempHighToggle == true){
                    tempHighBtn.setImageResource(R.drawable.switchoff);
                    resources.writeNewRelay(FirebaseConfig.heaterRelay, "Off");
                    miniDatabase.setInfoIntoPreference(FirebaseConfig.heaterRelay,"Off",getApplicationContext());
                    tempHighToggle = false;
                    tempHighAutoToggle = false;
                }else{
                    tempHighBtn.setImageResource(R.drawable.switchon);
                    resources.writeNewRelay(FirebaseConfig.heaterRelay, "On");
                    miniDatabase.setInfoIntoPreference(FirebaseConfig.heaterRelay,"On",getApplicationContext());
                    tempHighToggle = true;
                }
            }
        });

        tempLowBtn = (ImageView)findViewById(R.id.switchTempLow);
        setImage(R.id.switchTempLow,miniDatabase.getTempLowRelay(getApplicationContext()),R.id.autoBtnTempLow);
        tempLowBtn.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoBtnTempLow.setImageResource(R.drawable.autooff);
                if(tempLowToggle == true){
                    tempLowBtn.setImageResource(R.drawable.switchoff);
                    resources.writeNewRelay(FirebaseConfig.coolerRelay, "Off");
                    miniDatabase.setInfoIntoPreference(FirebaseConfig.coolerRelay,"Off",getApplicationContext());
                    tempLowToggle = false;
                    tempLowAutoToggle = false;
                }else{
                    tempLowBtn.setImageResource(R.drawable.switchon);
                    resources.writeNewRelay(FirebaseConfig.coolerRelay, "On");
                    miniDatabase.setInfoIntoPreference(FirebaseConfig.coolerRelay,"On",getApplicationContext());
                    tempLowToggle = true;
                }
            }
        });

        humidBtn = (ImageView)findViewById(R.id.switchHumid);
        setImage(R.id.switchHumid,miniDatabase.getHumidRelay(getApplicationContext()),R.id.autoBtnHumid);
        humidBtn.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoBtnHumid.setImageResource(R.drawable.autooff);
                if(humidToggle == true){
                    humidBtn.setImageResource(R.drawable.switchoff);
                    resources.writeNewRelay(FirebaseConfig.humidifierRelay, "Off");
                    miniDatabase.setInfoIntoPreference(FirebaseConfig.humidifierRelay,"Off",getApplicationContext());
                    humidToggle = false;
                    humidAutoToggle = false;
                }else{
                    humidBtn.setImageResource(R.drawable.switchon);
                    resources.writeNewRelay(FirebaseConfig.humidifierRelay, "On");
                    miniDatabase.setInfoIntoPreference(FirebaseConfig.humidifierRelay,"On",getApplicationContext());
                    humidToggle = true;
                }
            }
        });



        autoBtnTempHigh.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempHighBtn.setImageResource(R.drawable.switchoff);
                if(tempHighAutoToggle == true){
                    autoBtnTempHigh.setImageResource(R.drawable.autooff);
                    resources.writeNewRelay(FirebaseConfig.heaterRelay, "Off");
                    miniDatabase.setInfoIntoPreference(FirebaseConfig.heaterRelay,"Off",getApplicationContext());
                    tempHighAutoToggle = false;
                    tempHighToggle = false;
                }else{
                    autoBtnTempHigh.setImageResource(R.drawable.auto);
                    resources.writeNewRelay(FirebaseConfig.heaterRelay, "power");
                    miniDatabase.setInfoIntoPreference(FirebaseConfig.heaterRelay,"power",getApplicationContext());
                    tempHighAutoToggle = true;
                }
            }
        });


        autoBtnTempLow.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempLowBtn.setImageResource(R.drawable.switchoff);
                if(tempLowAutoToggle == true){
                    autoBtnTempLow.setImageResource(R.drawable.autooff);
                    resources.writeNewRelay(FirebaseConfig.coolerRelay, "Off");
                    miniDatabase.setInfoIntoPreference(FirebaseConfig.coolerRelay,"Off",getApplicationContext());
                    tempLowToggle = false;
                    tempLowAutoToggle = false;
                }else{
                    autoBtnTempLow.setImageResource(R.drawable.auto);
                    resources.writeNewRelay(FirebaseConfig.coolerRelay, "power");
                    miniDatabase.setInfoIntoPreference(FirebaseConfig.coolerRelay,"power",getApplicationContext());
                    tempLowAutoToggle = true;
                }
            }
        });

        autoBtnHumid.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                humidBtn.setImageResource(R.drawable.switchoff);
                if(humidAutoToggle == true){
                    autoBtnHumid.setImageResource(R.drawable.autooff);
                    resources.writeNewRelay(FirebaseConfig.humidifierRelay, "Off");
                    miniDatabase.setInfoIntoPreference(FirebaseConfig.humidifierRelay,"Off",getApplicationContext());
                    humidAutoToggle = false;
                    humidToggle = false;
                }else{
                    autoBtnHumid.setImageResource(R.drawable.auto);
                    resources.writeNewRelay(FirebaseConfig.humidifierRelay, "power");
                    miniDatabase.setInfoIntoPreference(FirebaseConfig.humidifierRelay,"power",getApplicationContext());
                    humidAutoToggle = true;
                }
            }
        });



    }

    public void onStart(){
        super.onStart();
        ValueEventListener valueListener = new ValueEventListener() { //voc 리스너
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                LatestData latestData = dataSnapshot.getValue(LatestData.class);
                tempValue = ConvertData.UNIQUE.stringToDouble(latestData.temp);
                humidValue = ConvertData.UNIQUE.stringToDouble(latestData.humid);
                tempHumidCleanView.setText(ConvertData.UNIQUE.doubleToString(ConvertData.UNIQUE.getCleanValue(hopeTemp,tempValue,humidValue)));
                tempView.setText("        "+ConvertData.UNIQUE.doubleToString(tempValue));
                humidView.setText("      "+ConvertData.UNIQUE.doubleToString(humidValue));
                tempRemainTime = RemainTime.getRemainTime(tempValue,Integer.parseInt(miniDatabase.getHopeTemp(getApplicationContext())));
                Log.d("jiwon",hopeTemp+"");
                tempHumidRecommendView.setText(RemainTime.setRecomBest(tempRemainTime,"온도조절기"));
                setEmoticonImg(R.id.cleanWhat);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
            }
        };
        databaseReference.addValueEventListener(valueListener);
        mDataListener= valueListener;

    }



    protected void showHopeTempDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(TempHumidActivity.this);
        View promptView = layoutInflater.inflate(R.layout.temphumidinfo_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TempHumidActivity.this);
        alertDialogBuilder.setView(promptView);

        final EditText editText = (EditText) promptView.findViewById(R.id.message);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        hopeTemp = Integer.parseInt(editText.getText().toString());
                        miniDatabase.setInfoIntoPreference("hopeTemp",hopeTemp+"",getApplicationContext());
                        hopeTempView.setText("희망온도 : " + editText.getText()+"°C");
                        resources.setHopeTemp(hopeTemp);
                        tempRemainTime = RemainTime.getRemainTime(tempValue,hopeTemp);
                        Log.d("jiwon",hopeTemp+"");
                        tempHumidRecommendView.setText(RemainTime.setRecomBest(tempRemainTime,"온도조절기"));
                        tempHumidCleanView.setText(ConvertData.UNIQUE.doubleToString(ConvertData.UNIQUE.getCleanValue(hopeTemp,tempValue,humidValue)));
                        setEmoticonImg(R.id.cleanWhat);
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
    @Override
    public void setAircleanerBtn(int autoBtnId,int airCleanerBtnId){};
    @Override
    public void setAutoBtn(int autoBtnId,int airCleanerBtnId){};

    public void setEmoticonImg(int emoticonImgId){
        ImageView tempHumidCleanWhatView = (ImageView)findViewById(emoticonImgId);
        double value = ConvertData.UNIQUE.getCleanValue("TEMPHUMID",tempValue,humidValue);
        if(value >= 80.0) {
            tempHumidCleanWhatView.setImageResource(R.drawable.love);
        }else if(value>= 50 && value <= 80){
            tempHumidCleanWhatView.setImageResource(R.drawable.normal);
        }else{
            tempHumidCleanWhatView.setImageResource(R.drawable.notgood);
        }
    }

}
