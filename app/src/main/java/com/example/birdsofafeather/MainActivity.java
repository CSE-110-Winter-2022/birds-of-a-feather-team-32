package com.example.birdsofafeather;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

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

    }

    public void onDoneClicked(View view){
        imageView = findViewById(R.id.pfp);
        URLText = findViewById(R.id.URL);
        String URLString = URLText.getText().toString();
        Glide.with(this).load(URLString).into(imageView);
    }
}