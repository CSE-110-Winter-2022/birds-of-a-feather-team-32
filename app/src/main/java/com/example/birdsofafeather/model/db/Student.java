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

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "photoURL")
    public String photoURL;

    //@ColumnInfo
    //public List<Course> courses;

    // placeholder constructor to use for testing purposes
    @Ignore
    public Student(String name) {
        this.name = name;
        this.photoURL = null;
        //this.courses = null;
    }

    public Student(String name, String photoURL) {
        this.name = name;
        this.photoURL = photoURL;
        //this.courses = courses;
    }

    public String getName() {
        return this.name;
    }
    public String getPhotoURL(){ return this.photoURL;}
    //public HashSet<Course> getCourses() {return this.courses;}

}
