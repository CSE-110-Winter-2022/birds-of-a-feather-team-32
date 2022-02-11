package com.example.birdsofafeather;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.bumptech.glide.Glide;

public class ImageActivity extends AppCompatActivity {

    ImageView imageView;
    TextView URLText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
    }

    public void onConfirmClicked(View view){
        imageView = findViewById(R.id.pfp);
        URLText = findViewById(R.id.URL);
        String url = URLText.getText().toString();

        SharedPreferences pref = getSharedPreferences("BOF",MODE_PRIVATE);  //save image url to sharedpref
        SharedPreferences.Editor editor = pref.edit();

        if(url.equals("")){      //url is empty
            editor.putString("image_url", "R.drawable.ic_baseline_android_24");
            editor.apply();
            Log.d("<onConfirm>", "URL is empty, loading default");
        }
        /*  This section of code doesn't work yet. I can't find the right conditional to figure this out
        else if(imageView.getDrawable() == ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_baseline_error_24)){
            ErrorUtilities.showAlert(this, "Cool your jets, that's an invalid URL!");       //invalid url, but only when done is pressed and image is updated
        }*/
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