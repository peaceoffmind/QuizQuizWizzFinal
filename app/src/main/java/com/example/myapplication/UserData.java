package com.example.myapplication;

import java.util.List;
import java.util.Map;

public class UserData {
    private int mRightAnswers;
    private int mWrongAnswers;
    private double mStrikeRate;
    private int mTotalScore;
    private double mAccuracy;
    Map<String,List<Integer>> mquestionListMap;

    public UserData() {
        //required to read data from database
    }

    public UserData(int mRightAnswers, int mWrongAnswers, double mStrikeRate, int mTotalScore, double mAccuracy, Map<String, List<Integer>> mquestionListMap) {
        this.mRightAnswers = mRightAnswers;
        this.mWrongAnswers = mWrongAnswers;
        this.mStrikeRate = mStrikeRate;
        this.mTotalScore = mTotalScore;
        this.mAccuracy = mAccuracy;
        this.mquestionListMap = mquestionListMap;
    }

    public double getStrikeRate() {
        return mStrikeRate;
    }

    public void setStrikeRate(double mStrikeRate) {
        this.mStrikeRate = mStrikeRate;
    }

    public int getTotalScore() {
        return mTotalScore;
    }

    public void setTotalScore(int mTotalScore) {
        this.mTotalScore = mTotalScore;
    }

    public double getAccuracy() {
        return mAccuracy;
    }

    public void setAccuracy(double mAccuracy) {
        this.mAccuracy = mAccuracy;
    }

    public int getmRightAnswers() {
        return mRightAnswers;
    }

    public void setmRightAnswers(int mRightAnswers) {
        this.mRightAnswers = mRightAnswers;
    }

    public int getmWrongAnswers() {
        return mWrongAnswers;
    }

    public void setmWrongAnswers(int mWrongAnswers) {
        this.mWrongAnswers = mWrongAnswers;
    }

    public Map<String, List<Integer>> getquestionListMap() {
        return mquestionListMap;
    }

    public void setquestionListMap(Map<String, List<Integer>> mquestionListMap) {
        this.mquestionListMap = mquestionListMap;
    }
}
