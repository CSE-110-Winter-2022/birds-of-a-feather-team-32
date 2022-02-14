package com.example.birdsofafeather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.birdsofafeather.model.db.Course;
import com.example.birdsofafeather.model.db.AppDatabase;
import com.example.birdsofafeather.model.db.Student;
import com.example.birdsofafeather.model.db.StudentWithCourses;
import com.example.birdsofafeather.model.db.StudentWithCoursesDao;

import java.util.Arrays;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private AppDatabase db;
    private RecyclerView coursesRecyclerView;
    private RecyclerView.LayoutManager coursesLayoutManager;
    private ProfileActivityViewAdapter profileActivityViewAdapter;
    private ImageView imageView;
    private TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        db = AppDatabase.singleton(this);

        Intent intent = getIntent();
        int studentId = intent.getIntExtra("student_id",0);

        StudentWithCourses student = db.studentWithCoursesDao().get(studentId);
        List<Course> courses = student.getCourses();

        // set title
        //setTitle(studentName);

        // use some logic to filter out the classes that we don't want to see
        // right now, let's just try to display some data from the database

        // display information on the courseRecyclerView
        coursesRecyclerView = findViewById(R.id.courseRecyclerView);
        coursesLayoutManager = new LinearLayoutManager(this);
        coursesRecyclerView.setLayoutManager(coursesLayoutManager);

        profileActivityViewAdapter = new ProfileActivityViewAdapter(courses);
        coursesRecyclerView.setAdapter(profileActivityViewAdapter);

        textview = findViewById(R.id.nameTextView);
        textview.setText(student.getName());

        imageView = findViewById(R.id.profileImageView);
        String url = student.getPhotoURL();
        url = url.trim();
        Glide.with(this)
                .load(url)
                .override(128,128)
                .into(imageView);
    }

    public void onBackClicked(View view) {
        finish();
    }
}