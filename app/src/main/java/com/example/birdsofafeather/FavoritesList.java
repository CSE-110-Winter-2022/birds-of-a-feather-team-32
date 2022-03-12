package com.example.birdsofafeather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.birdsofafeather.model.db.AppDatabase;
import com.example.birdsofafeather.model.db.Course;
import com.example.birdsofafeather.model.db.Student;
import com.example.birdsofafeather.model.db.StudentWithCourses;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Activity to display the list of favorite students, across all saved sessions
 */
public class FavoritesList extends AppCompatActivity {

    private AppDatabase db;
    private RecyclerView studentRecyclerView;
    private RecyclerView.LayoutManager studentLayoutManager;
    private ListOfBoFViewAdapter studentViewAdapter;
    private List<StudentWithCourses> students = new ArrayList<>();

    /**
     * Initializes the UI as well as the databased
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites_list);
        db = AppDatabase.singleton(this);
        students = db.studentWithCoursesDao().getFavorites();

        // Set up UI
        studentRecyclerView = findViewById(R.id.favorite_view);
        studentLayoutManager = new LinearLayoutManager(this);
        studentRecyclerView.setLayoutManager(studentLayoutManager);

        studentViewAdapter = new ListOfBoFViewAdapter(students);
        studentRecyclerView.setAdapter(studentViewAdapter);

    }

    /**
     * Returns to the previous activity screen
     */
    public void onBackButtonClicked(View view) {
        finish();
    }
}