/**
 * File: ErrorUtilities.java
 * Description: Handles displaying error dialogs.
 *
 * @author Team 32
 */
package com.example.birdsofafeather;

import android.app.AlertDialog;
import android.app.Activity;

public class ErrorUtilities {
    /**
     * Displays an error dialog with a description of the error.
     * @param activity Current activity
     * @param message Error description to display
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
