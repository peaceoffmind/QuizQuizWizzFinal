package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class Score extends AppCompatActivity {
    private TextView scoreView;
    private int scoreDigits=0;
    private Button home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        scoreDigits = getIntent().getIntExtra("score",0);

        scoreView = findViewById(R.id.score);
        home = findViewById(R.id.home);

        scoreView.setText(String.valueOf(scoreDigits));
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),home.class);
                startActivity( intent);
                finish();
                return;
            }
        });
    }
}
