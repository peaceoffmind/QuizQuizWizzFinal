package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class challengeFriend extends AppCompatActivity {
    private Button makeAChall, acceptAChall, acceptCode;
    private EditText gameId;
    private TextView challCreated;
    private GameHandler gameHandler;
    private game gameObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_friend);

        makeAChall = (Button) findViewById(R.id.makeachall);
        acceptAChall = (Button) findViewById(R.id.acceptachall);
        acceptCode = (Button) findViewById(R.id.accept);
        challCreated = (TextView) findViewById(R.id.challcreated);
        gameId = (EditText) findViewById(R.id.gameid);
        gameHandler = new GameHandler();


        makeAChall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameObj = gameHandler.makeGame(gameType.SHORTRUN);
                acceptAChall.setVisibility(View.GONE);
                makeAChall.setVisibility(View.GONE);
                challCreated.setVisibility(View.VISIBLE);
                challCreated.setText("Challenge Created !! \n\n CODE : "+gameObj.getGameId()+
                                                     " \n\nWaiting for other player to join...");
            }
        });

        acceptAChall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acceptAChall.setVisibility(View.GONE);
                makeAChall.setVisibility(View.GONE);
                gameId.setVisibility(View.VISIBLE);
                acceptCode.setVisibility(View.VISIBLE);
            }
        });

        acceptCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String gameID = gameId.getText().toString();
                gameHandler.acceptGame(gameID);
                if(gameHandler.getGameAccepted()==true)
                    Toast.makeText(challengeFriend.this, "Game is Accepted", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
