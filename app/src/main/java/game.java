import com.example.myapplication.UserData;

import java.util.List;

enum gameStatus {
    INACTIVE,
    WAITINGFORPLAYERS,
    INPROGRESS
}

enum gameType {
    SHORTRUN,
    LONGRUN,
    MARATHON
}

public class game {
    private gameType mGameType;
    private int mGameCode;
    private gameStatus mGameStatus;
    private List<UserData> mPlayers;

    public game(gameType mGameType, int mGameCode, gameStatus mGameStatus, List<UserData> mPlayers) {
        this.mGameType = mGameType;
        this.mGameCode = mGameCode;
        this.mGameStatus = mGameStatus;
        this.mPlayers = mPlayers;
    }

    public gameType getGameType() {
        return mGameType;
    }

    public void setGameType(gameType mGameType) {
        this.mGameType = mGameType;
    }

    public int getGameCode() {
        return mGameCode;
    }

    public void setGameCode(int mGameCode) {
        this.mGameCode = mGameCode;
    }

    public gameStatus getGameStatus() {
        return mGameStatus;
    }

    public void setGameStatus(gameStatus mGameStatus) {
        this.mGameStatus = mGameStatus;
    }

    public List<UserData> getPlayers() {
        return mPlayers;
    }

    public void setPlayers(List<UserData> mPlayers) {
        this.mPlayers = mPlayers;
    }
}
