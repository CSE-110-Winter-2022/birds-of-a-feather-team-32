package com.example.birdsofafeather.model.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.HashSet;
import java.util.List;

@Dao
public interface CoursesDao {
    @Transaction
    @Query("SELECT * FROM courses")
    List<Course> getAll();

    @Query("SELECT * FROM courses WHERE id=:id")
    Course get(int id);

    @Query("SELECT COUNT(*) from courses")
    int numCourses();

    @Insert
    void insert(Course course);

    @Delete
    void delete(Course course);

}
