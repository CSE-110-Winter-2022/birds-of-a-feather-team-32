/**
 * Filename: CheckImageTask.java
 * Sources: AsyncTask in Android Developers
 *
 * Description: This file is responsible for checking the image validity of the entered text from
 * the URL Text View. CheckImageTask extends AsyncTask in order to utilize a background thread
 * operation in order to check the validity of the text retrieved from the URL TextView.
 * CheckImageTask returns true if the text is a valid image URL and false if not.
 */
package com.example.birdsofafeather;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class CheckImageTask extends AsyncTask<String, Void, Boolean> {

    boolean image = false; // boolean to check if string is valid image url or not

    /**
     * Method that checks if URL is valid and if URL is an image URL in the background
     * @param url
     * @return Boolean specifying if image is valid image URL (true if valid image URL)
     */
    protected Boolean doInBackground(String... url) {
        // returns true if url is valid image url
        try {
            URL urlObj = new URL(url[0]);
            URLConnection connection = urlObj.openConnection();
            String contentType = connection.getHeaderField("Content-Type");
            image = contentType.startsWith("image/");
            return image;
        // catches exceptions and return false for invalid image URL
        } catch(MalformedURLException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }

}
