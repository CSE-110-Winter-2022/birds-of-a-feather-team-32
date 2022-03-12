package com.example.birdsofafeather.model.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface SessionWithStudentsDao {
    @Transaction
    @Query("SELECT * FROM sessions WHERE sessionId!=-1")
    List<SessionWithStudents> getAll();

    @Query("SELECT * FROM sessions WHERE sessionId=:id")
    SessionWithStudents get(int id);

    @Query("SELECT COUNT(*) from sessions")
    int count();

    @Query("UPDATE sessions SET name=:name WHERE sessionId=:id")
    void update(String name, int id);

    @Insert
    void insert(Session session);
}
