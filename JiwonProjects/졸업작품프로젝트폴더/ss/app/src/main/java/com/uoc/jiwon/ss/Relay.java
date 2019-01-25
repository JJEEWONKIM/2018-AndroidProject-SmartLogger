package com.uoc.jiwon.ss;

public class Relay {

    public String userPower;
    public String power;



    public Relay() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Relay(String userPower, String power ) {
       this.userPower = userPower;
       this.power = power;
    }
}
