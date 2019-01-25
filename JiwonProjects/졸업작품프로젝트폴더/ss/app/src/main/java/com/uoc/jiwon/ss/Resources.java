package com.uoc.jiwon.ss;



import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import java.text.SimpleDateFormat;

import static android.content.Context.MODE_PRIVATE;

public abstract class Resources {

    private DatabaseReference databaseReference;

    public Resources() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child(FirebaseConfig.routeRoot).child(FirebaseConfig.route1).child(FirebaseConfig.route2);
    }

    public void writeNewRelay(String relay,String userPower) {
        databaseReference = FirebaseDatabase.getInstance().getReference("DEVICEID");
        databaseReference.child("2FSQEO45").child("JHJ").child(relay).child("userPower").setValue(userPower);
    }
    public void setHopeTemp(int hopeTemp){
        databaseReference = FirebaseDatabase.getInstance().getReference("DEVICEID");
        databaseReference.child("2FSQEO45").child("JHJ").child("DL").child("desired").child(getCurrentDate()).child("desiredTemp").setValue(hopeTemp);
    }

    public String getCurrentDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar c1 = Calendar.getInstance();
        return sdf.format(c1.getTime());

    }

    public abstract int setImage(double value);
    public abstract Resources getInstance();

}

