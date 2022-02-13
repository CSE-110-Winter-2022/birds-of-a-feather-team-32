package com.example.birdsofafeather;

import static org.junit.Assert.assertEquals;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.birdsofafeather.model.db.AppDatabase;
import com.example.birdsofafeather.model.db.Course;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class UnitTests {
    @Rule
    public ActivityScenarioRule<CourseActivity> scenarioRule = new ActivityScenarioRule<>(CourseActivity.class);

    @Test
    public void test_database_format() {
        ActivityScenario<CourseActivity> scenario = scenarioRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            AppDatabase db = AppDatabase.singleton(activity);

            EditText deptView = activity.findViewById(R.id.enter_class_dept);
            EditText numView = activity.findViewById(R.id.enter_class_number);
            EditText yearView = activity.findViewById(R.id.enter_year);
            Spinner qtrView = activity.findViewById(R.id.pick_quarter);
            Button addButton = activity.findViewById(R.id.addclass_button);

            deptView.setText("cse"); // text should be uppercase in the database
            numView.setText("1  10"); // whitespaces should be removed in the database
            yearView.setText("2022");
            qtrView.setSelection(1);
            addButton.performClick();

            Course course = db.coursesDao().get(1);
            assertEquals("CSE", course.dept);
            assertEquals("110", course.num);

            if (db.isOpen()) {
                db.close();
            }
        });
    }
}
