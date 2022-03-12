package com.example.birdsofafeather;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeStamp {

    private final Calendar calendar = Calendar.getInstance();
    public int month = calendar.get(Calendar.MONTH);    //current month
    public String currYear = Integer.toString(calendar.get(Calendar.YEAR));      //current year

    /**
     * getTime method
     * @return String returns timestamp for display
     */
    public String getTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mma");
        String time = simpleDateFormat.format(new Date());
        Log.d("TimeStamp", "Current Timestamp: " + time);
        return time;
    }

    /**
     * getTimeAlt method
     * @return String timestamp that is a format that we can use
     * as an identifier for the database (the key)
     */
    public String getTimeAlt() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy-hh-mm-a");
        String time = simpleDateFormat.format(new Date());
        Log.d("AltTime", "Altered Timestamp: " + time);
        return time;
    }

    public String getYear() {
        return currYear;
    }

    public String getQuarter() {
        switch (month) {
            case 0:
            case 1:
            case 2: return "WI";
            case 3:
            case 4:
            case 5: return "SP";
            case 6:
            case 7: return "SU";
            case 8:
            case 9:
            case 10:
            case 11: return "FA";
        }
        return "ERROR";
    }

}
