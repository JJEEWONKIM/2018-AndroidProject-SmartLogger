package com.uoc.jiwon.ss;

public class LatestData {
    public String temp ="";
    public String co2="";
    public String voc="";
    public String microdust="";
    public String humid="";


    public LatestData() {

    }

    public LatestData(String temp, String co2,
                      String voc, String microdust,
                      String humid) {
        this.temp = temp;
        this.co2 = co2;
        this.voc = voc;
        this.microdust = microdust;
        this.humid = humid;

    }

}
