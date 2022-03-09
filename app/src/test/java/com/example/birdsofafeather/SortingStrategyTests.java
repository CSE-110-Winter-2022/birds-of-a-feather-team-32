package com.example.birdsofafeather;

import android.content.Context;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.birdsofafeather.model.db.AppDatabase;
import com.example.birdsofafeather.model.db.Course;
import com.example.birdsofafeather.model.db.CoursesDao;
import com.example.birdsofafeather.model.db.Student;
import com.example.birdsofafeather.model.db.StudentWithCourses;
import com.example.birdsofafeather.model.db.StudentWithCoursesDao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class SortingStrategyTests {

    AppDatabase db;
    CoursesDao coursesDao;
    StudentWithCoursesDao swcDao;
    sortStudentsByStrategySorter sorter;

    @Before
    public void initialize() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        AppDatabase.useTestSingleton(appContext);
        db = AppDatabase.singleton(appContext);
        coursesDao =  db.coursesDao();
        swcDao = db.studentWithCoursesDao();

        Student student1 = new Student(0,0,"Test Student 1", "TESTURL1", 0);
        Course course1 = new Course(0, 0, "CSE", "110", "2022", "Winter", "Small (40–75)");

        Student student2 = new Student(1,0,"Test Student 2", "TESTURL2", 1);
        Course course2 = new Course(1, 1, "CSE", "110", "2022", "WI", "Small");
        Course course3 = new Course(2, 1, "CSE", "110", "2022", "FA", "Huge (250–400)");

        swcDao.insert(student1);
        swcDao.insert(student2);
        coursesDao.insert(course1);
        coursesDao.insert(course2);
        coursesDao.insert(course3);
    }

    @Test
    public void testSmallClassesStrategy() {
        sorter = new sortStudentsByStrategySorter(swcDao.getFromSession(0));

        sorter.setStrategy(new SmallClassSizeScoreStrategy());
        List<StudentWithCourses> result = sorter.sort();

        assertEquals(result.get(0).getName(), "Test Student 2");
    }

    @After
    public void end() {
        db.close();
    }
}
