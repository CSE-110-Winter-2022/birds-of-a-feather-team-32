package com.example.birdsofafeather.model.db;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class StudentWithCourses {
    @Embedded
    public Student student;

    @Relation(parentColumn = "sessionId",
            entityColumn = "studentId",
            entity = Course.class)
    public List<Course> courses;

    public String getName() {
        return this.student.name;
    }
    public String getPhotoURL() {
        return this.student.photoURL;
    }
    public List<Course> getCourses() {
        return this.courses;
    }
    public int getId() {
        return this.student.studentId;
    }
}
