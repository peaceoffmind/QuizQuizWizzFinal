package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class leaderboard extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        String[] dataSet = {"Ramayan", "Mahabharat", "Buddha", "Movies", "Extra", "Maths", "Mahabharat", "Buddha", "Movies", "Extra", "Maths"};
        adapter = new recyclerViewAdapter(dataSet);
        recyclerView.setAdapter(adapter);
    }
}
