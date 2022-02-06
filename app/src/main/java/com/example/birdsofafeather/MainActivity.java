package com.example.birdsofafeather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onViewClassesClicked(View view) {
        Intent intent = new Intent(this, CourseActivity.class);
        startActivity(intent);
<<<<<<< HEAD
=======


>>>>>>> d96eccf4aa2ef1800ab15c730f640f0293863d84
    }
}