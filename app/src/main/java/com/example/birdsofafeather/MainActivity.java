/**
 * Filename: MainActivity.java
 *
 * Description: This file is responsible for saving the appropriate user name based on the
 * Name Text View.
 */
package com.example.birdsofafeather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.TextView;

import com.example.birdsofafeather.model.db.AppDatabase;
import com.example.birdsofafeather.model.db.Student;
import com.example.birdsofafeather.model.db.StudentWithCourses;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    /**
     * Method that starts activity with loaded data from savedInstanceState
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //if name already inputted, skip to next activity
        SharedPreferences preferences = getSharedPreferences("BOF", MODE_PRIVATE);
        String retrievedName = preferences.getString("name", "name not found");
        if(retrievedName != "name not found"){
            Intent intent = new Intent(this, ImageActivity.class);
            startActivity(intent);
        }
    }

    /**
     * Method that is responsible for saving name to shared preferences. Two cases:
     * 1. entered text: save to SharedPreferences
     * 2. empty text: show alert to that no name is entered
     * @param view
     */
    public void onEnterClicked(View view) {
        // get string userName from Name Text View
        SharedPreferences preferences = getSharedPreferences("BOF", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String userName = ((TextView)findViewById(R.id.name)).getText().toString();

        // case when user entered nothing
        if(userName.equals("")){
            ErrorUtilities.showAlert(this, "Whoa! Don't forget to set your name!");
            Log.d("<onEnter>", "Empty Name");
        }
        // save name to shared preferences
        else{
            editor.putString("name", userName);
            editor.apply();
            Log.d("<onEnter>", preferences.getString("name", "No Name?"));
            Intent intent = new Intent(this, ImageActivity.class);
            startActivity(intent);
        }
    }

}