package com.example.birdsofafeather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
        List<Course> courses = db.coursesDao().getAll();

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

<<<<<<< HEAD
        Course newCourse = new Course(newCourseId, newCourseDeptText, newCourseNumText, newCourseYearText, newCourseQtrText);
        db.coursesDao().insert(newCourse);

        coursesViewAdapter.addCourse(newCourse);
=======


        if (!newCourseDeptText.equals("") && !newCourseNumText.equals("") && !newCourseYearText.equals("")) {
            Course newCourse = new Course(newCourseId, newCourseDeptText, newCourseNumText, newCourseYearText, newCourseQtrText);
            if(!db.coursesDao().getAll().contains(newCourse)){
                db.coursesDao().insert(newCourse);
                coursesViewAdapter.addCourse(newCourse);
            }

        } else {
            ErrorUtilities.showAlert(this, "One or more of the fields is empty!");
        }
>>>>>>> d96eccf4aa2ef1800ab15c730f640f0293863d84
    }

    public void onHomeClicked(View view) {
        finish();
    }
}