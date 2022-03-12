/**
 * StudentWithCoursesDao File
 * Data Access Object for Students with Courses
 * Allows access and insertion of data into the database
 */
package com.example.birdsofafeather.model.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface StudentWithCoursesDao {

    @Transaction
    // Change to order by wave status first
    @Query("SELECT * FROM students order by numClassOverlap desc")
    List<StudentWithCourses> getAll();

    @Query("SELECT * FROM students WHERE studentId=:id")
    StudentWithCourses get(int id);

    @Query("SELECT * FROM students WHERE favorite=1")
    List<StudentWithCourses> getFavorites();

    @Query("SELECT * FROM students WHERE sessionId=:sessionId order by numClassOverlap desc")
    List<StudentWithCourses> getFromSession(int sessionId);

    @Query("SELECT COUNT(*) from students")
    int count();

    @Query("UPDATE students SET wavedAt=:wavedAt WHERE studentId=:id")
    void update(boolean wavedAt, int id);

    @Query("UPDATE students SET favorite=:favorite WHERE studentId=:id")
    void updateFavorite(boolean favorite, int id);

    @Insert
    void insert(Student student);

}