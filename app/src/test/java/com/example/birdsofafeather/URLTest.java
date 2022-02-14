package com.example.birdsofafeather;

import static org.junit.Assert.assertEquals;

import android.widget.ImageView;
import android.widget.TextView;


import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.bumptech.glide.Glide;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class URLTest {

    @Rule
    public ActivityScenarioRule rule = new ActivityScenarioRule<>(ImageActivity.class);

    // check image views after clicking done and after entering valid url on URLTextView
    @Test
    public void test_Done_ValidURL() {
        rule.getScenario().onActivity(activity -> {
            // set text
            TextView URLTextView = (TextView) activity.findViewById(R.id.URL);
            String link = "https://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-PzLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0";
            URLTextView.setText(link);

            // check if text set correctly
            assertEquals(link,URLTextView.getText().toString());


            // press done
            activity.findViewById(R.id.done).performClick();

            // reference imageview after clicking button
            ImageView profile = (ImageView) activity.findViewById(R.id.pfp);

            // load actual image with glide
            ImageView actualImg = activity.findViewById(R.id.pfp);
            Glide.with(activity)
                    .load(link)
                    .into(actualImg);

            // check if imageview loaded matched expected image view
            assertEquals(profile,actualImg);
        });
    }

    // check image views after clicking done and entering invalid url on URLTextView
    @Test
    public void test_Done_InvalidURL() {
        rule.getScenario().onActivity(activity -> {
            // set text
            TextView URLTextView = (TextView) activity.findViewById(R.id.URL);
            String invalidLink = "hello";
            URLTextView.setText(invalidLink);

            // check if text set correctly
            assertEquals(invalidLink,URLTextView.getText().toString());


            // press done
            activity.findViewById(R.id.done).performClick();

            // imageview after clicking button
            ImageView profile = (ImageView) activity.findViewById(R.id.pfp);

            // load actual image
            ImageView actualImg = activity.findViewById(R.id.pfp);
            Glide.with(activity)
                    .load(R.drawable.ic_baseline_error_24)
                    .into(actualImg);

            // check if imageview loaded matched expected image view
            assertEquals(profile,actualImg);
        });
    }

    // check image views after clicking done and entering empty text on URLTextView
    @Test
    public void test_Done_EmptyURL() {
        rule.getScenario().onActivity(activity -> {
            // set text
            TextView URLTextView = (TextView) activity.findViewById(R.id.URL);
            String emptyLink = "";
            URLTextView.setText(emptyLink);

            // check if text set correctly
            assertEquals(emptyLink,URLTextView.getText().toString());


            // press done
            activity.findViewById(R.id.done).performClick();

            // imageview after clicking button
            ImageView profile = (ImageView) activity.findViewById(R.id.pfp);

            // load actual image
            ImageView actualImg = activity.findViewById(R.id.pfp);
            Glide.with(activity)
                    .load(R.drawable.ic_baseline_error_24)
                    .into(actualImg);

            // check if imageview loaded matched expected image view
            assertEquals(profile,actualImg);
        });
    }


}
