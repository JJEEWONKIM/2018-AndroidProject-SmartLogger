package com.uoc.jiwon.ss;

import android.util.Log;

public final class RemainTime {

    public static final String getRemainTime(double curtemp, int hopeTemp) {
        Log.d("potato","curTemp : "+curtemp+", hopeTemp : "+hopeTemp);
        if (curtemp > hopeTemp)
            return getLowTemp(curtemp, hopeTemp);
        else
            return getHighTemp(curtemp, hopeTemp);
    }

    public static final String getLowTemp(double curtemp, int hopeTemp) {
        if (curtemp < 25.0) {
            return String.valueOf(((int) curtemp - hopeTemp) * 2 - 1);

        } else if (curtemp >= 25.0 && curtemp < 26.0) {
            switch (hopeTemp) {
                case 18:
                    return "22";
                case 19:
                    return "19";
                case 20:
                    return "16";
                case 21:
                    return "15";
                case 22:
                    return "13";
                case 23:
                    return "11";
                case 24:
                    return "9";
                case 25:
                    return "7";
                case 26:
                    return "5";
                case 27:
                    return "3";
                case 28:
                    return "3";
                default:
                    return "3";
            }

        } else if (curtemp >= 26.0 && curtemp < 27.0) {
            switch (hopeTemp) {
                case 18:
                    return "18";
                case 19:
                    return "16";
                case 20:
                    return "14";
                case 21:
                    return "11";
                case 22:
                    return "9";
                case 23:
                    return "7";
                case 24:
                    return "5";
                case 25:
                    return "3";
                case 26:
                    return "1";
                default:
                    return "1";
            }
        } else if (curtemp >= 27.0 && curtemp < 28.0) {
            switch (hopeTemp) {
                case 19:
                    return "16";
                case 20:
                    return "14";
                case 21:
                    return "11";
                case 22:
                    return "9";
                case 23:
                    return "7";
                case 24:
                    return "5";
                case 25:
                    return "3";
                case 26:
                    return "1";
                default:
                    return "1";
            }
        } else if (curtemp >= 28.0 && curtemp < 30.0) {
            switch (hopeTemp) {
                case 18:
                    return "20";
                case 19:
                    return "18";
                case 20:
                    return "16";
                case 21:
                    return "14";
                case 22:
                    return "12";
                case 23:
                    return "10";
                case 24:
                    return "8";
                case 25:
                    return "5";
                case 26:
                    return "4";
                case 27:
                    return "2";
                default:
                    return "1";
            }

        } else {
            switch (hopeTemp) {
                case 18:
                    return "22";
                case 19:
                    return "19";
                case 20:
                    return "16";
                case 21:
                    return "15";
                case 22:
                    return "13";
                case 23:
                    return "11";
                case 24:
                    return "9";
                case 25:
                    return "7";
                case 26:
                    return "5";
                case 27:
                    return "3";
                case 28:
                    return "3";
                default:
                    return "3";
            }
        }
    }

    public static final String getHighTemp(double curtemp, int hopeTemp) {
        if (curtemp < 19.0) {
            switch (hopeTemp) {
                case 18:
                    return "1";
                case 19:
                    return "1";
                case 20:
                    return "1";
                case 21:
                    return "1";
                case 22:
                    return "2";
                case 23:
                    return "2";
                case 24:
                    return "2";
                case 25:
                    return "3";
                case 26:
                    return "4";
                case 27:
                    return "4";
                case 28:
                    return "5";
                default:
                    return "5";
            }
        } else if (curtemp >= 19.0 && curtemp < 20.0) {
            switch (hopeTemp) {
                case 20:
                    return "1";
                case 21:
                    return "1";
                case 22:
                    return "1";
                case 23:
                    return "2";
                case 24:
                    return "2";
                case 25:
                    return "3";
                case 26:
                    return "3";
                case 27:
                    return "4";
                default:
                    return "4";
            }

        } else if (curtemp >= 20.0 && curtemp < 21.0) {
            switch (hopeTemp) {
                case 21:
                    return "1";
                case 22:
                    return "1";
                case 23:
                    return "1";
                case 24:
                    return "2";
                case 25:
                    return "2";
                case 26:
                    return "3";
                case 27:
                    return "3";
                default:
                    return "4";
            }

        } else {
            switch (hopeTemp - (int) curtemp) {
                case 1:
                    return "1";
                case 2:
                    return "1";
                case 3:
                    return "1";
                case 4:
                    return "2";
                case 5:
                    return "2";
                case 6:
                    return "3";
                case 7:
                    return "3";
                default:
                    return "3";

            }

        }
    }

    public static final String getVocRemainTime(double voc) {
        if (voc < 500.0)
            return "0";
        else if (voc >= 500.0 && voc < 800)
            return "1";
        else if (voc >= 800.0 && voc < 1100)
            return "2";
        else
            return "3";
    }

    public static final String setRecomBest(String remainTime, String machineName){
        if(remainTime.equals("0"))
            return "현재 상태 쾌적합니다!";
        else
            return "쾌적 수치까지 "+remainTime+"분 남았습니다. \n"+ machineName+ " 가동합니다.";
    }
}
