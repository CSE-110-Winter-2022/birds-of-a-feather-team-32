package com.example.birdsofafeather.model.db;

import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "sessions")
public class Session {
    @PrimaryKey
    @ColumnInfo(name = "sessionId")
    public int sessionId;

    @ColumnInfo(name = "name")
    public String name;

    public Session(int sessionId, String name){
        this.sessionId = sessionId;
        this.name = name;
        Log.d("in session constructor", "name is: " + this.name);
    }

    public int getSessionId(){return this.sessionId;}
    public String getName(){return this.name;}
}
