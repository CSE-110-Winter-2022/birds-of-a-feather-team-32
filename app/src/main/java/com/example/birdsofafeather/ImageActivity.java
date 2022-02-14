/**
 * Filename: ImageActivity.java
 * Sources: Glide via CodePath
 *
 * Description: This file is responsible for displaying the appropriate image and saving the
 * appropriate url to shared preferences for each user's profile picture.
 */
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

    /**
     * Method that starts activity with loaded data from savedInstanceState
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        //if image already inputted, skip to next activity
        SharedPreferences preferences = getSharedPreferences("BOF", MODE_PRIVATE);
        String retrievedImage = preferences.getString("image_url", "image not found");
        if(retrievedImage != "image not found"){
            Intent intent = new Intent(this, CourseActivity.class);
            startActivity(intent);
        }
    }

    /**
     * Method that executes after clicking the confirm button. It calls methods that checks image
     * url and appropriately save it to shared preferences
     * @param view
     */
    public void onConfirmClicked(View view){
        // get string url from URL Text View
        imageView = findViewById(R.id.pfp);
        URLText = findViewById(R.id.URL);
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

    /**
     * Method responsible for saving image url to SharedPreferences based on three cases:
     * 1. empty URL: saves default
     * 2. invalid URL: temporarily saves default, but alert appears to re-enter url
     * 3. valid URL: saves url String
     * @param url
     */
    public void savePref(String url){
        SharedPreferences pref = getSharedPreferences("BOF",MODE_PRIVATE);  //save image url to sharedpref
        SharedPreferences.Editor editor = pref.edit();

        // case when url is empty (no text from TextView)
        if(url.equals("")){
            editor.putString("image_url", "R.drawable.ic_baseline_android_24");
            editor.apply();
            Log.d("<onConfirm>", "URL is empty, loading default");
            Intent intent = new Intent(this, CourseActivity.class);
            startActivity(intent);

        // case when URL is not a valid image URL
        } else if (image == false) {
            editor.putString("image_url", "R.drawable.ic_baseline_android_24");
            editor.apply();
            Log.d("<onConfirm>", "URL is invalid");
            ErrorUtilities.showAlert(this, "Cool your jets, that's an invalid URL!");
        }

        // case when URL is a valid image URL
        else {
            editor.putString("image_url", url);
            editor.apply();
            Log.d("<onConfirm>", "URL is valid");
            Intent intent = new Intent(this, CourseActivity.class);
            startActivity(intent);
        }
    }

    // Method responsible for loading appropriate image after clicking done button
    /**
     * Method responsible for loading appropriate image after clicking done button. Two cases:
     * 1. empty URL or invalid URL: displays error image
     * 3. valid URL: displays image from url
     * @param view
     */
    public void onDoneClicked(View view){
        // get string url from URL Text View
        imageView = findViewById(R.id.pfp);
        URLText = findViewById(R.id.URL);
        String URLString = URLText.getText().toString();
        // load image if it is a valid image url, else load error image
        Glide.with(this)
                .load(URLString)
                .error(R.drawable.ic_baseline_error_24)
                .into(imageView);
        Log.d("<onDone>", "Loaded!");
    }
}