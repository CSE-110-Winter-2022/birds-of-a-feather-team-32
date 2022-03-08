package com.example.birdsofafeather.model.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.HashMap;
import java.util.HashSet;

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
    }

    public int getSessionId(){return this.sessionId;}
    public String getName(){return this.name;}
}
