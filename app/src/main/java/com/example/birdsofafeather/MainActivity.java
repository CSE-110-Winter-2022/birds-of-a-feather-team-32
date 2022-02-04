package com.example.birdsofafeather;

import static android.widget.ImageView.*;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    Handler mainHandler = new Handler();
    ImageView imageView; // new
    TextView URLText; // new


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onConfirmClicked(View view) {
    }

    // new
    public void onDoneClicked(View view){
        imageView = findViewById(R.id.pfp);
        URLText = findViewById(R.id.URL);
        String URLString = URLText.getText().toString();
        Glide.with(this).load(URLString).into(imageView);
    }


/*
    public void onDoneClicked(View view) throws IOException {
        TextView URLText = findViewById(R.id.URL);
        ImageView img = findViewById(R.id.pfp);
        String s = URLText.getText().toString();

        new FetchImage(s, img).start();
    }

    class FetchImage extends Thread{
        String URL;
        Bitmap btp;
        ImageView image;

        FetchImage(String URL, ImageView image){
            this.URL = URL;
            this.image = image;

        }

        @Override
        public void run(){
            InputStream in = null;
            try {
                in = new URL(URL).openStream();
                btp = BitmapFactory.decodeStream(in);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mainHandler.post(new Runnable() {
                @Override
                public void run(){
                    image.setImageBitmap(btp);
                }
            });
        }
    } */


}