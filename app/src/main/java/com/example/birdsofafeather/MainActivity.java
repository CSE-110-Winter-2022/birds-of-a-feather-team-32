package com.example.birdsofafeather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.birdsofafeather.model.db.AppDatabase;
import com.example.birdsofafeather.model.db.Student;
import com.example.birdsofafeather.model.db.StudentWithCourses;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onViewClassesClicked(View view) {
        Intent intent = new Intent(this, CourseActivity.class);
        startActivity(intent);
    }

    public void onFindStudentsClicked(View view) {
        Intent intent = new Intent(this, NearbyMessagesMockScreen.class);
        startActivity(intent);
    }
}