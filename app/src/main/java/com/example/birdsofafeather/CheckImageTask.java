package com.example.birdsofafeather;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class CheckImageTask extends AsyncTask<String, Void, Boolean> {

    private Exception e;
    boolean image = false;

    protected Boolean doInBackground(String... url) {
        try {
            URL urlObj = new URL(url[0]);
            URLConnection connection = urlObj.openConnection();
            String contentType = connection.getHeaderField("Content-Type");
            image = contentType.startsWith("image/");
            return image;

        } catch(MalformedURLException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    protected void onPostExecute(Boolean bool) {

    }
}
