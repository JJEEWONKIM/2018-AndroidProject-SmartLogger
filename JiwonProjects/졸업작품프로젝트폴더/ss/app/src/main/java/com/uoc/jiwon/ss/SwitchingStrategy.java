package com.uoc.jiwon.ss;

import android.content.Context;
import android.widget.ImageView;

public interface SwitchingStrategy {
    public void writeNewRelay(Context context);
    public  void setRelayInfo(Context context);
    public void setImage(ImageView button);
    public void setToggle(MicrodustActivity microdustActivity);
}