package com.example.birdsofafeather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.birdsofafeather.model.db.AppDatabase;
import com.example.birdsofafeather.model.db.Course;

import java.util.ArrayList;
import java.util.List;

public class SavedSessionsActivity extends AppCompatActivity{

    private AppDatabase db;
    private RecyclerView savedSessionsRecyclerView;
    private RecyclerView.LayoutManager savedSessionsLayoutManager;
    private SavedSessionsViewAdapter savedSessionsViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_sessions);

        setTitle("Saved Sessions");

        db = AppDatabase.singleton(this);
        // List<Course> courses = db.coursesDao().getCoursesFromStudentId(0);
        savedSessionsRecyclerView = findViewById(R.id.saved_sessions_view);

        savedSessionsLayoutManager = new LinearLayoutManager(this);
        savedSessionsRecyclerView.setLayoutManager(savedSessionsLayoutManager);

        List<String> testSessions = new ArrayList<>();
        testSessions.add("1/1/1011");
        testSessions.add("2/2/2022");
        testSessions.add("3/3/3033");

        savedSessionsViewAdapter = new SavedSessionsViewAdapter(testSessions);
        savedSessionsRecyclerView.setAdapter(savedSessionsViewAdapter);
    }
}