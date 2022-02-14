package com.example.birdsofafeather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class NearbyMessagesMockScreen extends AppCompatActivity {

    private ArrayList<String> messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_messages_mock_screen);
        this.setTitle("Nearby Messages Mock Screen");
        messages = new ArrayList<>();
    }

    @Override
    protected void onPause() {
        super.onPause();
        messages.clear();
    }

    public void onEnterClicked(View view) {
        TextView messageView = findViewById(R.id.textBox);
        String message = messageView.getText().toString();
        messages.add(message);
        messageView.setText("");
    }

    public void onContinueClicked(View view) {
        Intent intent = new Intent(this, ListOfBoFActivity.class);
        intent.putExtra("messages", messages);
        startActivity(intent);
    }
}