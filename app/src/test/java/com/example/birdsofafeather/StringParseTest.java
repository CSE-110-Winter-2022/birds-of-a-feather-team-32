/**
 * StringParseTest file
 * In progress tester for the String Parser in ListOfBoFActivity
 * @author Allison Chan
 *
 * Having difficulties with testing string parser on its own because
 * of it's design begin embedded in too many places.
 * Could just convert this tester to test the MessageListener
 * and ListOfBoFViewAdapter and adding things to the database
 * in a more realistic context and have a different string parser
 * test if later refactored to be less dependent on other
 * components
 */
package com.example.birdsofafeather;

import static org.junit.Assert.assertEquals;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.birdsofafeather.model.db.AppDatabase;
import com.example.birdsofafeather.model.db.Course;
import com.example.birdsofafeather.model.db.CoursesDao;
import com.example.birdsofafeather.model.db.Student;
import com.example.birdsofafeather.model.db.StudentWithCourses;
import com.example.birdsofafeather.model.db.StudentWithCoursesDao;
import com.google.android.gms.nearby.messages.MessageListener;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

/**
 * StringParseTest Method
 * In progress tester that is currently testing the
 * NearbyMessagesMockScreen that takes in a message
 * and then passes it to ListOfBofActivity to parse
 * Still needs som work
 */
@RunWith(AndroidJUnit4.class)
public class StringParseTest {
    public static String exampleMessage = "Bill,,,\n" +
            "https://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-PzLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0,,,\n" +
            "2021,FA,CSE,210\n" +
            "2022,WI,CSE,110\n" +
            "2022,SP,CSE,110";
    AppDatabase db;
    CoursesDao coursesDao;
    StudentWithCoursesDao swcDao;

    @Rule
    public ActivityScenarioRule<NearbyMessagesMockScreen> scenarioRule =  new ActivityScenarioRule<>(NearbyMessagesMockScreen.class);

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

    /**
     * testSanityStringParse
     * Intended to test regular message with no weird inputs
     */
    @Test
    public void testSanityStringParse(){
        Student student1 = new Student(0,0,"Test Student 1", "TESTURL1", 0);
        Course course1 = new Course(0, 0, "CSE", "210", "2021", "FA");

        ActivityScenario scenario = scenarioRule.getScenario();
        scenario.onActivity(activity -> {
            TextView messageView = activity.findViewById(R.id.textBox);
            messageView.setText(exampleMessage);
            Button enterButton = activity.findViewById(R.id.enterDataButton);
            Button continueButton = activity.findViewById(R.id.continueButton);

            enterButton.performClick();
            continueButton.performClick();

            assertEquals(messageView.getText().toString(),"");
            // assertEquals(swcDao.count(), 1);
            // check that student was correctly inserted into the database
            List<StudentWithCourses> students = swcDao.getAll();

            // this is giving me an error, am I misunderstanding how new IDs
            // assigned?
            // actually I think it's a problem with not having
            // the test student and test course inserted so nothing
            // is actually being inserted into the database
            // StudentWithCourses testStudent = swcDao.get(swcDao.count() - 1);
            // assertEquals(testStudent.getName(), "Bill");

        });
    }
}
