package com.example.birdsofafeather;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TimeStamp {
    String getTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mma");
        String time = simpleDateFormat.format(new Date());
        Log.d("TimeStamp", "Current Timestamp: " + time);
        return time;
    }

    String getTimeAlt() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy-hh-mm-a");
        String time = simpleDateFormat.format(new Date());
        Log.d("AltTime", "Altered Timestamp: " + time);
        return time;
    }
}
