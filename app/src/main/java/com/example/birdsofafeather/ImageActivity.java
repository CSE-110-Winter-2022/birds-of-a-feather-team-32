package com.example.birdsofafeather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;

public class ImageActivity extends AppCompatActivity {

    ImageView imageView;
    TextView URLText;
    boolean image = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
    }

    // Method responsible for saving appropriate link after pressing confirm button
    public void onConfirmClicked(View view){
        // reference profile ImageView and url TextView
        imageView = findViewById(R.id.pfp);
        URLText = findViewById(R.id.URL);
        // make TextView's url text into a string
        String url = URLText.getText().toString();

        // check if url is a valid image url
        try {
            image = new CheckImageTask().execute(url).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // call method to save appropriate url
        savePref(url);
    }

    // Method responsible for saving appropriate url into shared preferences
    public void savePref(String url){
        // getting shared preferences and declaring editor
        SharedPreferences pref = getSharedPreferences("BOF",MODE_PRIVATE);  //save image url to sharedpref
        SharedPreferences.Editor editor = pref.edit();

        // case when url is empty (no text from TextView)
        if(url.equals("")){
            editor.putString("image_url", "R.drawable.ic_baseline_android_24");
            editor.apply();
            Log.d("<onConfirm>", "URL is empty, loading default");
            // take user to next screen by starting activity
            Intent intent = new Intent(this, CourseActivity.class);
            startActivity(intent);
        // case when URL is not a valid image URL
        } else if (image == false) {
            // saves default image to shared preferences
            editor.putString("image_url", "R.drawable.ic_baseline_android_24");
            editor.apply();
            Log.d("<onConfirm>", "URL is invalid");
            // shows alert that url is not valid
            ErrorUtilities.showAlert(this, "Cool your jets, that's an invalid URL!");
            // doesn't take user to next page until valid image URL is entered
        }
        // case when URL is a valid image URL
        else {
            // saves default image to shared preferences
            editor.putString("image_url", url);
            editor.apply();
            Log.d("<onConfirm>", "URL is valid");
            // take user to next screen by starting activity
            Intent intent = new Intent(this, CourseActivity.class);
            startActivity(intent);
        }
    }

    // Method responsible for loading appropriate image after clicking done button
    public void onDoneClicked(View view){
        // reference profile ImageView and url TextView
        imageView = findViewById(R.id.pfp);
        URLText = findViewById(R.id.URL);
        // make TextView's text into a string
        String URLString = URLText.getText().toString();
        // load image if it is a valid image url, else load error image
        Glide.with(this)
                .load(URLString)
                .error(R.drawable.ic_baseline_error_24)
                .into(imageView);
        Log.d("<onDone>", "Loaded!");
    }
}