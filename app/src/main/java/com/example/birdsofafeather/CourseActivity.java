/**
 * File: CoursesActivity.java
 * Description: This activity allows the user to add their own courses to the application.
 * They can also see the courses that they have previously added.
 *
 * @authors Team 32
 */
package com.example.birdsofafeather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

    /**
     * Initializes the database, RecyclerView, LayoutManager, and CoursesViewAdapter when the
     * activity is created.
     * @param savedInstanceState Most recent activity data
     */
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

        //if classes already inputted, skip to next activity
        SharedPreferences preferences = getSharedPreferences("BOF", MODE_PRIVATE);
        String coursesDone = preferences.getString("done", "don't skip");
        if(coursesDone != "don't skip"){
            Intent intent = new Intent(this, NearbyMessagesMockScreen.class);
            startActivity(intent);
        }
    }

    /**
     * Gets strings from TextViews and checks for empty/duplicate entries before inserting into
     * database.
     * @param view View that was clicked
     */
    public void onAddClassClicked(View view) {
        int newCourseId = db.coursesDao().numCourses() + 1;
        TextView newCourseDeptTextView = findViewById(R.id.enter_class_dept);
        TextView newCourseNumTextView = findViewById(R.id.enter_class_number);
        TextView newCourseYearTextView = findViewById(R.id.enter_year);
        Spinner newCourseQtrSpinner = findViewById(R.id.pick_quarter);
        Spinner newCourseSizeSpinner = findViewById(R.id.pick_size);

        String newCourseDeptText = newCourseDeptTextView.getText().toString();
        String newCourseNumText = newCourseNumTextView.getText().toString();
        String newCourseYearText = newCourseYearTextView.getText().toString();
        String newCourseQtrText = newCourseQtrSpinner.getSelectedItem().toString();
        String newCourseSizeText = newCourseSizeSpinner.getSelectedItem().toString();

        // Check if any fields are empty
        if (!newCourseDeptText.equals("") && !newCourseNumText.equals("") &&
                !newCourseYearText.equals("")) {
            Course newCourse = new Course(newCourseId, 0, newCourseDeptText,
                    newCourseNumText, newCourseYearText, newCourseQtrText, newCourseSizeText);

            // Check duplicate courses
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

    /**
     * Checks if there is at least one course added and moves to the next activity if so.
     * @param view View that was clicked
     */
    public void onHomeClicked(View view) {
        if (db.coursesDao().numCourses() < 1) {
            ErrorUtilities.showAlert(this, "Please enter at least one course " +
                    "before proceeding!");
            return;
        }
        SharedPreferences preferences = getSharedPreferences("BOF", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("done", "skip");
        editor.apply();
        Intent intent = new Intent(this, NearbyMessagesMockScreen.class);
        startActivity(intent);
    }
}