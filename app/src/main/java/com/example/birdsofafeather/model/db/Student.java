package com.example.birdsofafeather.model.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "students")
public class Student {

    @PrimaryKey
    @ColumnInfo(name = "studentId")
    public int studentId;

    @ColumnInfo(name = "sessionId")
    public int sessionId;

    @ColumnInfo(name = "numClassOverlap")
    public int numClassOverlap;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "photoURL")
    public String photoURL;

    @ColumnInfo(name = "uuid")
    public String uuid;

    @ColumnInfo(name = "wavedFrom")
    public boolean wavedFrom;

    @ColumnInfo(name = "wavedAt")
    public boolean wavedAt;

    public Student(int studentId, int sessionId, String name, String photoURL, int numClassOverlap, String uuid, boolean wavedFrom, boolean wavedAt) {
        this.studentId = studentId;
        this.sessionId = sessionId;
        this.name = name;
        this.photoURL = photoURL;
        this.numClassOverlap = numClassOverlap;
        this.uuid = uuid;
        this.wavedFrom = wavedFrom;
        this.wavedAt = wavedAt;
    }

    public String getName() {
        return this.name;
    }
    public int getSession(){return this.sessionId;}
    public String getPhotoURL() {
        return this.photoURL;
    }
    public int getStudentId() {
        return studentId;
    }
    public String getNumOverlap() {
        return String.valueOf(this.numClassOverlap);
    }
    public String getUUID() { return uuid; }
    public boolean getWavedFrom() { return wavedFrom; }
    public boolean getWavedAt() { return wavedAt; }
}
