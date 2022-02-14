/**
 * File: CourseTest.java
 * Description: Tests for CourseActivity class
 */
package com.example.birdsofafeather;

import static org.junit.Assert.assertEquals;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.birdsofafeather.model.db.AppDatabase;
import com.example.birdsofafeather.model.db.Course;
import com.example.birdsofafeather.model.db.CoursesDao;
import com.example.birdsofafeather.model.db.StudentWithCoursesDao;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.shadows.ShadowDialog;

@RunWith(AndroidJUnit4.class)
public class CourseTest {
    AppDatabase db;
    CoursesDao coursesDao;

    @Rule
    public ActivityScenarioRule<CourseActivity> rule = new ActivityScenarioRule<>(CourseActivity.class);

    @Before
    public void initialize() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        AppDatabase.useTestSingleton(appContext);
        db = AppDatabase.singleton(appContext);
        coursesDao =  db.coursesDao();
    }

    @After
    public void end() {
        db.close();
    }

    /**
     * Test for an error if user tries to move to next activity without adding a course.
     */
    @Test
    public void test_no_courses_added() {
        rule.getScenario().onActivity(activity -> {
            activity.findViewById(R.id.home_button).performClick();
            AlertDialog dialog = (AlertDialog) ShadowDialog.getLatestDialog();
            // check only one error pop up
            assertEquals(1, ShadowDialog.getShownDialogs().size());
            // check if AlertDialog object dialog is latest alert
            assertEquals(dialog, ShadowDialog.getShownDialogs().get(0));
        });
    }

    /**
     * Test for an error if user leaves a field empty and tries to add the course.
     */
    @Test
    public void test_empty_course_added() {
        rule.getScenario().onActivity(activity -> {
            ((TextView) activity.findViewById(R.id.enter_class_dept)).setText("CSE");
            ((TextView) activity.findViewById(R.id.enter_class_number)).setText("110");
            activity.findViewById(R.id.addclass_button).performClick();
            AlertDialog dialog = (AlertDialog) ShadowDialog.getLatestDialog();
            // check only one error pop up
            assertEquals(1, ShadowDialog.getShownDialogs().size());
            // check if AlertDialog object dialog is latest alert
            assertEquals(dialog, ShadowDialog.getShownDialogs().get(0));
        });
    }

    /**
     * Test for an error if user tries to enter a duplicate course.
     */
    @Test
    public void test_duplicate_course_added() {
        rule.getScenario().onActivity(activity -> {
            ((TextView) activity.findViewById(R.id.enter_class_dept)).setText("CSE");
            ((TextView) activity.findViewById(R.id.enter_class_number)).setText("110");
            ((TextView) activity.findViewById(R.id.enter_year)).setText("2021");
            activity.findViewById(R.id.addclass_button).performClick();
            activity.findViewById(R.id.addclass_button).performClick();
            AlertDialog dialog = (AlertDialog) ShadowDialog.getLatestDialog();
            // check only one error pop up
            assertEquals(1, ShadowDialog.getShownDialogs().size());
            // check if AlertDialog object dialog is latest alert
            assertEquals(dialog, ShadowDialog.getShownDialogs().get(0));
        });
    }
}
