package com.example.birdsofafeather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.birdsofafeather.model.db.AppDatabase;
import com.example.birdsofafeather.model.db.Course;
import com.example.birdsofafeather.model.db.Session;
import com.example.birdsofafeather.model.db.SessionWithStudents;

import java.util.ArrayList;
import java.util.List;

/**
 * This activity displays all the past saved sessions of the user
 */
public class SavedSessionsActivity extends AppCompatActivity{

    private AppDatabase db;
    private RecyclerView savedSessionsRecyclerView;
    private RecyclerView.LayoutManager savedSessionsLayoutManager;
    private SavedSessionsViewAdapter savedSessionsViewAdapter;

    /**
     * Initializes the UI, fetches the list of past saved sessions
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_sessions);

        setTitle("Saved Sessions");

        db = AppDatabase.singleton(this);
        List<SessionWithStudents> sessions = db.sessionsWithStudentsDao().getAll();
        Log.d("in savesessionsactivity", "name is: " + sessions.get(0).getSessionName());
        savedSessionsRecyclerView = findViewById(R.id.saved_sessions_view);

        savedSessionsLayoutManager = new LinearLayoutManager(this);
        savedSessionsRecyclerView.setLayoutManager(savedSessionsLayoutManager);

        savedSessionsViewAdapter = new SavedSessionsViewAdapter(sessions);
        savedSessionsRecyclerView.setAdapter(savedSessionsViewAdapter);
    }
}