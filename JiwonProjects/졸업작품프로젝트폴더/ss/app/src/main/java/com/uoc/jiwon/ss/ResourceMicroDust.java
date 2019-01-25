package com.uoc.jiwon.ss;

public class ResourceMicroDust extends Resources {
    @Override
    public Resources getInstance(){
        return new ResourceMicroDust();
    }
    public int setImage(double microdustValue){
        if(microdustValue<=30.0)
            return R.drawable.love;
        else if(microdustValue>=31.0 && microdustValue<80.0)
            return R.drawable.normal;
        else if(microdustValue>=81.0 && microdustValue <150.0)
            return R.drawable.notgood;
        else
            return R.drawable.danger;
    }
}
