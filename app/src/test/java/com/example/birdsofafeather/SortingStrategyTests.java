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

        Student student1 = new Student(0,0,"Test Student 1", "TESTURL1", 1);
        Course course1 = new Course(0, 0, "CSE", "110", "2022", "WI", "Small (40–75)");

        Student student2 = new Student(1,0,"Test Student 2", "TESTURL2", 3);
        Course course2 = new Course(1, 1, "CSE", "110", "2021", "WI", "Small");
        Course course3 = new Course(2, 1, "CSE", "110", "2021", "WI", "Huge (250–400)");
        Course course6 = new Course(5, 1, "CSE", "110", "2021", "FA", "Gigantic");

        Student student3 = new Student(2,0,"Test Student 3", "TESTURL2", 2);
        Course course4 = new Course(3, 2, "CSE", "110", "2020", "WI", "Small");
        Course course5 = new Course(4, 2, "CSE", "110", "2020", "FA", "Small");

        swcDao.insert(student1);
        swcDao.insert(student2);
        swcDao.insert(student3);
        coursesDao.insert(course1);
        coursesDao.insert(course2);
        coursesDao.insert(course3);
        coursesDao.insert(course4);
        coursesDao.insert(course5);
        coursesDao.insert(course6);

        Student student4 = new Student(3,1,"Test Student 4", "TESTURL1", 1);
        Course course7 = new Course(6, 3, "CSE", "110", "2021", "FA", "Small (40–75)");

        Student student5 = new Student(4,1,"Test Student 5", "TESTURL2", 3);
        Course course8 = new Course(7, 4, "CSE", "110", "2021", "SU", "Small");

        Student student6 = new Student(5,1,"Test Student 6", "TESTURL2", 2);
        Course course9 = new Course(8, 5, "CSE", "110", "2021", "SP", "Small");

        Student student7 = new Student(6,1,"Test Student 7", "TESTURL2", 2);
        Course course10 = new Course(9, 6, "CSE", "110", "2021", "WI", "Small");

        swcDao.insert(student4);
        swcDao.insert(student5);
        swcDao.insert(student6);
        swcDao.insert(student7);
        coursesDao.insert(course7);
        coursesDao.insert(course8);
        coursesDao.insert(course9);
        coursesDao.insert(course10);
    }

    @Test
    public void testSmallClassesStrategy() {
        sorter = new sortStudentsByStrategySorter(swcDao.getFromSession(0));

        sorter.setStrategy(new SmallClassSizeScoreStrategy());
        List<StudentWithCourses> result = sorter.sort();

        assertEquals(result.get(0).getName(), "Test Student 3");
        assertEquals(result.get(1).getName(), "Test Student 2");
        assertEquals(result.get(2).getName(), "Test Student 1");
    }

    @Test
    public void testRecentClassesStrategy() {
        sorter = new sortStudentsByStrategySorter(swcDao.getFromSession(1));

        sorter.setStrategy(new SortByRecentScoreStrategy());
        List<StudentWithCourses> result = sorter.sort();

        assertEquals(result.get(0).getName(), "Test Student 4");
        assertEquals(result.get(1).getName(), "Test Student 5");
        assertEquals(result.get(2).getName(), "Test Student 6");
        assertEquals(result.get(3).getName(), "Test Student 7");
    }

    @After
    public void end() {
        db.close();
    }
}
