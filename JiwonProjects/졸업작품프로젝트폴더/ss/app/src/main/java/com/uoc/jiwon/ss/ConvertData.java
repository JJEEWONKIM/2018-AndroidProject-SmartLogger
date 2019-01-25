package com.uoc.jiwon.ss;
enum ConvertData {
    UNIQUE;
    public Double stringToDouble(String stringData){
        return Double.parseDouble(stringData);
    }
    public int stringToInt(String stringData){
        return Integer.parseInt(stringData);
    }
    public String doubleToString(Double doubleData){
        return String.valueOf(Double.parseDouble(String.format("%.2f",doubleData)));
    }
    public String intToString(int intData){
        return Integer.toString(intData);
    }

    public Double getCleanValue(String airName, double Celsius, double RH) {
        double a = (0.81 * Celsius);
        double b = 0.01 * RH;
        double c = 0.99 * Celsius - 14.3;
        double d = a + b * c + 46.3;
        double e = 100 - d;
        double f = 20.0 / 7.0;
        double doubleResult = e * f;
        if (doubleResult> 100.0) {doubleResult = 100.0; }
        if( doubleResult < 0.0){ doubleResult = 0.0; }
        return doubleResult;
    }

    public Double getCleanValue(double hopeTemp, double tempValue, double humidValue) {
        double W = 0.1;
        if(hopeTemp == 10) {
            W = 5.0/3.0;
        }
        else if(hopeTemp == 11) {
            W = 50.0/33.0;
        }
        else if(hopeTemp == 12) {
            W = 25.0/18.0;
        }
        else if(hopeTemp == 13) {
            W = 50.0/39.0;
        }
        else if(hopeTemp == 14) {
            W = 25.0/21.0;
        }
        else if(hopeTemp == 15) {
            W = 10.0/9.0;
        }
        else if(hopeTemp == 16) {
            W = 25.0/24.0;
        }
        else if(hopeTemp == 17) {
            W = 50.0/51.0;
        }
        else if(hopeTemp == 18) {
            W = 25.0/27.0;
        }
        else if(hopeTemp == 19) {
            W = 50.0/57.0;
        }
        else if(hopeTemp == 20) {
            W = 5.0/6.0;
        }
        else if(hopeTemp == 21) {
            W = 50.0/63.0;
        }
        else if(hopeTemp == 22) {
            W = 25.0/33.0;
        }
        else if(hopeTemp == 23) {
            W = 50/0/69.0;
        }
        else if(hopeTemp == 24) {
            W = 25.0/36.0;
        }
        else if(hopeTemp == 25) {
            W = 2.0/3.0;
        }
        else if(hopeTemp == 26) {
            W = 25.0/39.0;
        }
        else if(hopeTemp == 27) {
            W = 50.0/81.0;
        }
        else if(hopeTemp == 28) {
            W = 25.0/42.0;
        }
        else if(hopeTemp == 29) {
            W = 50.0/87.0;
        }
        else if(hopeTemp == 30) {
            W = 5.0/9.0;
        }
        else if(hopeTemp == 31) {
            W = 50.0/93.0;
        }
        else if(hopeTemp == 32) {
            W = 25.0/48.0;
        }
        double resultHope = (hopeTemp - (Math.abs(tempValue - hopeTemp)) / W) / hopeTemp * 80.0;
        double humidHope = (60.0 - (humidValue - 60.0) / (10.0/9.0)) / 60.0 * 20.0;
        return (resultHope + humidHope> 100)? 100 : resultHope+humidHope;
    }

    public double getCleanValue(String airName, double value){
        double result=0;
        switch(airName) {
            case "CO2":
                result = (450.0 - (value - 450.0) / (110.0/27.0)) / 450.0 * 100.0;
                if(result > 100.0) { result = 100.0; }
                break;

            case "MICRODUST":
                double s = 12.0/7.0;
                double a = value / s;
                double b = 70 - a;
                result = b / 70.0 * 100.0;
                break;
            case "VOC":
                result = (500.0 - (value- 500.0) / (101.0/150.0)) / 500.0 * 100.0;
                if(result > 100.0) { result = 100.0; }
                break;
            default:
                return 0.0;
        }
        if(result < 0.0){ result = 0.0; }

        return result;
    }


}
