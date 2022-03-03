package com.example.birdsofafeather.model.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface SessionWithStudentsDao {
    @Transaction
    @Query("SELECT * FROM students")
    List<SessionWithStudents> getAll();

    @Query("SELECT COUNT(*) from sessions")
    int count();

    @Insert
    void insert(Session session);
}
