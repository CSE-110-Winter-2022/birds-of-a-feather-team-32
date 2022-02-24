/**
 * StudentWithCoursesDao File
 * Data Access Object for Students with Courses
 * Allows access and insertion of data into the database
 */
package com.example.birdsofafeather.model.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface StudentWithCoursesDao {

    @Transaction
    @Query("SELECT * FROM students order by numClassOverlap desc")
    List<StudentWithCourses> getAll();

    @Query("SELECT * FROM students WHERE id=:id")
    StudentWithCourses get(int id);

    @Query("SELECT COUNT(*) from students")
    int count();

    @Insert
    void insert(Student student);

}