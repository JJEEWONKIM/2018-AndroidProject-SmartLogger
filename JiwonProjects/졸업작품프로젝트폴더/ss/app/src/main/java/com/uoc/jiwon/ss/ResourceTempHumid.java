package com.uoc.jiwon.ss;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ResourceTempHumid extends Resources {
    private DatabaseReference databaseReference;

    @Override
    public Resources getInstance(){
        return new ResourceTempHumid();
    }
    @Override
    public int setImage(double value){
        return 0;
    }

}

