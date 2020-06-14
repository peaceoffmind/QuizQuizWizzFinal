package com.example.myapplication;

import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;

public class GameHandler {
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference gamesRef;
    private Boolean gameAccepted;
    //Constructor
    public GameHandler(){
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        gamesRef = database.getReference("games");
        gameAccepted = false;
    };

    public Boolean getGameAccepted() {
        return gameAccepted;
    }

    public void setGameAccepted(Boolean gameAccepted) {
        this.gameAccepted = gameAccepted;
    }

    public game makeGame(gameType type){
        //TODO: Check if this gameId is already present or not
        Random rand = new Random();
        int GameId =rand.nextInt(99999);
        String challengersId = mAuth.getCurrentUser().getUid();
        List<String> players = new ArrayList<String>();
        players.add(challengersId);
        List<String> categories = new ArrayList<String>();
        //TODO:: CHOICE TO ADD CATEGORIES BY USER
        categories.add("Ramayan");

        game gameObj =  new game(type,GameId,gameStatus.WAITINGFORPLAYERS,categories,players);
        gamesRef.child(String.valueOf(GameId)).setValue(gameObj);
        return gameObj;
    };

    public void acceptGame(final String gameId)
    {
        //Search the game in database

        gamesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    game gameObj = snap.getValue(game.class);
                    if(gameObj.getGameId() == Integer.valueOf(gameId) &&
                            gameObj.getGameStatus()== gameStatus.WAITINGFORPLAYERS)
                    {
                        gameObj.getPlayers().add(mAuth.getCurrentUser().getUid());
                        gameObj.setGameStatus(gameStatus.READYTOPLAY);
                        gamesRef.child(gameId).setValue(gameObj);
                        gameAccepted = true;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}