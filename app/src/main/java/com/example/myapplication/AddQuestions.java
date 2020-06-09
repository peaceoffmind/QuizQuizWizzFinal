package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddQuestions extends AppCompatActivity  {
    private EditText statement, option1,option2,option3,option4;
    private EditText newCategory;
    private RadioButton rightAnswer;
    private Button addQuestionButton;
    private RadioGroup radioGroup;
    private Spinner category;
    private String categoryName;
    private int TotalQuestions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_questions);

        category = findViewById(R.id.category);
        statement = findViewById(R.id.statement);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        radioGroup = findViewById(R.id.radiogroup);
        addQuestionButton = findViewById(R.id.addbutton);
        newCategory = findViewById(R.id.newcategory);
        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.questionCategory,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        category.setAdapter(adapter);
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                categoryName = adapterView.getItemAtPosition(i).toString();
                if(categoryName.equals("Add New Category")) {
                    newCategory.setVisibility(View.VISIBLE);
                    TotalQuestions=0;
                }
                else {
                    //Reading total questions in database
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("questions").child(categoryName).child("total questions");
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            TotalQuestions = (int) dataSnapshot.getValue(int.class);
                            //Toast.makeText(getApplicationContext(),String.valueOf(TotalQuestions),Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        addQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String ques = statement.getText().toString();
                final String op1 = option1.getText().toString();
                final String op2 = option2.getText().toString();
                final String op3 = option3.getText().toString();
                final String op4 = option4.getText().toString();

                int selectedId = radioGroup.getCheckedRadioButtonId();
                final String rightAnswerText = findCheckedRadioButtonText(selectedId);
                if(categoryName.equals("Add New Category"))
                {
                    categoryName = newCategory.getText().toString();
                }


                if (ques.equals("") || op1.equals("") || op2.equals("") || op2.equals("") || op4.equals("") || categoryName.equals("") || rightAnswerText.equals(""))
                    Toast.makeText(getApplicationContext(),"Please enter all the details before adding the question.", Toast.LENGTH_SHORT).show();
                else {
                    Boolean isOp1True = rightAnswerText.equals("1");
                    Boolean isOp2True = rightAnswerText.equals("2");
                    Boolean isOp3True = rightAnswerText.equals("3");
                    Boolean isOp4True = rightAnswerText.equals("4");
                    Option option_1 = new Option(op1, isOp1True);
                    Option option_2 = new Option(op2, isOp2True);
                    Option option_3 = new Option(op3, isOp3True);
                    Option option_4 = new Option(op4, isOp4True);
                    final int questionNumber = TotalQuestions+1;
                    Question question = new Question(ques, option_1, option_2, option_3, option_4,questionNumber);
                    //Adding to DataBase
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("questions").child(categoryName);
                    myRef.child(String.valueOf(questionNumber)).setValue(question);
                    myRef.child("total questions").setValue(questionNumber);
                    Toast.makeText(getApplicationContext(), "Question added to database", Toast.LENGTH_SHORT).show();
                    statement.setText("");
                    option1.setText("");
                    option2.setText("");
                    option3.setText("");
                    option4.setText("");
                    newCategory.setText("");
                    newCategory.setVisibility(View.GONE);
                    radioGroup.clearCheck();
                }
            }
        });

    }

    private void addNewCategoryToDataBase(String categoryName) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("questions").child(newCategory.getText().toString());
        myRef.child("total questions").setValue(0);
    }

    private String findCheckedRadioButtonText(int selectedId) {

        if(selectedId==-1)
            return "";

        switch(selectedId){

            case R.id.radio1 :
                return "1";
            case R.id.radio2 :
                return "2";
            case R.id.radio3 :
                return "3";
            case R.id.radio4 :
                return "4";
        }
        return "";
    }

}
