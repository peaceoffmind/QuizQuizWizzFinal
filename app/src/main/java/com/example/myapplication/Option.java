package com.example.myapplication;

public class Option {
    private String statement;
    private Boolean isRightAnswer;

    public Option() {
    }

    public Option(String statement, Boolean isRightAnswer) {
        this.statement = statement;
        this.isRightAnswer = isRightAnswer;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public Boolean getRightAnswer() {
        return isRightAnswer;
    }

    public void setRightAnswer(Boolean rightAnswer) {
        isRightAnswer = rightAnswer;
    }
}
