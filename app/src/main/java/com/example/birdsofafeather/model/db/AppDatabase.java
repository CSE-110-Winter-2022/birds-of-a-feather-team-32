package com.example.birdsofafeather.model.db;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.birdsofafeather.ProfileActivity;
import com.example.birdsofafeather.model.Course;
import com.example.birdsofafeather.model.Student;

@Database(entities = {Student.class, Course.class}, version=1)
public abstract class AppDatabase extends RoomDatabase {

    //public static AppDatabase singleton(ProfileActivity profileActivity) {
    //}
}
