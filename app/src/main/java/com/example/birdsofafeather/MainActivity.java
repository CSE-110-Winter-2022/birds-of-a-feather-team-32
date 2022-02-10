package com.example.birdsofafeather;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    TextView URLText;
    private FilterInputStream ImageIO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onConfirmClicked(View view){
        imageView = findViewById(R.id.pfp);
        URLText = findViewById(R.id.URL);
        String url = URLText.getText().toString();

        SharedPreferences pref = getPreferences(MODE_PRIVATE);  //save image url to sharedpref
        SharedPreferences.Editor editor = pref.edit();


        if(url == ""){      //url is empty
            editor.putString("image_url", "R.drawable.ic_baseline_android_24");
            editor.apply();
            Log.d("<onConfirm>", "URL is empty, loading default");
        }
        else {
            editor.putString("image_url", url); //url is valid
            editor.apply();
            Log.d("<onConfirm>", "URL is valid");
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