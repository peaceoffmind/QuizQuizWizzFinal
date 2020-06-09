package com.example.myapplication;

public class Question {
    String statement;
    Option option1;
    Option option2;
    Option option3;
    Option option4;
    int questionNumber;

    public Question(String statement, Option option1, Option option2, Option option3, Option option4,int questionNumber) {
        this.statement = statement;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.questionNumber = questionNumber;
    }

    public Question() {
    }

    public String getStatement() {
        return statement;
    }

    public Option getOption1() {
        return option1;
    }

    public Option getOption2() {
        return option2;
    }

    public Option getOption3() {
        return option3;
    }

    public Option getOption4() {
        return option4;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }


    public void setStatement(String statement) {
        this.statement = statement;
    }

    public void setOption1(Option option1) {
        this.option1 = option1;
    }

    public void setOption2(Option option2) {
        this.option2 = option2;
    }

    public void setOption3(Option option3) {
        this.option3 = option3;
    }

    public void setOption4(Option option4) {
        this.option4 = option4;
    }

    public void setQuestionNumber() {
        this.questionNumber = questionNumber;
    }

   public Option getRightAnswer() {
        if(option1.getRightAnswer()== true)
            return option1;
        else if(option2.getRightAnswer() == true)
            return option2;
        else if(option3.getRightAnswer() == true)
            return option3;
        else
            return option4;
   }
}
