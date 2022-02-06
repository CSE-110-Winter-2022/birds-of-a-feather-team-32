package com.example.birdsofafeather.model.db;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.HashSet;

public class StudentWithCourses {
    @Embedded
    public Student student;

    @Relation(parentColumn = "id",
            entityColumn = "student_id",
            entity = Course.class,
            projection = {"text"})
    //public HashSet<Course> courses;


    public String getName() {
        return this.student.name;
    }
    public String getPhotoURL(){ return this.student.photoURL;}
    //public HashSet<Course> getCourses() {return this.courses;}
}
