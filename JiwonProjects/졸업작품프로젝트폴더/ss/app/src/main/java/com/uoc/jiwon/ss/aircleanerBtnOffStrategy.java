package com.uoc.jiwon.ss;

import android.content.Context;
import android.widget.Button;
import android.widget.ImageView;

public class aircleanerBtnOffStrategy implements SwitchingStrategy {
    public aircleanerBtnOffStrategy(ImageView button,Context context,MicrodustActivity microdustActivity){
        writeNewRelay(context);
        setRelayInfo(context);
        setImage(button);
        setToggle(microdustActivity);
    }
    @Override
    public void writeNewRelay(Context context){
        new ResourceMicroDust().writeNewRelay(FirebaseConfig.aircleanerRelay, "On");
    }
    @Override
    public  void setRelayInfo(Context context){
        MiniDatabase miniDatabase = new MiniDatabase();
        miniDatabase.setRelayInfo(FirebaseConfig.aircleanerRelay,"On",context);
    }
    @Override
    public void setImage(ImageView button){
       button.setImageResource(R.drawable.switchon);
    }
    @Override
    public void setToggle(MicrodustActivity microdustActivity){
        microdustActivity.setAutoToggle(false);
        microdustActivity.setAirCleanerToggle(true);
    }
}

