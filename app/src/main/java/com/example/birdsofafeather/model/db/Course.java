package com.example.birdsofafeather.model.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "courses")
public class Course {

    // this is how we identify a certain course
    @PrimaryKey
    @ColumnInfo(name = "id")
    public int id;

    // this is how we identify that this course belongs to
    // a particular person
    @ColumnInfo(name = "student_id")
    public int studentId;

    @ColumnInfo(name = "dept")
    public String dept;

    @ColumnInfo(name = "num")
    public String num;

    @ColumnInfo(name = "year")
    public String year;

    @ColumnInfo(name = "qtr")
    public String qtr;

    @ColumnInfo(name = "courseFullString")
    public String courseFullString;

    public Course(int id, String dept, String num, String year, String qtr) {
        this.id = id;
        this.dept = dept.toUpperCase();
        this.num = num;
        this.year = year;
        this.qtr = qtr;
        courseFullString = dept + " " + num + " " + qtr + " " + year;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Course courseObj = (Course) obj;
        if (this.courseFullString.equals(courseObj.getCourseFullString())) {
            return true;
        }
        return false;
    }

    public String getCourseFullString() {
        return courseFullString;
    }

    @Override
    public int hashCode() {
        return courseFullString.hashCode();
    }

}
