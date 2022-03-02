/**
 * Course.java
 *  Object used to store information about individual courses
 *  Will utilize CourseDao class in order to process the Course objects within the database
 *
 * @authors Team 32
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
    @ColumnInfo(name = "studentId")
    public int studentId;

    @ColumnInfo(name = "dept")
    public String dept;

    @ColumnInfo(name = "num")
    public String num;

    @ColumnInfo(name = "year")
    public String year;

    @ColumnInfo(name = "qtr")
    public String qtr;

    @ColumnInfo(name = "size")
    public String size;

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
     * @param size
     *
     * return Instantiated Course object
     */

    public Course(int id, int studentId, String dept, String num, String year, String qtr, String size) {
        this.id = id;
        this.studentId = studentId;
        this.dept = dept.toUpperCase();
        num = num.replaceAll("\\s", "");
        this.num = num.toUpperCase();
        this.year = year;
        this.qtr = qtr;
        this.size = size;

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

        switch(size) {
            case "TINY":
            case "Tiny (<40)":
                this.size = "TINY";
                break;
            case "SMALL":
            case "Small (40–75)":
                this.size = "SMALL";
                break;
            case "MEDIUM":
            case "Medium (75–150)":
                this.size = "MEDIUM";
                break;
            case "LARGE":
            case "Large (150–250)":
                this.size = "LARGE";
                break;
            case "HUGE":
            case "Huge (250–400)":
                this.size = "HUGE";
                break;
            case "GIGANTIC":
            case "Gigantic (400+)":
                this.size = "GIGANTIC";
                break;
        }
        this.courseFullString = this.year + "," + this.qtr + "," + this.dept + "," + this.num +
                "," + this.size;
    }

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
        return this.courseFullString.equals(courseObj.getCourseFullString());
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
     * Returns all fields in Course object concatenated into a more readable single string
     *
     * @return String courseFullStringReadable
     */

    public String getCourseFullStringReadable() {
        return this.dept + " " + this.num + " " + this.qtr + " " + this.year;
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
