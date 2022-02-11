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

    @Test
    public void testDoneButton() {
        rule.getScenario().onActivity(activity -> {
            // set text
            TextView URLTextView = (TextView) activity.findViewById(R.id.URL);
            String link = "https://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-PzLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0";
            URLTextView.setText(link);

            // check if text set correctly
            assertEquals(link,URLTextView.getText().toString());


            // press done
            activity.findViewById(R.id.done).performClick();

            // imageview after clicking button
            ImageView profile = (ImageView) activity.findViewById(R.id.pfp);

            ImageView actualImg = activity.findViewById(R.id.pfp);

            // load actual image
            Glide.with(activity)
                    .load(link)
                    .into(actualImg);

            // check if imageview loaded matched expected image view
            assertEquals(profile,actualImg);
        });
    }
}
