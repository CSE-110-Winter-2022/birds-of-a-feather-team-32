package com.example.birdsofafeather;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.birdsofafeather.model.Student;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;

import java.util.Arrays;

public class ListOfBoFActivity extends AppCompatActivity {

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

    private static final String TAG = "bofNearby";
    private MessageListener realListener;
    private FakedMessageListener testListener;
    private int buttonState = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boflist);

        studentRecyclerView = findViewById(R.id.student_view);

        studentLayoutManager = new LinearLayoutManager(this);
        studentRecyclerView.setLayoutManager(studentLayoutManager);

        studentViewAdapter = new ListOfBoFViewAdapter(Arrays.asList(data));
        studentRecyclerView.setAdapter(studentViewAdapter);

        realListener = new MessageListener() {
            @Override
            public void onFound(@NonNull Message message) {
                Log.d(TAG, "Found message: " + new String(message.getContent()));
            }

            @Override
            public void onLost(@NonNull Message message) {
                Log.d(TAG, "Lost sight of message: " + new String(message.getContent()));
            }
        };
        this.testListener = new FakedMessageListener(realListener, 3, "Hello World");
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

    @Override
    public void onStop() {
        super.onStop();
        Nearby.getMessagesClient(this).unsubscribe(realListener);
    }

}
