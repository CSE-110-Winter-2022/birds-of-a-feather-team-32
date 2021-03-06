package com.example.birdsofafeather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.birdsofafeather.model.db.Course;
import com.example.birdsofafeather.model.db.AppDatabase;
import com.example.birdsofafeather.model.db.Student;
import com.example.birdsofafeather.model.db.StudentWithCourses;
import com.example.birdsofafeather.model.db.StudentWithCoursesDao;

import java.util.Arrays;
import java.util.List;

/**
 * Displays more detailed information about a specific student
 */
public class ProfileActivity extends AppCompatActivity {

    private AppDatabase db;
    private RecyclerView coursesRecyclerView;
    private RecyclerView.LayoutManager coursesLayoutManager;
    private ProfileActivityViewAdapter profileActivityViewAdapter;
    private ImageView imageView;
    private TextView textview;
    private ImageButton waveButton;
    private StudentWithCourses student;
    private int studentId;

    /**
     * Initializes the UI, fetches the data of the student to be displayed
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        db = AppDatabase.singleton(this);

        Intent intent = getIntent();
        studentId = intent.getIntExtra("student_id",0);

        student = db.studentWithCoursesDao().get(studentId);
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

        waveButton = findViewById(R.id.waveButton);

        // Button should already be filled in if student has been waved at previously in this session
        if (student.student.getWavedAt()) {
            waveButton.setImageResource(R.mipmap.wave_filled);
        }

        imageView = findViewById(R.id.profileImageView);
        String url = student.getPhotoURL();
        url = url.trim();
        Glide.with(this)
                .load(url)
                .override(128,128)
                .into(imageView);
    }

    /**
     * Defines the behavior when the wave button is clicked by the user
     */
    public void onWaveClicked(View view) {
        // Only clickable if student has not been waved at yet
        if (!student.student.getWavedAt()) {
            waveButton.setImageResource(R.mipmap.wave_filled);
            Toast.makeText(this, "Wave sent!", Toast.LENGTH_LONG).show();

            db.studentWithCoursesDao().update(true, studentId);
        }
    }

    /**
     * Returns to the previous activity screen
     */
    public void onBackClicked(View view) {
        finish();
    }
}