/**
 * CoursesActivity.java
 * This file allows the user to add their own courses to the application.
 * They can also see the courses that they have previously added.
 *
 * @authors Andrew Tang, Marc Mendoza
 */
package com.example.birdsofafeather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.birdsofafeather.model.db.AppDatabase;
import com.example.birdsofafeather.model.db.Course;

import java.util.List;

public class CourseActivity extends AppCompatActivity {

    private AppDatabase db;
    private RecyclerView coursesRecyclerView;
    private RecyclerView.LayoutManager coursesLayoutManager;
    private CoursesViewAdapter coursesViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        setTitle("Modify Courses");

        db = AppDatabase.singleton(this);
        List<Course> courses = db.coursesDao().getCoursesFromStudentId(0);
        coursesRecyclerView = findViewById(R.id.courses_view);

        coursesLayoutManager = new LinearLayoutManager(this);
        coursesRecyclerView.setLayoutManager(coursesLayoutManager);

        coursesViewAdapter = new CoursesViewAdapter(courses, (course) -> {
            db.coursesDao().delete(course);
        });
        coursesRecyclerView.setAdapter(coursesViewAdapter);
    }

    public void onAddClassClicked(View view) {
        int newCourseId = db.coursesDao().numCourses() + 1;
        TextView newCourseDeptTextView = findViewById(R.id.enter_class_dept);
        TextView newCourseNumTextView = findViewById(R.id.enter_class_number);
        TextView newCourseYearTextView = findViewById(R.id.enter_year);
        Spinner newCourseQtrSpinner = findViewById(R.id.pick_quarter);

        String newCourseDeptText = newCourseDeptTextView.getText().toString();
        String newCourseNumText = newCourseNumTextView.getText().toString();
        String newCourseYearText = newCourseYearTextView.getText().toString();
        String newCourseQtrText = newCourseQtrSpinner.getSelectedItem().toString();

        // Check if any fields are empty
        if (!newCourseDeptText.equals("") && !newCourseNumText.equals("") && !newCourseYearText.equals("")) {
            Course newCourse = new Course(newCourseId, 0, newCourseDeptText, newCourseNumText, newCourseYearText, newCourseQtrText);
            if(!db.coursesDao().getAll().contains(newCourse)){
                db.coursesDao().insert(newCourse);
                coursesViewAdapter.addCourse(newCourse);
            }
            else {
                ErrorUtilities.showAlert(this, "This class has already been added!");
            }
        } else {
            ErrorUtilities.showAlert(this, "One or more of the fields is empty!");
        }
    }

    public void onHomeClicked(View view) {
        Intent intent = new Intent(this, NearbyMessagesMockScreen.class);
        startActivity(intent);
    }
}