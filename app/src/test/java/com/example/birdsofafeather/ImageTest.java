package com.example.birdsofafeather;

import static android.content.Context.MODE_PRIVATE;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import android.content.SharedPreferences;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

import android.widget.TextView;



@RunWith(AndroidJUnit4.class)
public class ImageTest {

    @Rule
    public ActivityScenarioRule rule = new ActivityScenarioRule<>(ImageActivity.class);

    /* checked saved image url on shared preferences after clicking confirm and entering valid
     url on URLTextView */
    @Test
    public void test_Confirm_ValidURL() {
        rule.getScenario().onActivity(activity -> {
            // set url in TextView
            TextView URLTextView = (TextView) activity.findViewById(R.id.URL);
            String link = "https://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-PzLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0";
            URLTextView.setText(link);

            // check if text set correctly
            assertEquals(link,URLTextView.getText().toString());

            // press confirm
            activity.findViewById(R.id.confirm).performClick();

            // get url from shared preferences
            SharedPreferences sp = activity.getSharedPreferences("BOF",MODE_PRIVATE);
            String retrievedURL = sp.getString("image_url","R.drawable.ic_baseline_android_24");

            // check url
            assertEquals(retrievedURL, link);

        });
    }

    /* checked saved image url on shared preferences after clicking confirm and entering invalid
     url on URLTextView */
    @Test
    public void test_Confirm_InvalidURL() {
        rule.getScenario().onActivity(activity -> {
            // set text
            TextView URLTextView = (TextView) activity.findViewById(R.id.URL);
            String invalidLink = "helloItIsMe";
            URLTextView.setText(invalidLink);

            // check if text set correctly
            assertEquals(invalidLink,URLTextView.getText().toString());

            // press confirm
            activity.findViewById(R.id.confirm).performClick();

            // get saved default drawable from shared preferences
            SharedPreferences sp = activity.getPreferences(MODE_PRIVATE);
            String defaultImage = sp.getString("image_url","R.drawable.ic_baseline_android_24");

            // check if is default drawable saved
            assertEquals(defaultImage, "R.drawable.ic_baseline_android_24");

        });
    }

    /* checked saved image url on shared preferences after clicking confirm and entering empty
     text on URLTextView */
    @Test
    public void test_Confirm_EmptyURL() {
        rule.getScenario().onActivity(activity -> {
            // set text
            TextView URLTextView = (TextView) activity.findViewById(R.id.URL);
            String emptyLink = "";
            URLTextView.setText(emptyLink);

            // check if text set correctly
            assertEquals(emptyLink,URLTextView.getText().toString());

            // press confirm
            activity.findViewById(R.id.confirm).performClick();

            // get saved default drawable from shared preferences
            SharedPreferences sp = activity.getPreferences(MODE_PRIVATE);
            String defaultImage = sp.getString("image_url","R.drawable.ic_baseline_android_24");

            // check if default image saved
            assertEquals(defaultImage, "R.drawable.ic_baseline_android_24");

        });
    }


}

