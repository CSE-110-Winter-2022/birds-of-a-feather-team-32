package com.example.birdsofafeather;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.example.birdsofafeather.model.db.AppDatabase;
import com.example.birdsofafeather.model.db.CoursesDao;
import com.example.birdsofafeather.model.db.Student;
import com.example.birdsofafeather.model.db.StudentWithCoursesDao;

@RunWith(AndroidJUnit4.class)
public class InstrumentedBoFDatabaseTests {

    @Test
    public void addPerson() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        AppDatabase.useTestSingleton(appContext);
        AppDatabase db = AppDatabase.singleton(appContext);
        Student student1 = new Student(0,"Test Student 1", "TESTURL1", 0);
        Student student2 = new Student(1, "Test Student 2", "TESTURL2", 1);

        StudentWithCoursesDao studentWithCoursesDao;
        studentWithCoursesDao = db.studentWithCoursesDao();

        studentWithCoursesDao.insert(student1);
        studentWithCoursesDao.insert(student2);

        assertEquals(2, studentWithCoursesDao.count());

        assertEquals(student1.getStudentId(), 0);
        assertEquals(student2.getStudentId(), 1);

        assertEquals(student1.getName(), "Test Student 1");
        assertEquals(student2.getName(), "Test Student 2");

        assertEquals(student1.getPhotoURL(), "TESTURL1");
        assertEquals(student2.getPhotoURL(), "TESTURL2");
    }
}
