package com.example.birdsofafeather.model.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.HashSet;
import java.util.List;

@Entity(tableName = "students")
public class Student {

    @PrimaryKey
    @ColumnInfo(name = "id")
    public int studentId;

    @ColumnInfo(name = "numClassOverlap")
    public int numClassOverlap;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "photoURL")
    public String photoURL;

    // placeholder constructor to use for testing purposes
    @Ignore
    public Student(String name) {
        this.name = name;
        this.photoURL = null;
    }

    public Student(int studentId, String name, String photoURL, int numClassOverlap) {
        this.studentId = studentId;
        this.name = name;
        this.photoURL = photoURL;
        this.numClassOverlap = numClassOverlap;
    }

    public String getName() {
        return this.name;
    }
    public String getPhotoURL(){ return this.photoURL; }
    public int getStudentId() { return studentId; }

}
