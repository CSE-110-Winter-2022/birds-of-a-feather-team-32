/**
 * Filename: ErrorUtilities.java
 * Sources: Lab 4
 *
 * Description: This file is responsible for displaying an alert to the user. ErrorUtilities class
 * is a class that holds the method showAlert in order to inform the user if something goes wrong
 * or if we require a certain action from the user.
 */
package com.example.birdsofafeather;

import android.app.AlertDialog;
import android.app.Activity;

public class ErrorUtilities {
    /**
     * Method that shows a message to the user. For the appropriate message and activity, this
     * creates the alert dialog with a title "Error!" and an "Ok" button for dismissal.
     * @param activity
     * @param message
     */
    public static void showAlert(Activity activity, String message){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(activity);

        alertBuilder
                .setTitle("Error!")
                .setMessage(message)
                .setPositiveButton("Ok",(dialog, id) -> {
                    dialog.cancel();
                })
                .setCancelable(true);

        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }
}
