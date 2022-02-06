/**
 * ListOfBofActivity.java
 * This class allows the user to see other students nearby them that share
 * the same classes as they do.
 * They are also able to click on an individual student to see all the
 * classes that they have in common.
 */
package com.example.birdsofafeather;

import android.os.Bundle;
import android.view.View;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.birdsofafeather.model.db.AppDatabase;
import com.example.birdsofafeather.model.db.Course;
import com.example.birdsofafeather.model.db.Student;
import com.example.birdsofafeather.model.db.StudentWithCourses;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class ListOfBoFActivity extends AppCompatActivity {

    private AppDatabase db;

    protected RecyclerView studentRecyclerView;
    protected RecyclerView.LayoutManager studentLayoutManager;
    protected ListOfBoFViewAdapter studentViewAdapter;

    protected Student[] data = {
            new Student("Marc"),
            new Student("Allison"),
            new Student("Matthew"),
            new Student("Hassan"),
            new Student("Jennifer"),
            new Student("Andrew"),
            new Student("Bob"),
            new Student("Joe"),
            new Student("Student A"),
            new Student("Student B"),
            new Student("Student C"),
            new Student("Student D"),
            new Student("Student E")
    };

    protected String exampleMessage = "Bill,,,\n" +
            "https://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-PzLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0,,,\n" +
            "2021,FA,CSE,210\n" +
            "2022,WI,CSE,110\n" +
            "2022,SP,CSE,110";

    private static final String TAG = "bofNearby";
    private MessageListener realListener;
    private FakedMessageListener testListener;
    private int buttonState = 0;

    private HashSet<String> seenMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boflist);

        // for testing purposes
        // createStudents();

        db = AppDatabase.singleton(this);
        List<StudentWithCourses> students = db.studentWithCoursesDao().getAll();

        List<Course> ownCourses = db.coursesDao().getCoursesFromStudentId(0);

        // reformat into a hashset to make it easier to compare later on
        HashSet<Course> ownCoursesSet = new HashSet<>();
        for(Course course: ownCourses){
            ownCoursesSet.add(course);
        }

        // Initialize a HashSet of messages that we've seen so far
        seenMessages = new HashSet<String>();

        studentRecyclerView = findViewById(R.id.student_view);

        studentLayoutManager = new LinearLayoutManager(this);
        studentRecyclerView.setLayoutManager(studentLayoutManager);

        studentViewAdapter = new ListOfBoFViewAdapter(students);
        studentRecyclerView.setAdapter(studentViewAdapter);

        realListener = new MessageListener() {
            @Override
            public void onFound(@NonNull Message message) {
                String rawString = new String(message.getContent());
                String[] data;
                Log.d(TAG, "Found message: " + rawString);


                data = rawString.split("\n");
                Log.d(TAG, data[0]);
                // TODO:
                /*
                Check the message string against our HashSet, and if found, ignore the message
                Parse message, compare, insert into database
                 */
                if(!seenMessages.contains(rawString)){
                    parseStudentMessage(rawString);
                }
            }

            public void parseStudentMessage(String studentMessage){
                String studentName;
                String photoUrl;
                ArrayList<Course> courses = new ArrayList();

                String[] data = studentMessage.split(",,,");

                studentName = data[0];
                photoUrl = data[1];

                String[] coursesString = data[2].split("\n");

                Course newCourse;
                int currId = 0;

                for(int i = 1; i < coursesString.length; i ++){
                    String course = coursesString[i];
                    String[] courseParts = course.split(",");

                    int id = currId;
                    int studentId = 1;
                    String dept = courseParts[2];
                    System.out.println("dept: " + dept);
                    String num = courseParts[3];
                    System.out.println("num: " + num);

                    String year = courseParts[0];
                    System.out.println("year: " + year);

                    String qtr = courseParts[1];
                    System.out.println("qtr: " + qtr);

                    newCourse = new Course(currId, studentId, dept, num, year, qtr);
                    courses.add(newCourse);

                    currId ++;
                }

                int studentId = db.studentWithCoursesDao().count() + 1;
                Student newStudent = new Student(studentId, studentName, photoUrl, 0);
                checkForMatchingCourses(newStudent, courses);
            }

            public void checkForMatchingCourses(Student newStudent, ArrayList<Course> newCourses){
                int studentId = newStudent.getStudentId();
                for(Course course: newCourses){
                    // compare them with courses to see if there's a match
                    if(ownCoursesSet.contains(course)){

                        // not sure what the behavior is when you pass in a studentId
                        // that doesn't exist? so check here if you're getting any errors
                        if(db.studentWithCoursesDao().get(studentId) != null) {
                            db.studentWithCoursesDao().insert(newStudent);
                        }

                        db.studentWithCoursesDao().get(studentId).student.incrementNumClassOverlap();
                        db.coursesDao().insert(course);
                    }
                }
            }

            @Override
            public void onLost(@NonNull Message message) {
                Log.d(TAG, "Lost sight of message: " + new String(message.getContent()));
            }
        };
        this.testListener = new FakedMessageListener(realListener, 3, exampleMessage);
    }

    public void onStartClicked(View view) {
        Button startButton = findViewById(R.id.runButton);
        if (buttonState == 0) {
            buttonState = 1;
            startButton.setText("Stop");
            Nearby.getMessagesClient(this).subscribe(realListener);
            testListener.getMessage();
        } else {
            buttonState = 0;
            startButton.setText("Start");
            Nearby.getMessagesClient(this).unsubscribe(realListener);
        }
    }

    public void createStudents() {

        db = AppDatabase.singleton(this);

        int newCourseId = db.coursesDao().numCourses() + 1;
        Course testCourse = new Course(newCourseId,5,"POLI", "132", "2021", "FALL");
        //Student testStudent = new Student(5,"student5", "testPhotoURL", 1);

        //db.studentWithCoursesDao().insert(testStudent);
        db.coursesDao().insert(testCourse);

        // List<StudentWithCourses> students = db.studentWithCoursesDao().getAll();
        // insert new people into the thing
        //Course testCourse = new Course(0, "testDept", "testNum", "testYear", "testQtr");
        //HashSet<Course> testCourses = new HashSet<>();
        //testCourses.add(testCourse);

        // db.StudentWithCoursesDao().insert(testStudent);
        // studentViewAdapter.addStudent(testStudent);
        // populate the thing with new stuff

        // studentViewAdapter = new ListOfBoFViewAdapter(Arrays.asList(data));
        //studentRecyclerView.setAdapter(studentViewAdapter);
    }
  
    @Override
    public void onStop() {
        super.onStop();
        Nearby.getMessagesClient(this).unsubscribe(realListener);
    }
}
