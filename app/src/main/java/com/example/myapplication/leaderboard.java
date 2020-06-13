package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class leaderboard extends AppCompatActivity {
    private RecyclerView recyclerView;
    private recyclerViewAdapter adapter;
    private LinearLayoutManager layoutManager;
    public ArrayList<UserData> userList = new ArrayList<UserData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_leaderboard);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                   userList.add(snap.getValue(UserData.class)) ;
                }
                Collections.sort(userList,new UserComparator());
                /*Collections.sort(userList, new Comparator<UserData>(){
                    public int compare(UserData u1, UserData u2) {
                        return u2.getTotalScore()-u1.getTotalScore();
                    }
                });*/
                adapter = new recyclerViewAdapter(userList);
                recyclerView.setAdapter(adapter);
                layoutManager = new LinearLayoutManager(leaderboard.this);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setHasFixedSize(true);
                //Toast.makeText(leaderboard.this,String.valueOf(userList.size()), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public class UserComparator implements Comparator<UserData> {
        @Override
        public int compare(UserData user1, UserData user2) {
            if(user1.getTotalScore()!=user2.getTotalScore())
                return user2.getTotalScore()-user1.getTotalScore();
            else if(user1.getAccuracy()!=user2.getAccuracy()) {
                if(user2.getAccuracy() - user1.getAccuracy() < 0)
                    return -1;
                else
                    return 1;
            }
            else
            {
                if(user2.getStrikeRate() - user1.getStrikeRate() < 0)
                    return -1;
                else
                    return 1;
            }
        }
    }

}
