package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ramayan extends AppCompatActivity {
    //LAYOUT
    private TextView qStatement;
    private Button option1,option2,option3,option4;
    private TextView category;
    private FirebaseAuth mAuth;
    private int TotalQuestions;
    private Question question;
    private int questionNumber;
    final int MaxQuizSize = 5;
    private int quizLength;
    private int score;
    private int questionCount;
    private int rightAnswers;
    private int wrongAnswers;
    private double strikeRate;
    private  double accuracy;
    private int totalScore;
    private long tStart=0;
    Animation anim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ramayan);

        qStatement=  findViewById(R.id.question);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        category = findViewById(R.id.category);
        mAuth = FirebaseAuth.getInstance();


        final String categoryName =  getIntent().getStringExtra("category");
        final List<Integer> questionList= getIntent().getIntegerArrayListExtra("questionList");
        score = getIntent().getIntExtra("score",0);
        questionCount = getIntent().getIntExtra("questionCount",0);
        //////////////////////////////////////////////////////////
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("questions").child(categoryName);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                TotalQuestions = (int) dataSnapshot.child("total questions").getValue(int.class);
                quizLength = MaxQuizSize<TotalQuestions?MaxQuizSize:TotalQuestions;
                Random rand = new Random();
                questionNumber =  rand.nextInt(TotalQuestions) +1;
                if(questionList.size()==1 && questionList.contains(0))
                    questionList.clear();

                if(questionList.size()==TotalQuestions)
                    questionList.subList(0,TotalQuestions-questionCount-1).clear();

                if(!questionList.isEmpty())
                {
                    while(questionList.contains(questionNumber))
                    {
                        questionNumber =  rand.nextInt(TotalQuestions) +1;
                    }
                }
                questionList.add(questionNumber);

                //Toast.makeText(getApplicationContext(),user_id,Toast.LENGTH_SHORT).show();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                String user_id = mAuth.getCurrentUser().getUid();
                DatabaseReference userRef =  database.getReference("users").child(user_id);
                userRef.child("questionListMap").child(categoryName).setValue(questionList);
                question = (Question) dataSnapshot.child(String.valueOf(questionNumber)).getValue(Question.class);

                qStatement.setText(question.getStatement());
                option1.setText(question.getOption1().getStatement());
                option2.setText(question.getOption2().getStatement());
                option3.setText(question.getOption3().getStatement());
                option4.setText(question.getOption4().getStatement());
                tStart = System.currentTimeMillis();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        final String user_id = mAuth.getCurrentUser().getUid();
        final DatabaseReference dataBaseRefUsers = database.getReference("users").child(user_id);
        dataBaseRefUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserData userData = dataSnapshot.getValue(UserData.class);
                rightAnswers = userData.getmRightAnswers();
                wrongAnswers = userData.getmWrongAnswers();
                strikeRate = userData.getStrikeRate();
                accuracy = userData.getAccuracy();
                totalScore = userData.getTotalScore();
                //Toast.makeText(getApplicationContext(),String.valueOf(rightAnswers),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        category.setText(categoryName);

        anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(10); //You can manage the blinking time with this parameter
        anim.setStartOffset(5);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        /////////////////////////////////////////////////////////////////
        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long tEnd = System.currentTimeMillis();
                long tDelta = tEnd - tStart;
                Double elapsedSeconds = tDelta / 1000.0;
                int totalAnswers = rightAnswers+wrongAnswers;
                Toast.makeText(getApplicationContext(),String.valueOf(elapsedSeconds),Toast.LENGTH_SHORT).show();
                Double newStrikeRate = (strikeRate*totalAnswers+elapsedSeconds)/(totalAnswers+1);
                dataBaseRefUsers.child("strikeRate").setValue(newStrikeRate);

                if(option1.getText()==question.getRightAnswer().getStatement())
                {
                    option1.setText("Right Answer");
                    option1.setBackgroundResource(R.drawable.roundbuttongreen);
                    option1.setTextColor(Color.WHITE);
                    double acc = (accuracy*totalAnswers+100)/(totalAnswers+1);
                    dataBaseRefUsers.child("accuracy").setValue(acc);
                    int scr = totalScore+10;
                    dataBaseRefUsers.child("totalScore").setValue(scr);
                    score= score+10;
                    final int rightAns = rightAnswers+1;
                    dataBaseRefUsers.child("mRightAnswers").setValue(rightAns);


                }
                else {
                    option1.setText("Wrong Answer");
                    option1.setBackgroundResource(R.drawable.roundbuttonred);
                    option1.setTextColor(Color.WHITE);
                    option1.startAnimation(anim);
                    double acc = (accuracy*totalAnswers)/(totalAnswers+1);
                    dataBaseRefUsers.child("accuracy").setValue(acc);
                    final int wrongAns= wrongAnswers+1;
                    dataBaseRefUsers.child("mWrongAnswers").setValue(wrongAns);
                }
                questionCount++;
                if(questionCount<=quizLength) {
                    Intent intent = new Intent(getApplicationContext(), Ramayan.class);
                    intent.putExtra("category", categoryName);
                    intent.putIntegerArrayListExtra("questionList",(ArrayList<Integer>)questionList);
                    intent.putExtra("score",score);
                    intent.putExtra("questionCount",questionCount);
                    startActivity(intent);
                    finish();
                    return;
                }
                else
                {
                    questionList.clear();
                    questionCount=0;
                    Intent intent = new Intent(getApplicationContext(), Score.class);
                    intent.putExtra("score", score);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        });

        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long tEnd = System.currentTimeMillis();
                long tDelta = tEnd - tStart;
                double elapsedSeconds = tDelta / 1000.0;
                int totalAnswers = rightAnswers+wrongAnswers;
                double newStrikeRate = (strikeRate*totalAnswers+elapsedSeconds)/(totalAnswers+1);
                dataBaseRefUsers.child("strikeRate").setValue(newStrikeRate);
                if(option2.getText()==question.getRightAnswer().getStatement())
                {
                    option2.setText("Right Answer");
                    option2.setBackgroundResource(R.drawable.roundbuttongreen);
                    option2.setTextColor(Color.WHITE);
                    score= score+10;
                    double acc = (accuracy*totalAnswers+100)/(totalAnswers+1);
                    dataBaseRefUsers.child("accuracy").setValue(acc);
                    int scr = totalScore+10;
                    dataBaseRefUsers.child("totalScore").setValue(scr);
                    final int rightAns = rightAnswers+1;
                    dataBaseRefUsers.child("mRightAnswers").setValue(rightAns);
                }
                else
                {
                    option2.setText("Wrong Answer");
                    option2.setBackgroundResource(R.drawable.roundbuttonred);
                    option2.setTextColor(Color.WHITE);
                    option2.startAnimation(anim);
                    double acc = (accuracy*totalAnswers)/(totalAnswers+1);
                    dataBaseRefUsers.child("accuracy").setValue(acc);
                    final int wrongAns= wrongAnswers+1;
                    dataBaseRefUsers.child("mWrongAnswers").setValue(wrongAns);
                }
                questionCount++;
                if(questionCount<=quizLength) {
                    Intent intent = new Intent(getApplicationContext(), Ramayan.class);
                    intent.putExtra("category", categoryName);
                    intent.putIntegerArrayListExtra("questionList",(ArrayList<Integer>)questionList);
                    intent.putExtra("score",score);
                    intent.putExtra("questionCount",questionCount);
                    startActivity(intent);
                    finish();
                    return;
                }
                else
                {
                    questionList.clear();
                    questionCount=0;
                    Intent intent = new Intent(getApplicationContext(), Score.class);
                    intent.putExtra("score",score);
                    startActivity(intent);
                    finish();
                    return;
                }
            }

        });


        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long tEnd = System.currentTimeMillis();
                long tDelta = tEnd - tStart;
                double elapsedSeconds = tDelta / 1000.0;
                int totalAnswers = rightAnswers+wrongAnswers;
                double newStrikeRate = (strikeRate*totalAnswers+elapsedSeconds)/(totalAnswers+1);
                dataBaseRefUsers.child("strikeRate").setValue(newStrikeRate);
                if(option3.getText()==question.getRightAnswer().getStatement())
                {
                    option3.setText("Right Answer");
                    option3.setBackgroundResource(R.drawable.roundbuttongreen);
                    option3.setTextColor(Color.WHITE);
                    //option3.startAnimation(anim);00
                    int scr = totalScore+10;
                    dataBaseRefUsers.child("totalScore").setValue(scr);
                    double acc = (accuracy*totalAnswers+100)/(totalAnswers+1);
                    dataBaseRefUsers.child("accuracy").setValue(acc);
                    score= score+10;
                    final int rightAns = rightAnswers+1;
                    dataBaseRefUsers.child("mRightAnswers").setValue(rightAns);
                }
                else
                {
                    option3.setText("Wrong Answer");
                    option3.setBackgroundResource(R.drawable.roundbuttonred);
                    option3.setTextColor(Color.WHITE);
                    option3.startAnimation(anim);
                    double acc = (accuracy*totalAnswers)/(totalAnswers+1);
                    dataBaseRefUsers.child("accuracy").setValue(acc);
                    final int wrongAns= wrongAnswers+1;
                    dataBaseRefUsers.child("mWrongAnswers").setValue(wrongAns);
                }
                questionCount++;
                if(questionCount<=quizLength) {
                    Intent intent = new Intent(getApplicationContext(), Ramayan.class);
                    intent.putExtra("category", categoryName);
                    intent.putIntegerArrayListExtra("questionList",(ArrayList<Integer>)questionList);
                    intent.putExtra("score",score);
                    intent.putExtra("questionCount",questionCount);
                    startActivity(intent);
                    finish();
                    return;
                }
                else
                {
                    questionList.clear();
                    questionCount=0;
                    Intent intent = new Intent(getApplicationContext(), Score.class);
                    intent.putExtra("score",score);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        });

        option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long tEnd = System.currentTimeMillis();
                long tDelta = tEnd - tStart;
                double elapsedSeconds = tDelta / 1000.0;
                int totalAnswers = rightAnswers+wrongAnswers;
                double newStrikeRate = (strikeRate*totalAnswers+elapsedSeconds)/(totalAnswers+1);
                dataBaseRefUsers.child("strikeRate").setValue(newStrikeRate);
                if(option4.getText()==question.getRightAnswer().getStatement())
                {
                    option4.setText("Right Answer");
                    option4.setBackgroundResource(R.drawable.roundbuttongreen);
                    option4.setTextColor(Color.WHITE);
                    //option4.startAnimation(anim);
                    int scr = totalScore+10;
                    dataBaseRefUsers.child("totalScore").setValue(scr);
                    double acc = (accuracy*totalAnswers+100)/(totalAnswers+1);
                    dataBaseRefUsers.child("accuracy").setValue(acc);
                    score= score+10;
                    final int rightAns = rightAnswers+1;
                    dataBaseRefUsers.child("mRightAnswers").setValue(rightAns);
                }
                else
                {
                    option4.setText("Wrong Answer");
                    option4.setBackgroundResource(R.drawable.roundbuttonred);
                    option4.setTextColor(Color.WHITE);
                    option4.startAnimation(anim);
                    double acc = (accuracy*totalAnswers)/(totalAnswers+1);
                    dataBaseRefUsers.child("accuracy").setValue(acc);
                    final int wrongAns= wrongAnswers+1;
                    dataBaseRefUsers.child("mWrongAnswers").setValue(wrongAns);
                }
                questionCount++;
                if(questionCount<=quizLength) {
                    Intent intent = new Intent(getApplicationContext(), Ramayan.class);
                    intent.putExtra("category", categoryName);
                    intent.putIntegerArrayListExtra("questionList",(ArrayList<Integer>)questionList);
                    intent.putExtra("score",score);
                    intent.putExtra("questionCount",questionCount);
                    startActivity(intent);
                    finish();
                    return;
                }
                else
                {
                    questionList.clear();
                    questionCount=0;
                    Intent intent = new Intent(getApplicationContext(), Score.class);
                    intent.putExtra("score", score);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        });

    }

}
