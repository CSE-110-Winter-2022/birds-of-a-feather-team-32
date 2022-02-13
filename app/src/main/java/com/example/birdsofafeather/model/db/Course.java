/**
 * Course.java
 *  Object used to store information about individual courses
 *  Will utilize CourseDao class in order to process the Course objects within the database
 *
 * @authors Marc Mendoza, Andrew Tang
 */
package com.example.birdsofafeather.model.db;

import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Locale;

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

    /**
     * Parameterized Contructor: Instantiates object with passed through
     * values for the fields in object
     *
     * @param id
     * @param studentId
     * @param  dept
     * @param year
     * @param  qtr
     *
     * return Instantiated Course object
     */

    public Course(int id, int studentId, String dept, String num, String year, String qtr) {
        this.id = id;
        this.studentId = studentId;
        this.dept = dept.toUpperCase();
        num = num.replaceAll("\\s", "");
        this.num = num.toUpperCase();
        this.year = year;
        this.qtr = qtr;

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
        this.courseFullString = this.dept + " " + this.num + " " + this.qtr + " " + this.year;
        Log.d("qtr:", this.qtr);

    }
/*
    @Override
    public boolean equals(Object other){
        if(this == other) { return true;}
        if(other == null || getClass() != other.getClass()) { return false;}
        Course course = (Course) other;
        if((this.dept.equals(course.dept)) && (this.num.equals(course.num))
            && (this.qtr.equals(course.qtr)) && (this.year.equals(course.year))){
            return true;
        }
        return false;
    }
    */

    /**
     * Overriden equals() function to compare each Course Object
     *
     * @param obj: object being compared
     * @return boolean: true if equivalent, false otherwise
     */

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

    /**
     * Returns all fields in Course object concatenated into a single string
     *
     * @return String courseFullString
     */

    public String getCourseFullString() {
        return courseFullString;
    }

    /**
     * Creates unique hash code for the concatenated string of fields
     * of the Course Object
     *
     * @return int courseFullString's hashCode
     */

    @Override
    public int hashCode() {
        return courseFullString.hashCode();
    }

}
