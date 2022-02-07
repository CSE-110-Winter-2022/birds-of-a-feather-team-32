package com.example.birdsofafeather;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.io.InputStream;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Handler mainHandler = new Handler();
    ImageView imageView;
    TextView URLText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onConfirmClicked(View view) {
        imageView = findViewById(R.id.pfp);
        URLText = findViewById(R.id.URL);
        String url = URLText.getText().toString();
        SharedPreferences pref = getPreferences(MODE_PRIVATE);  //save image url to sharedpref
        SharedPreferences.Editor editor = pref.edit();

        try {
            //InputStream is = (InputStream) new URL(url).getContent();
            //Drawable d = Drawable.createFromStream(is, "src name");

            // if user didn't press done, this would load image
            Glide.with(this)
                    .load(url)
                    .error(R.drawable.ic_baseline_error_24)
                    .fitCenter()
                    .into(imageView);
            //onDoneClicked(view);

            /*Drawable loadDraw = imageView.getDrawable();
            if (loadDraw.equals(R.drawable.ic_baseline_error_24)){
                throw new Exception("invalid url");
            } */

            editor.putString("image_url", url);
            editor.apply();

            Log.d("<onConfirm>", "URL is valid");


        } catch (Exception e) {
            Glide.with(this)
                    .load(R.drawable.ic_baseline_android_24)
                    .into(imageView);   //load default image if url is invalid
            editor.putString("image_url", "R.drawable.ic_baseline_android_24");
            editor.apply();
            Log.d("<onConfirm>", "URL is invalid");
        }

    }

    public void onDoneClicked(View view){
        imageView = findViewById(R.id.pfp);
        URLText = findViewById(R.id.URL);
        String URLString = URLText.getText().toString();
        Glide.with(this)
                .load(URLString)
                .error(R.drawable.ic_baseline_error_24)
                .into(imageView);
    }
}