package com.example.birdsofafeather.model.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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
<<<<<<< HEAD
        this.dept = dept;
=======
        this.dept = dept.toUpperCase();
>>>>>>> d96eccf4aa2ef1800ab15c730f640f0293863d84
        this.num = num;
        this.year = year;
        this.qtr = qtr;
    }
<<<<<<< HEAD
=======

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
>>>>>>> d96eccf4aa2ef1800ab15c730f640f0293863d84
}
