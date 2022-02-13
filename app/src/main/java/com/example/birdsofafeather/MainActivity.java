package com.example.birdsofafeather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.TextView;

import com.example.birdsofafeather.model.db.AppDatabase;
import com.example.birdsofafeather.model.db.Student;
import com.example.birdsofafeather.model.db.StudentWithCourses;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Method responsible for saving name to shared preferences
    public void onEnterClicked(View view) {
        // getting shared preferences and declaring editor
        SharedPreferences preferences = getSharedPreferences("BOF", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        // make TextView's name text into a string
        String userName = ((TextView)findViewById(R.id.name)).getText().toString();

        // case when user entered nothing
        if(userName.equals("")){
            // shows alert that no name is entered
            ErrorUtilities.showAlert(this, "Whoa! Don't forget to set your name");
            Log.d("<onEnter>", "Empty Name");
        }
        // save name to shared preferences
        else{
            editor.putString("name", userName);
            editor.apply();
            Log.d("<onEnter>", preferences.getString("name", "No Name?"));
            // start activity to go to screen of adding image
            Intent intent = new Intent(this, ImageActivity.class);
            startActivity(intent);
        }
    }

}