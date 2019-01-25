package com.uoc.jiwon.ss;

public class ResourceVoc extends Resources {
    @Override
    public Resources getInstance(){
        return new ResourceVoc();
    }
    @Override
    public int setImage(double vocValue){
        if(vocValue<=400.0)
            return R.drawable.love;
        else if(vocValue>=400.0 && vocValue <500.0)
            return R.drawable.normal;
        else
            return R.drawable.notgood;
    }


}
