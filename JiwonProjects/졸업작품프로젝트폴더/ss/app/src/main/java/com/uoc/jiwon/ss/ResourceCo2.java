package com.uoc.jiwon.ss;
import com.google.firebase.database.ValueEventListener;

public class ResourceCo2 extends Resources {
    @Override
    public Resources getInstance(){
        return new ResourceCo2();
    }
    public int setImage(double co2Value){
        if(co2Value<=450.0)
            return R.drawable.love;
        else if(co2Value>=450.0 && co2Value<700.0)
            return R.drawable.good;
        else if(co2Value>=700.0 && co2Value <1000.0)
            return R.drawable.normal;
        else if(co2Value>=1000.0 && co2Value <2000.0)
            return R.drawable.sleepy;
        else if(co2Value>=2000.0 && co2Value <3000.0)
            return R.drawable.danger;
        else
            return R.drawable.danger;
    }

}
