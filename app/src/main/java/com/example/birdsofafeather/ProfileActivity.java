package com.example.birdsofafeather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.birdsofafeather.model.db.Course;
import com.example.birdsofafeather.model.db.AppDatabase;

import java.util.Arrays;

public class ProfileActivity extends AppCompatActivity {

    //private AppDatabase db; // note that this is the class that we made
                            // composition!
    private RecyclerView coursesRecyclerView;
    private RecyclerView.LayoutManager coursesLayoutManager;
    private ProfileActivityViewAdapter profileActivityViewAdapter;

    protected Course[] data = {
            new Course(0, "CSE","21", "2020", "Fall" ),
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        // Get extra information from intents
        //Intent intent = getIntent();
        //String studentName = intent.getStringExtra("student_name");


        // get information from database
        // db = AppDatabase.singleton(this);


        // set title
        //setTitle(studentName);

        // use some logic to filter out the classes that we don't want to see
        // right now, let's just try to display some data from the database

        // display information on the courseRecyclerView
        coursesRecyclerView = findViewById(R.id.courseRecyclerView);
        coursesLayoutManager = new LinearLayoutManager(this);
        coursesRecyclerView.setLayoutManager(coursesLayoutManager);

        profileActivityViewAdapter = new ProfileActivityViewAdapter(Arrays.asList(data));
        coursesRecyclerView.setAdapter(profileActivityViewAdapter);


    }

    public void onBackClicked(View view) {
        finish();
    }
}