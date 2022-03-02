package com.example.birdsofafeather;

import android.content.Context;

import androidx.room.Room;

import com.example.birdsofafeather.model.db.AppDatabase;

public class DatabaseClient {
    private Context context;
    private AppDatabase appDatabase;
    private static String databaseName;
    private static DatabaseClient instance;

    private DatabaseClient(Context context, String databaseName) {
        this.context = context;

        // for creating a new database
        if(this.databaseName == null || !this.databaseName.equals(databaseName)){
            this.databaseName = databaseName;
        }

        appDatabase = Room.databaseBuilder(context, AppDatabase.class, databaseName).build();
    }

    public String getDatabaseName(){
        return this.databaseName;
    }

    public static synchronized DatabaseClient getInstance(Context context, String databaseName){
        if(instance == null || DatabaseClient.databaseName == null || !DatabaseClient.databaseName.equals(databaseName)){
            instance = new DatabaseClient(context, databaseName);
        }
        return instance;
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }
}
