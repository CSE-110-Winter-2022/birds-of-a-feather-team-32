/**
 * File: AppDatabase.java
 * Description: This database stores information about students and their courses.
 *
 * @author Team 32
 */
package com.example.birdsofafeather.model.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Session.class, Student.class, Course.class}, version = 6)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase singletonInstance;

    /**
     * Constructs a singleton if it doesn't exist yet.
     * @param context Current context
     * @return Instance of the database
     */
    public static AppDatabase singleton(Context context) {
        if (singletonInstance == null) {
            singletonInstance = Room.databaseBuilder(context, AppDatabase.class, "students.db")
                    .allowMainThreadQueries()
                    .build();
        }

        return singletonInstance;
    }

    /**
     * Constructs an empty singleton for testing purposes.
     * @param context Current context
     */
    public static void useTestSingleton(Context context) {
        singletonInstance = Room.inMemoryDatabaseBuilder(context, AppDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    public abstract SessionWithStudentsDao sessionsWithStudentsDao();
    public abstract CoursesDao coursesDao(); // Stores course info
    public abstract StudentWithCoursesDao studentWithCoursesDao(); // Stores student info
}
