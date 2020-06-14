package com.example.myapplication;

import java.util.List;

enum gameStatus {
    INACTIVE,
    ACTIVE,
    WAITINGFORPLAYERS,
    READYTOPLAY,
    INPROGRESS
}

enum gameType {
    SHORTRUN,
    LONGRUN,
    MARATHON
}

public class game {
    private gameType mGameType;
    private int mGameId;
    private gameStatus mGameStatus;
    private List<String> mGameCategories;
    private List<String> mPlayers;

    public game ()
    {
        //to read data from database
    }

    public game(gameType mGameType, int mGameId, gameStatus mGameStatus,
                        List<String> mGameCategories, List<String> mPlayers) {
        this.mGameType = mGameType;
        this.mGameId = mGameId;
        this.mGameStatus = mGameStatus;
        this.mGameCategories = mGameCategories;
        this.mPlayers = mPlayers;
    }
    //TODO: destructor of the game, always delete inactive games
    public List<String> getGameCategories() {
        return mGameCategories;
    }

    public void setGameCategories(List<String> mGameCategories) {
        this.mGameCategories = mGameCategories;
    }

    public gameType getGameType() {
        return mGameType;
    }

    public void setGameType(gameType mGameType) {
        this.mGameType = mGameType;
    }

    public int getGameId() {
        return mGameId;
    }

    public void setGameId(int mGameId) {
        this.mGameId = mGameId;
    }

    public gameStatus getGameStatus() {
        return mGameStatus;
    }

    public void setGameStatus(gameStatus mGameStatus) {
        this.mGameStatus = mGameStatus;
    }

    public List<String> getPlayers() {
        return mPlayers;
    }

    public void setPlayers(List<String> mPlayers) {
        this.mPlayers = mPlayers;
    }
}
