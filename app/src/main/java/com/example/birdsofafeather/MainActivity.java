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

    public void onEnterClicked(View view) {
        SharedPreferences preferences = getSharedPreferences("BOF", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String userName = ((TextView)findViewById(R.id.name)).getText().toString();


        if(userName.equals("")){
            ErrorUtilities.showAlert(this, "Whoa! Don't forget to set your name");
            Log.d("<onEnter>", "Empty Name");
        }
        else{
            editor.putString("name", userName);
            editor.apply();
            Log.d("<onEnter>", preferences.getString("name", "No Name?"));
            Intent intent = new Intent(this, ImageActivity.class);
            startActivity(intent);
        }
    }

    public void onFindStudentsClicked(View view) {
        Intent intent = new Intent(this, NearbyMessagesMockScreen.class);
        startActivity(intent);
    }
}