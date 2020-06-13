package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class signup extends AppCompatActivity {
    private TextView mEmail, mPassword, mDisplayName;
    private Button mSignUp;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;
    private String[] categoryArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mEmail = (TextView) findViewById(R.id.email);
        mPassword = (TextView) findViewById(R.id.password);
        mDisplayName = (TextView)findViewById(R.id.displayName);
        mSignUp = (Button)findViewById(R.id.signup);
        mAuth = FirebaseAuth.getInstance();

        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user != null)
                {
                    Intent intent = new Intent(signup.this,home.class);
                    startActivity( intent);
                    finish();
                    return;
                }
            }
        };

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = mEmail.getText().toString();
                final String password = mPassword.getText().toString();
                final String displayName = mDisplayName.getText().toString();
                if(email.equals("") || password.equals("")|| displayName.equals(""))
                    Toast.makeText(signup.this,"Please enter the required details and try again.", Toast.LENGTH_SHORT).show();
                else {

                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(signup.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(signup.this, "Sign Up Error", Toast.LENGTH_SHORT).show();
                            } else {
                                 String user_id = mAuth.getCurrentUser().getUid();
                                 Map<String,List<Integer>> questionListMap = new HashMap<String,List<Integer>>();
                                 categoryArray = getResources().getStringArray(R.array.questionCategory);
                                 for(String category : categoryArray)
                                 {
                                     List<Integer> list = new ArrayList<Integer>();
                                     list.add(0);
                                     questionListMap.put(category,list);
                                 }
                                 UserData userData = new UserData(displayName,0,0,0,0,0,questionListMap);
                                // Write a message to the database
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(displayName).build();

                                mAuth.getCurrentUser().updateProfile(profileUpdates)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(signup.this, "User Registration Done", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("users").child(user_id);
                                myRef.setValue(userData);
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthStateListener);
    }
}
