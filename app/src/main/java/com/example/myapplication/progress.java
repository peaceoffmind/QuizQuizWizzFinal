package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class progress extends AppCompatActivity {
    private ProgressBar progressBar;
    private TextView mCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        final String categoryName =  getIntent().getStringExtra("category");
        progressBar = findViewById(R.id.progressBar);
        mCategory = findViewById(R.id.category);

        mCategory.setText(categoryName);

        new CountDownTimer(10000, 1000) {
            public void onFinish() {
                // When timer is finished
                // Execute your code here
                return;
            }

            int count = 0;
            public void onTick(long millisUntilFinished) {
                // millisUntilFinished    The amount of time until finished.
                count += 10;
                //Toast.makeText(getApplicationContext(), String.valueOf(count), Toast.LENGTH_SHORT).show();
                progressBar.setProgress(count);
            }
        }.start();
    }
}
