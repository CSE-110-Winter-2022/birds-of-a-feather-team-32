package com.example.birdsofafeather;

import android.content.Context;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import static org.junit.Assert.assertEquals;
import com.example.birdsofafeather.model.db.AppDatabase;
import com.example.birdsofafeather.model.db.Course;
import com.example.birdsofafeather.model.db.CoursesDao;
import com.example.birdsofafeather.model.db.Student;
import com.example.birdsofafeather.model.db.StudentWithCourses;
import com.example.birdsofafeather.model.db.StudentWithCoursesDao;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class WaveTest {
    AppDatabase db;
    StudentWithCoursesDao swcDao;

    @Rule
    public ActivityScenarioRule<ListOfBoFActivity> rule = new ActivityScenarioRule<>(ListOfBoFActivity.class);

    @Before
    public void initialize() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        AppDatabase.useTestSingleton(appContext);
        db = AppDatabase.singleton(appContext);
        swcDao =  db.studentWithCoursesDao();
    }

    @After
    public void end() {
        db.close();
    }

    @Test
    public void testWavedAtTop() {
        Student student1 = new Student(0,0,"Test Student 1", "TESTURL1", 1, "test", true, false);
        Student student2 = new Student(1,0,"Test Student 2", "TESTURL2", 5132, "test", false, false);
        swcDao.insert(student1);
        swcDao.insert(student2);
        List<StudentWithCourses> students = swcDao.getFromSession(0);
        assertEquals("Test Student 1",students.get(0).getName());
    }
}
