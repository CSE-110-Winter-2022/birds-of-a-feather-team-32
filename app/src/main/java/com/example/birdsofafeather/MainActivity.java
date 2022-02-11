package com.example.birdsofafeather;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onEnterClicked(View view) {
        SharedPreferences preferences = getSharedPreferences("BOF", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String userName = ((TextView)findViewById(R.id.name)).getText().toString();
        if(userName.equals("")){
            ErrorUtilities.showAlert(this, "Whoa! Don't forget to set your name");
            Log.d("<onEnter>", "Empty Name");
        }
        else{
            editor.putString("name", userName);
            editor.apply();
            Log.d("<onEnter>", preferences.getString("name", "No Name?"));
            Intent intent = new Intent(this, ImageActivity.class);
            startActivity(intent);
        }
    }
}