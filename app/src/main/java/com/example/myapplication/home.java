package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class home extends AppCompatActivity {
    private Button mRamayan, mMahabharat, mBudhhacharitra, mRandom,mAddQuestions;
    private Button mLeaderBoard;
    private TextView mWelcomeNote;
    private Button mSignOut;
    private FirebaseAuth mAuth;
    private List<Integer> questionList;
    private int score=0;
    private UserData userData;
    private int count = 0;
    private ProgressBar progressBar;
    private Boolean isProgressActivityCalled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mRamayan = findViewById(R.id.ramayan);
        mMahabharat = findViewById(R.id.mahabharat);
        mBudhhacharitra = findViewById(R.id.buddha);
        mRandom = findViewById(R.id.random);
        mSignOut = findViewById(R.id.signout);
        mAuth = FirebaseAuth.getInstance();
        mAddQuestions = findViewById(R.id.addquestions);
        mWelcomeNote = findViewById(R.id.welcomenote);
        mLeaderBoard = findViewById(R.id.leaderboard);
        progressBar = findViewById(R.id.progressBar);

        String displayName = mAuth.getCurrentUser().getDisplayName();
        final String welcomeNote = "Hey! " + displayName +" Let's get stared ";
        String user_id = mAuth.getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(user_id);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userData = dataSnapshot.getValue(UserData.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mWelcomeNote.setText(welcomeNote);
        mRamayan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(home.this,Ramayan.class);
                    intent.putExtra("category","Ramayan");
                    questionList = userData.getquestionListMap().get("Ramayan");
                    intent.putIntegerArrayListExtra("questionList",(ArrayList<Integer>)questionList);
                    intent.putExtra("score",score);
                    startActivity( intent);
                    finish();
                    return;
                } catch (Exception e) {
                    new CountDownTimer(10000, 1000) {
                        public void onFinish() {
                            Intent intent = new Intent(home.this, Ramayan.class);
                            intent.putExtra("category", "Ramayan");
                            questionList = userData.getquestionListMap().get("Ramayan");
                            intent.putIntegerArrayListExtra("questionList", (ArrayList<Integer>) questionList);
                            intent.putExtra("score", score);
                            startActivity(intent);
                            finish();
                            return;
                        }

                        public void onTick(long millisUntilFinished) {
                            if(!isProgressActivityCalled)
                            {
                                isProgressActivityCalled=true;
                                Intent intent = new Intent(home.this, progress.class);
                                intent.putExtra("category", "Ramayan");
                                startActivity(intent);
                                finish();
                                return;
                            }
                        }
                    }.start();
                }

            }
        });
        mMahabharat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(home.this,Ramayan.class);
                    intent.putExtra("category","Mahabharat");
                    questionList = userData.getquestionListMap().get("Mahabharat");
                    intent.putIntegerArrayListExtra("questionList",(ArrayList<Integer>)questionList);
                    startActivity( intent);
                    finish();
                    return;
                }catch (Exception e) {
                    new CountDownTimer(10000, 1000) {
                        public void onFinish() {
                            Intent intent = new Intent(home.this, Ramayan.class);
                            intent.putExtra("category", "Mahabharat");
                            questionList = userData.getquestionListMap().get("Mahabharat");
                            intent.putIntegerArrayListExtra("questionList", (ArrayList<Integer>) questionList);
                            startActivity(intent);
                            finish();
                            return;
                        }

                        public void onTick(long millisUntilFinished) {
                            if(!isProgressActivityCalled)
                            {
                                isProgressActivityCalled=true;
                                Intent intent = new Intent(home.this, progress.class);
                                intent.putExtra("category", "Mahabharat");
                                startActivity(intent);
                                finish();
                                return;
                            }
                        }
                    }.start();
                }
            }
        });
        mBudhhacharitra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(home.this,Ramayan.class);
                    intent.putExtra("category","BudhhaCharitra");
                    questionList = userData.getquestionListMap().get("BudhhaCharitra");
                    intent.putIntegerArrayListExtra("questionList",(ArrayList<Integer>)questionList);
                    startActivity( intent);
                    finish();
                    return;
                }catch (Exception e) {
                    new CountDownTimer(10000, 1000) {
                        public void onFinish() {
                            Intent intent = new Intent(home.this, Ramayan.class);
                            intent.putExtra("category", "BudhhaCharitra");
                            questionList = userData.getquestionListMap().get("BudhhaCharitra");
                            intent.putIntegerArrayListExtra("questionList", (ArrayList<Integer>) questionList);
                            startActivity(intent);
                            finish();
                            return;
                        }

                        public void onTick(long millisUntilFinished) {
                            if(!isProgressActivityCalled)
                            {
                                isProgressActivityCalled=true;
                                Intent intent = new Intent(home.this, progress.class);
                                intent.putExtra("category", "Budhha");
                                startActivity(intent);
                                finish();
                                return;
                            }
                        }
                    }.start();
                }
            }
        });

        mSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAuth.getInstance().signOut();
                Intent intent = new Intent(home.this,MainActivity.class);
                startActivity( intent);
                finish();
                return;

            }
        });

        mAddQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(home.this,AddQuestions.class);
                startActivity( intent);
                finish();
                return;
            }
        });

        mLeaderBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    private void flyButtonsExceptThis(Button exceptThis) {
        Animation animation = new TranslateAnimation(0, 1000,0, 0);
        animation.setDuration(100);
        animation.setFillAfter(true);
        //myImage.startAnimation(animation);
        //myImage.setVisibility(0);
        if(exceptThis != mRamayan)
        {
            mRamayan.setAnimation(animation);
            mRamayan.setVisibility(View.INVISIBLE);
        }

        if(exceptThis != mMahabharat)
        {
            mMahabharat.setAnimation(animation);
            mMahabharat.setVisibility(View.INVISIBLE);
        }

        if(exceptThis != mBudhhacharitra)
        {
            mBudhhacharitra.setAnimation(animation);
            mBudhhacharitra.setVisibility(View.INVISIBLE);
        }
        if(exceptThis != mRandom)
        {
            mRandom.setAnimation(animation);
            mRandom.setVisibility(View.INVISIBLE);
        }

        if(exceptThis != mAddQuestions)
        {
            mAddQuestions.setAnimation(animation);
            mAddQuestions.setVisibility(View.INVISIBLE);
        }

        if(exceptThis != mLeaderBoard)
        {
            mLeaderBoard.setAnimation(animation);
            mLeaderBoard.setVisibility(View.INVISIBLE);
        }

        if(exceptThis != mSignOut)
        {
            mSignOut.setAnimation(animation);
            mSignOut.setVisibility(View.INVISIBLE);
        }
    }
}
