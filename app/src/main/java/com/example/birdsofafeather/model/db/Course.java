package com.example.birdsofafeather.model.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Locale;

@Entity(tableName = "courses")
public class Course {
    @PrimaryKey
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "dept")
    public String dept;

    @ColumnInfo(name = "num")
    public String num;

    @ColumnInfo(name = "year")
    public String year;

    @ColumnInfo(name = "qtr")
    public String qtr;

    public Course(int id, String dept, String num, String year, String qtr) {
        this.id = id;
        this.dept = dept.toUpperCase();
        num = num.replaceAll("\\s", "");
        this.num = num.toUpperCase();
        this.year = year;
        switch(qtr){
            case "FA":
            case "Fall":
                this.qtr = "FA";
                break;
            case "WI":
            case "Winter":
                this.qtr = "WI";
                break;
            case "SP":
            case "Spring":
                this.qtr = "SP";
                break;
            case "SS1":
            case "Summer 1":
                this.qtr = "SS1";
                break;
            case "SS2":
            case "Summer 2":
                this.qtr = "SS2";
                break;
            case "SSS":
            case "Special Summer Session":
                this.qtr = "SSS";
                break;
        }
    }

    @Override
    public boolean equals(Object other){
        if(this == other) { return true;}
        if(other == null || getClass() != other.getClass()) { return false;}
        Course course = (Course) other;
        if((this.dept.equals(course.dept)) && (this.num.equals(course.num)) && (this.qtr.equals(course.qtr)) && (this.year.equals(course.year))){
            return true;
        }
        return false;
    }
}
