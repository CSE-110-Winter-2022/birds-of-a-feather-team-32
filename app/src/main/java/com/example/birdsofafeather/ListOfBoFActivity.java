/**
 * ListOfBofActivity.java
 * This class allows the user to see other students nearby them that share
 * the same classes as they do.
 * They are also able to click on an individual student to see all the
 * classes that they have in common.
 */
package com.example.birdsofafeather;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.birdsofafeather.model.db.CoursesDao;
import com.example.birdsofafeather.model.db.Student;
import com.example.birdsofafeather.model.db.StudentWithCourses;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ListOfBoFActivity extends AppCompatActivity {

    private AppDatabase db;

    protected RecyclerView studentRecyclerView;
    protected RecyclerView.LayoutManager studentLayoutManager;
    protected ListOfBoFViewAdapter studentViewAdapter;

    private MessageListener realListener;
    private FakedMessageListener testListener;

    private static final String TAG = "bofNearby";

    private int buttonState = 0;
    private HashSet<String> seenMessages;
    private HashSet<Course> ownCoursesSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boflist);
        Log.d("onCreate", "called onCreate");

        // Get list of messages from Nearby Messages Mock Screen
        Intent i = getIntent();
        ArrayList<String> messages = i.getStringArrayListExtra("messages");

        // Get current data stored in database
        db = AppDatabase.singleton(this);
        List<StudentWithCourses> students = db.studentWithCoursesDao().getAll();

        // Get user's own Courses
        List<Course> ownCourses = db.coursesDao().getCoursesFromStudentId(0);
        // reformat these courses into a hashset to make comparisons easier
        ownCoursesSet = new HashSet<>();
        ownCoursesSet.addAll(ownCourses);

        // Initialize a HashSet of messages that we've seen so far
        seenMessages = new HashSet<>();

        // Set up UI
        studentRecyclerView = findViewById(R.id.student_view);

        studentLayoutManager = new LinearLayoutManager(this);
        studentRecyclerView.setLayoutManager(studentLayoutManager);

        studentViewAdapter = new ListOfBoFViewAdapter(students);
        studentRecyclerView.setAdapter(studentViewAdapter);

        // Initialize our Message Listener
        realListener = new builtInMessageListener();

        // Use Fake Message Listener for the demo
        this.testListener = new FakedMessageListener(realListener, messages);

        // Restarts search for new bof if it was never turned off by user
        SharedPreferences preferences = getSharedPreferences("BOF", MODE_PRIVATE);
        boolean isBofSearchOn = preferences.getBoolean("bofSearchOn", false);
        if (isBofSearchOn) {
            buttonState = 0;
            findViewById(R.id.runButton).performClick();
            Log.d("Performed Click", "True");
        }
    }

    // Restarts search for new bof if it was never turned off by user
    @Override
    public void onStart() {
        super.onStart();
        Log.d("onStart", "onStart called");
        SharedPreferences preferences = getSharedPreferences("BOF", MODE_PRIVATE);
        boolean isBofSearchOn = preferences.getBoolean("bofSearchOn", false);
        Log.d("isBofSearchOn", "" + isBofSearchOn);
        if (isBofSearchOn) {
            buttonState = 0;
            findViewById(R.id.runButton).performClick();
            Log.d("Performed Click", "True");
        }
    }

    // NOTE: In order to display Bluetooth permissions dialog box, need to clear Google Play Services Data
    public void onStartClicked(View view) {
        Log.d("onStartClicked", "clicked onStart");

        Button startButton = findViewById(R.id.runButton);

        // Build user message to publish to other students
        Message myMessage = new Message(buildMessage().getBytes(StandardCharsets.UTF_8));

        SharedPreferences preferences = getSharedPreferences("BOF", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        // Search is currently off
        if (buttonState == 0) {
            buttonState = 1;
            startButton.setText("Stop");
            Nearby.getMessagesClient(this).subscribe(realListener);
            Nearby.getMessagesClient(this).publish(myMessage);
            editor.putBoolean("bofSearchOn", true);
            editor.apply();
            testListener.getMessage();
        } else { // Search is currently on
            buttonState = 0;
            startButton.setText("Start");
            Nearby.getMessagesClient(this).unsubscribe(realListener);
            Nearby.getMessagesClient(this).unpublish(myMessage);
            editor.putBoolean("bofSearchOn", false);
            editor.apply();
        }
    }

    public String buildMessage() {

        SharedPreferences preferences = getSharedPreferences("BOF", MODE_PRIVATE);
        String name = preferences.getString("name", "");
        String photoURL = preferences.getString("image_url", "");

        List<Course> ownCourses = db.coursesDao().getCoursesFromStudentId(0);

        // Convert student data into desired format
        String message = "";
        message += name + ",,,\n";
        message += photoURL + ",,,\n";

        for (Course c : ownCourses) {
            message += c.getCourseFullString() + "\n";
        }
        message.trim();

        Log.d("My message:", message);

        return message;
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("onStop", "onStop called");
        Nearby.getMessagesClient(this).unsubscribe(realListener);
    }

    // Our custom Message Listener
    public class builtInMessageListener extends MessageListener {

        @Override
        public void onFound(@NonNull Message message) {
            String rawString = new String(message.getContent());
            Log.d(TAG, "Found message: " + rawString);

            // Do not process repeated messages
            if(!seenMessages.contains(rawString)){
                // Add message to seen messages
                seenMessages.add(rawString);
                // Parse data from raw messages
                parseStudentMessage(rawString);
                // Refresh screen with new data after processing
                List<StudentWithCourses> students = db.studentWithCoursesDao().getAll();
                studentViewAdapter = new ListOfBoFViewAdapter(students);
                studentRecyclerView.setAdapter(studentViewAdapter);
            }
        }

        // Get student data from found message
        public void parseStudentMessage(String studentMessage){

            String studentName;
            String photoUrl;
            int numClassesOverlap = 0;
            CoursesDao courseDao = db.coursesDao();

            // Split message by the 3 commas
            String[] data = studentMessage.split(",,,");
            studentName = data[0];
            photoUrl = data[1];

            // Split message by new line
            String[] coursesString = data[2].split("\n");

            Course newCourse;
            // Ensure that a new student ID is used
            int studentId = db.studentWithCoursesDao().count() + 1;

            // Iterate through all course strings
            for(int i = 1; i < coursesString.length; i ++){
                String course = coursesString[i];
                String[] courseParts = course.split(",");

                // Ensue that a new course ID is used
                int currId = courseDao.numCourses()+1;

                String dept = courseParts[2];
                Log.d("Found new dept", dept);
                String num = courseParts[3];
                Log.d("Found new course num", num);
                String year = courseParts[0];
                Log.d("Found new year", year);
                String qtr = courseParts[1];
                Log.d("Found new qtr", qtr);

                // Create new course
                newCourse = new Course(currId, studentId, dept, num, year, qtr);

                // If new course matches with one of the user's courses, add it to the database
                if (ownCoursesSet.contains(newCourse)) {
                    db.coursesDao().insert(newCourse);
                    numClassesOverlap++;
                }
            }

            // If new student has 1 or more shared courses, add them to the student database
            if (numClassesOverlap > 0) {
                Student newStudent = new Student(studentId, studentName, photoUrl, numClassesOverlap);
                db.studentWithCoursesDao().insert(newStudent);
            }
        }

        @Override
        public void onLost(@NonNull Message message) {
            Log.d(TAG, "Lost sight of message: " + new String(message.getContent()));
        }
    }
}
