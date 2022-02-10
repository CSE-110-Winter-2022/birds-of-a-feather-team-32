package com.example.birdsofafeather;

import android.app.AlertDialog;
import android.app.Activity;

public class ErrorUtilities {
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
