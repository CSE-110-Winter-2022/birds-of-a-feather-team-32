package com.example.birdsofafeather;

import static android.content.Context.MODE_PRIVATE;
import static org.junit.Assert.assertEquals;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.widget.TextView;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.shadows.ShadowDialog;

@RunWith(AndroidJUnit4.class)
public class NameTest {

    @Rule
    public ActivityScenarioRule rule = new ActivityScenarioRule<>(MainActivity.class);

    /* checked saved name on shared preferences after clicking enter and entering text on
     nameTextView */
    @Test
    public void checkValidName() {
        rule.getScenario().onActivity(activity -> {

            // set name
            TextView nameTextView = (TextView) activity.findViewById(R.id.name);
            String nameString = "Eva";
            nameTextView.setText(nameString);

            // check if text set correctly
            assertEquals(nameString,nameTextView.getText().toString());

            // press enter
            activity.findViewById(R.id.button).performClick();

            // get name from shared preferences
            SharedPreferences sp = activity.getSharedPreferences("BOF",MODE_PRIVATE);
            String retrievedName = sp.getString("name","no name");

            // check name
            assertEquals(retrievedName, nameString);

        });
    }

    /* checked for alert and saved name on shared preferences after clicking enter and
       entering nothing on nameTextView */
    @Test
    public void checkInvalidName() {
        rule.getScenario().onActivity(activity -> {

            // set name
            TextView nameTextView = (TextView) activity.findViewById(R.id.name);
            String emptyString = "";

            // check if text set correctly
            assertEquals(emptyString,nameTextView.getText().toString());

            // press enter
            activity.findViewById(R.id.button).performClick();

            // get name from shared preferences
            SharedPreferences sp = activity.getSharedPreferences("BOF",MODE_PRIVATE);
            String retrievedNothing = sp.getString("name","no name");

            // check that there's no name saved
            assertEquals(retrievedNothing, "no name");

            // get dialog
            AlertDialog dialog = (AlertDialog) ShadowDialog.getLatestDialog();
            // check only one error pop up
            assertEquals(1, ShadowDialog.getShownDialogs().size());
            // check if AlertDialog object dialog is latest alert
            assertEquals(dialog, ShadowDialog.getShownDialogs().get(0));


        });
    }
}
