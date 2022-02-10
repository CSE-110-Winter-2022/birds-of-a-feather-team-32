package com.example.birdsofafeather;

import static android.content.Context.MODE_PRIVATE;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import android.content.Context;
import android.content.SharedPreferences;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

import android.content.SharedPreferences;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/*
@RunWith(AndroidJUnit4.class)
public class ImageTest {
    @Before
    public void init(){
    }

    @Test
    public void test1(){
        //assertEquals("https://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-PzLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0", SharedPreferences.);
    }
} */

@RunWith(AndroidJUnit4.class)
public class ImageTest {

    @Rule
    public ActivityScenarioRule rule = new ActivityScenarioRule<>(MainActivity.class);

    // click confirm after clicking done with valid url
    @Test
    public void checkConfirmButton() {
        rule.getScenario().onActivity(activity -> {
            // set text
            TextView URLTextView = (TextView) activity.findViewById(R.id.URL);
            String link = "https://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-PzLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0";
            URLTextView.setText(link);

            // check if text set correctly
            assertEquals(link,URLTextView.getText().toString());

            //press done
            activity.findViewById(R.id.done).performClick();
            // press confirm
            activity.findViewById(R.id.confirm).performClick();

            SharedPreferences sp = activity.getPreferences(MODE_PRIVATE);
            String retrievedURL = sp.getString("image_url","R.drawable.ic_baseline_android_24");

            assertEquals(retrievedURL, link);

        });
    }
/*
    // check invalid url link
    @Test
    public void checkInvalidURL() {
        rule.getScenario().onActivity(activity -> {
            // set text
            TextView URLTextView = (TextView) activity.findViewById(R.id.URL);
            String link = "ABCDEFG";
            URLTextView.setText(link);

            // check if text set correctly
            assertEquals(link,URLTextView.getText().toString());

            // press confirm
            activity.findViewById(R.id.confirm).performClick();

            SharedPreferences sp = activity.getPreferences(MODE_PRIVATE);
            String defaultImage = sp.getString("image_url","R.drawable.ic_baseline_android_24");

            assertEquals(defaultImage, "R.drawable.ic_baseline_android_24");




        });
    } */
}

