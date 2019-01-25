package com.uoc.jiwon.ss;

import android.content.Context;
import android.widget.Button;
import android.widget.ImageView;

public class autoBtnOffStrategy implements SwitchingStrategy {
    public autoBtnOffStrategy(ImageView button,Context context,MicrodustActivity microdustActivity){
        writeNewRelay(context);
        setRelayInfo(context);
        setImage(button);
        setToggle(microdustActivity);
    }
    @Override
    public void writeNewRelay(Context context){
        new ResourceMicroDust().writeNewRelay(FirebaseConfig.aircleanerRelay, "power");
    }
    @Override
    public  void setRelayInfo(Context context){
        MiniDatabase miniDatabase = new MiniDatabase();
        miniDatabase.setRelayInfo(FirebaseConfig.aircleanerRelay,"power",context);
    }
    @Override
    public void setImage(ImageView button){
        button.setImageResource(R.drawable.auto);
    }
    @Override
    public void setToggle(MicrodustActivity microdustActivity){
        microdustActivity.setAirCleanerToggle(false);
        microdustActivity.setAutoToggle(true);
    }
}

