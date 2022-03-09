/**
 * ListOfBofActivity.java
 * This class allows the user to see other students nearby them that share
 * the same classes as they do.
 * They are also able to click on an individual student to see all the
 * classes that they have in common.
 * @quthor Allison Chan, Matthew Peng
 */
package com.example.birdsofafeather;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.birdsofafeather.model.db.AppDatabase;
import com.example.birdsofafeather.model.db.Course;
import com.example.birdsofafeather.model.db.CoursesDao;
import com.example.birdsofafeather.model.db.Session;
import com.example.birdsofafeather.model.db.Student;
import com.example.birdsofafeather.model.db.StudentWithCourses;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;

import java.io.File;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ListOfBoFActivity extends AppCompatActivity {

    private AppDatabase db;

    private RecyclerView studentRecyclerView;
    private RecyclerView.LayoutManager studentLayoutManager;
    private ListOfBoFViewAdapter studentViewAdapter;
    private Spinner sortStrategySpinner;

    private MessageListener realListener;
    private FakedMessageListener testListener;

    private static final String TAG = "bofNearby";

    private int buttonState = 0;
    private HashSet<String> seenMessages;
    private HashSet<Course> ownCoursesSet;
    private List<StudentWithCourses> students = new ArrayList<>();
    private int currentSessionId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boflist);
        Log.d("onCreate", "called onCreate");

        // Get user's own Courses
        db = AppDatabase.singleton(this);
        List<Course> ownCourses = db.coursesDao().getCoursesFromStudentId(0);
        // reformat these courses into a hashset to make comparisons easier
        ownCoursesSet = new HashSet<>();
        ownCoursesSet.addAll(ownCourses);

        // Set up UI
        studentRecyclerView = findViewById(R.id.student_view);
        studentLayoutManager = new LinearLayoutManager(this);
        studentRecyclerView.setLayoutManager(studentLayoutManager);

        studentViewAdapter = new ListOfBoFViewAdapter(students);
        studentRecyclerView.setAdapter(studentViewAdapter);

        sortStrategySpinner = (Spinner) findViewById(R.id.sortStrategySpinner);

        // Restarts search for new bof if it was never turned off by user
        SharedPreferences preferences = getSharedPreferences("BOF", MODE_PRIVATE);
        boolean isBofSearchOn = preferences.getBoolean("bofSearchOn", false);
        /*
        /*
        if (isBofSearchOn) {
            buttonState = 0;
            findViewById(R.id.runButton).performClick();
            Log.d("Performed Click", "True");
        }
        */

        sortStrategySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                String selectedStrategy = (String) adapterView.getItemAtPosition(pos);
                sortStudentsByStrategySorter sorter = new sortStudentsByStrategySorter(students);
                switch(selectedStrategy) {
                    case "Default":
                        if (currentSessionId != -1) {
                            setSession(currentSessionId);
                        }
                        break;
                    case "Small Classes":
                        sorter.setStrategy(new SmallClassSizeScoreStrategy());
                        students = sorter.sort();
                        studentViewAdapter = new ListOfBoFViewAdapter(students);
                        studentRecyclerView.setAdapter(studentViewAdapter);
                        break;
                    case "Recent Classes":
                        // sorter.setStrategy(new RecentClassesScoreStrategy());
                        // students = sorter.sort();
                        studentViewAdapter = new ListOfBoFViewAdapter(students);
                        studentRecyclerView.setAdapter(studentViewAdapter);
                        break;
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
    }

    // Restarts search for new bof if it was never turned off by user
    @Override
    public void onStart() {
        super.onStart();
        Log.d("onStart", "onStart called");
        SharedPreferences preferences = getSharedPreferences("BOF", MODE_PRIVATE);
        boolean isBofSearchOn = preferences.getBoolean("bofSearchOn", false);
        Log.d("isBofSearchOn", "" + isBofSearchOn);

        // Get list of messages from Nearby Messages Mock Screen
        Intent i = getIntent();
        ArrayList<String> messages = i.getStringArrayListExtra("messages");

        // Initialize our Session
        int lastSessionID = preferences.getInt("lastSessionID", -1);
        if (lastSessionID != -1) {
            currentSessionId = lastSessionID;
            this.setTitle(db.sessionsWithStudentsDao().get(lastSessionID).getSessionName());
            setSession(lastSessionID);
        }

        // Initialize our Message Listener
        realListener = new builtInMessageListener();

        // Use Fake Message Listener for the demo
        if (messages != null) {
            this.testListener = new FakedMessageListener(realListener, messages);
        } else {
            this.testListener = new FakedMessageListener(realListener, new ArrayList<>());
        }

        /*
        if (isBofSearchOn) {
            buttonState = 0;
            findViewById(R.id.runButton).performClick();
            Log.d("Performed Click", "True");
        }
         */
    }

    // NOTE: In order to display Bluetooth permissions dialog box, need to clear Google Play Services Data
    public void onStartClicked(View view) {
        Button startButton = findViewById(R.id.runButton);

        // Build user message to publish to other students
        Message myMessage = new Message(buildMessage().getBytes(StandardCharsets.UTF_8));

        SharedPreferences preferences = getSharedPreferences("BOF", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        // button is start
        if (buttonState == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            //builder.setCancelable(true);
            builder.setTitle("Would you like to resume a previous session or create a new one?");
            //builder.setMessage("Message");
            String[] options = {"Resume Session", "New Session"};
            builder.setItems(options, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if (which == 0) {
                        Log.d("Resume was clicked", "Resume was clicked");

                        Intent intent = new Intent(ListOfBoFActivity.this, SavedSessionsActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(intent);

                    } else if (which == 1) {
                        Log.d("New was clicked", "New was clicked");
                        onNewSessionClicked();
                    }
                    // The 'which' argument contains the index position
                    // of the selected item
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();

        // button is stop
        } else {
            // only becomes true once confirm save is clicked
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            //builder.setCancelable(true);
            builder.setTitle("Save current session as:");
            builder.setView(R.layout.save_session_prompt);
            ArrayList<String> currentCourseArray = new ArrayList<>();
            for (Course c : ownCoursesSet) {
                if (c.year.equals("2022") && c.qtr.equals("WI")) {
                    currentCourseArray.add(c.getCourseFullStringReadable());
                }
            }
            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog stopDialog = builder.create();
            stopDialog.setContentView(R.layout.save_session_prompt);
            stopDialog.show();
            ArrayAdapter<String> courseListAA = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_dropdown_item, currentCourseArray);
            courseListAA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Spinner selectCoursesSpinner = stopDialog.findViewById(R.id.selectCurrentCourses);
            selectCoursesSpinner.setAdapter(courseListAA);
            buttonState = 0;
            startButton.setText("Start");
            Nearby.getMessagesClient(this).unsubscribe(realListener);
            Nearby.getMessagesClient(this).unpublish(myMessage);
            editor.putBoolean("bofSearchOn", false);
            editor.apply();
        }

    }

    public void onNewSessionClicked(){

        String defaultName = new TimeStamp().getTime();
        Log.d("TimeShown", "timestamp is " + defaultName);
        int sessionID = db.sessionsWithStudentsDao().count()+1; // id of session

        Session session = new Session(sessionID,defaultName);
        db.sessionsWithStudentsDao().insert(session);
        Log.d("in BOFActivity", "name is: " + defaultName);

        SharedPreferences preferences = getSharedPreferences("BOF", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("lastSessionID", sessionID);

        // Get current data stored in database
        currentSessionId = sessionID;
        setSession(sessionID);

        Button startButton = findViewById(R.id.runButton);

        // Build user message to publish to other students
        Message myMessage = new Message(buildMessage().getBytes(StandardCharsets.UTF_8));

        // if start and new session is clicked
        // Search is currently off
        //if (buttonState == 0) {
            buttonState = 1;
            startButton.setText("Stop");
            Nearby.getMessagesClient(this).subscribe(realListener);
            Nearby.getMessagesClient(this).publish(myMessage);
            editor.putBoolean("bofSearchOn", true);
            editor.apply();
            testListener.getMessage();
      /*  } else { // Search is currently on
            buttonState = 0;
            startButton.setText("Start");
            Nearby.getMessagesClient(this).unsubscribe(realListener);
            Nearby.getMessagesClient(this).unpublish(myMessage);
            editor.putBoolean("bofSearchOn", false);
            editor.apply();
        } */
    }

    public void setSession(int sessionId) {
        students = db.studentWithCoursesDao().getFromSession(sessionId);
        studentViewAdapter = new ListOfBoFViewAdapter(students);
        studentRecyclerView.setAdapter(studentViewAdapter);
        // Initialize a HashSet of messages that we've seen so far
        // seenMessages = db.sessionsWithStudentsDao().get(sessionId).session.getSeenMessages();
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
        message = message.trim();

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
            //if(!seenMessages.contains(rawString)){
                // Add message to seen messages
                //seenMessages.add(rawString);
                // Parse data from raw messages
                parseStudentMessage(rawString);
                // Refresh screen with new data after processing
                students = db.studentWithCoursesDao().getFromSession(currentSessionId);
                studentViewAdapter = new ListOfBoFViewAdapter(students);
                studentRecyclerView.setAdapter(studentViewAdapter);
            //}
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

                // Ensure that a new course ID is used
                int currId = courseDao.numCourses()+1;

                String dept = courseParts[2];
                Log.d("Found new dept", dept);
                String num = courseParts[3];
                Log.d("Found new course num", num);
                String year = courseParts[0];
                Log.d("Found new year", year);
                String qtr = courseParts[1];
                Log.d("Found new qtr", qtr);
                String size = courseParts[4];
                Log.d("Found new size", size);

                // Create new course
                newCourse = new Course(currId, studentId, dept, num, year, qtr, size);

                // If new course matches with one of the user's courses, add it to the database
                if (ownCoursesSet.contains(newCourse)) {
                    db.coursesDao().insert(newCourse);
                    numClassesOverlap++;
                }
            }

            // If new student has 1 or more shared courses, add them to the student database
            if (numClassesOverlap > 0) {
                Student newStudent = new Student(studentId, currentSessionId, studentName, photoUrl, numClassesOverlap);
                db.studentWithCoursesDao().insert(newStudent);
            }
        }

        @Override
        public void onLost(@NonNull Message message) {
            Log.d(TAG, "Lost sight of message: " + new String(message.getContent()));
        }
    }
}
