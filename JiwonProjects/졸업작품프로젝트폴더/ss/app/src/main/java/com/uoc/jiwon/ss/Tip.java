package com.uoc.jiwon.ss;

public class Tip {
    public String tempTip ="";
    public String co2Tip="";
    public String vocTip="";
    public String microdustTip="";
    public String humidTip="";


    public Tip() {

    }

    public Tip(String tempTip, String co2Tip,
                      String vocTip, String microdustTip,
                      String humidTip) {
        this.tempTip = tempTip;
        this.co2Tip = co2Tip;
        this.vocTip = vocTip;
        this.microdustTip = microdustTip;
        this.humidTip = humidTip;

    }
}
