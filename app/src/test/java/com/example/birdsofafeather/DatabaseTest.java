package com.example.birdsofafeather;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import android.content.Context;

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
public class DatabaseTest {

    AppDatabase db;
    CoursesDao coursesDao;
    StudentWithCoursesDao swcDao;

    @Before
    public void initialize() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        AppDatabase.useTestSingleton(appContext);
        db = AppDatabase.singleton(appContext);
        coursesDao =  db.coursesDao();
        swcDao = db.studentWithCoursesDao();
    }

    @After
    public void end() {
        db.close();
    }

    @Test
    public void testPersonEntity() {

        Student student1 = new Student(0,0,"Test Student 1", "TESTURL1", 0);
        Student student2 = new Student(1,0,"Test Student 2", "TESTURL2", 1);

        assertEquals("Test Student 1", student1.getName());
        assertEquals("Test Student 2", student2.getName());

        assertEquals("TESTURL1", student1.getPhotoURL());
        assertEquals("TESTURL2", student2.getPhotoURL());

        assertEquals(0, student1.getStudentId());
        assertEquals(1, student2.getStudentId());

        assertEquals("0", student1.getNumOverlap());
        assertEquals("1", student2.getNumOverlap());
    }

    @Test
    public void testCourseEntity() {

        Course course1 = new Course(0, 0, "CSE", "110", "2022", "Winter");
        Course course2 = new Course(1, 1, "CSE", "110", "2022", "WI");
        Course course3 = new Course(2, 1, "CSE", "110", "2022", "FA");

        assertEquals(course1, course2);
        assertNotEquals(course1, course3);

        assertEquals("2022,WI,CSE,110", course1.getCourseFullString());
        assertEquals("2022,WI,CSE,110", course2.getCourseFullString());
        assertEquals("2022,FA,CSE,110", course3.getCourseFullString());
    }

    @Test
    public void testInsertAndDeleteCourse() {

        assertEquals(0, coursesDao.numCourses());

        Course course1 = new Course(0, 0, "CSE", "110", "2022", "Winter");
        coursesDao.insert(course1);
        assertEquals(1, coursesDao.numCourses());

        Course course2 = new Course(1, 1, "CSE", "110", "2022", "WI");
        coursesDao.insert(course2);
        assertEquals(2, coursesDao.numCourses());

        coursesDao.delete(course1);
        assertEquals(1, coursesDao.numCourses());

        coursesDao.delete(course2);
        assertEquals(0, coursesDao.numCourses());
    }

    @Test
    public void getCoursesWithCorrectStudentId() {

        int courseId = 0;
        ArrayList<Course> correctCourses1 = new ArrayList<>();
        ArrayList<Course> correctCourses2 = new ArrayList<>();
        ArrayList<Course> allCourses = new ArrayList<>();

        assertEquals(correctCourses1, coursesDao.getCoursesFromStudentId(0));
        assertEquals(correctCourses2, coursesDao.getCoursesFromStudentId(1));
        assertEquals(allCourses, coursesDao.getAll());

        for (int i = 0; i < 8; i++) {
            Course newCourse = new Course(courseId, 0, "CSE", "110", "2022", "Winter");
            correctCourses1.add(newCourse);
            allCourses.add(newCourse);
            coursesDao.insert(newCourse);
            courseId++;
        }
        assertEquals(correctCourses1, coursesDao.getCoursesFromStudentId(0));
        assertEquals(correctCourses2, coursesDao.getCoursesFromStudentId(1));
        assertEquals(allCourses, coursesDao.getAll());

        for (int i = 0; i < 8; i++) {
            Course newCourse = new Course(courseId, 1, "CSE", "110", "2022", "Winter");
            correctCourses2.add(newCourse);
            allCourses.add(newCourse);
            coursesDao.insert(newCourse);
            courseId++;
        }
        assertEquals(correctCourses1, coursesDao.getCoursesFromStudentId(0));
        assertEquals(correctCourses2, coursesDao.getCoursesFromStudentId(1));
        assertEquals(allCourses, coursesDao.getAll());
    }

    @Test
    public void getCorrectCourseWithId() {

        Course course1 = new Course(0, 0, "CSE", "110", "2022", "Winter");
        Course course2 = new Course(1, 1, "CSE", "110", "2022", "WI");
        Course course3 = new Course(2, 1, "CSE", "110", "2022", "FA");

        coursesDao.insert(course1);
        coursesDao.insert(course2);
        coursesDao.insert(course3);

        assertEquals(course1, coursesDao.get(0));
        assertEquals(course2, coursesDao.get(1));
        assertEquals(course3, coursesDao.get(2));
    }

    @Test
    public void testAddStudentWithCourses() {

        assertEquals(0, swcDao.count());

        Student student1 = new Student(0,0,"Test Student 1", "TESTURL1", 0);
        swcDao.insert(student1);
        assertEquals(1, swcDao.count());

        Student student2 = new Student(1, 0,"Test Student 2", "TESTURL2", 1);
        swcDao.insert(student2);
        assertEquals(2, swcDao.count());
    }

    @Test
    public void testGetAllStudentWithCourses() {

        Student student1 = new Student(0,0,"Test Student 1", "TESTURL1", 0);
        Student student2 = new Student(1, 0,"Test Student 2", "TESTURL2", 3);
        Student student3 = new Student(2, 0,"Test Student 3", "TESTURL2", 2);

        swcDao.insert(student1);
        swcDao.insert(student2);
        swcDao.insert(student3);

        List<StudentWithCourses> swcList = swcDao.getAll();

        assertEquals("Test Student 2", swcList.get(0).getName());
        assertEquals("Test Student 3", swcList.get(1).getName());
        assertEquals("Test Student 1", swcList.get(2).getName());
    }

    @Test
    public void testGetStudentWithCourses() {

        Student student1 = new Student(0,0,"Test Student 1", "TESTURL1", 0);
        Student student2 = new Student(1, 0, "Test Student 2", "TESTURL2", 3);
        Student student3 = new Student(2, 0,"Test Student 3", "TESTURL2", 2);

        swcDao.insert(student1);
        swcDao.insert(student2);
        swcDao.insert(student3);

        assertEquals("Test Student 1", swcDao.get(0).getName());
        assertEquals("Test Student 2", swcDao.get(1).getName());
        assertEquals("Test Student 3", swcDao.get(2).getName());

        List<Course> correctCourses1 = new ArrayList<>();
        List<Course> correctCourses2 = new ArrayList<>();
        List<Course> correctCourses3 = new ArrayList<>();

        int courseId = 0;
        for (int i = 0; i < 8; i++) {
            Course newCourse = new Course(courseId, 0, "CSE", "110", "2022", "Winter");
            correctCourses1.add(newCourse);
            coursesDao.insert(newCourse);
            courseId++;
        }
        assertEquals(correctCourses1, swcDao.get(0).getCourses());

        for (int i = 0; i < 8; i++) {
            Course newCourse = new Course(courseId, 1, "CSE", "110", "2022", "Winter");
            correctCourses2.add(newCourse);
            coursesDao.insert(newCourse);
            courseId++;
        }
        assertEquals(correctCourses1, swcDao.get(0).getCourses());
        assertEquals(correctCourses2, swcDao.get(1).getCourses());

        for (int i = 0; i < 8; i++) {
            Course newCourse = new Course(courseId, 2, "CSE", "110", "2022", "Winter");
            correctCourses3.add(newCourse);
            coursesDao.insert(newCourse);
            courseId++;
        }
        assertEquals(correctCourses1, swcDao.get(0).getCourses());
        assertEquals(correctCourses2, swcDao.get(1).getCourses());
        assertEquals(correctCourses3, swcDao.get(2).getCourses());
    }
}