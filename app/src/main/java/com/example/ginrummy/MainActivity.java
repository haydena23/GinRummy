package com.example.ginrummy;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RummyGameState player = new RummyGameState();
        //I dont think the dumbAI is a new gameState.
        //RummyGameState dumbAI = new RummyGameState(player);

        Button discardButton = findViewById(R.id.discardButton);
        ImageButton discardedCard = findViewById(R.id.discardedCard);

        ImageView card0 = findViewById(R.id.card0);
        ImageView card1 = findViewById(R.id.card1);
        ImageView card2 = findViewById(R.id.card2);
        ImageView card3 = findViewById(R.id.card3);
        ImageView card4 = findViewById(R.id.card4);
        ImageView card5 = findViewById(R.id.card5);
        ImageView card6 = findViewById(R.id.card6);
        ImageView card7 = findViewById(R.id.card7);
        ImageView card8 = findViewById(R.id.card8);
        ImageView card9 = findViewById(R.id.card9);
        ImageView card10 = findViewById(R.id.card10);

        Controller controller = new Controller(player, discardButton, card0, card1, card2, card3, card4, card5, card6, card7, card8, card9, card10, discardedCard);

        /** Assigning button listeners */
        //Button startButton = findViewById(R.id.startButton);
        //startButton.setOnClickListener(this);

        Button quitButton = findViewById(R.id.quitButton);
        quitButton.setOnClickListener(this);

        Button knockButton = findViewById(R.id.knockButton);
        knockButton.setOnClickListener(controller);

        discardButton.setOnClickListener(controller);

        //ImageButton discardedCard = findViewById(R.id.discardedCard);
        discardedCard.setOnClickListener(controller);

        ImageButton drawPile = findViewById(R.id.drawPile);
        drawPile.setOnClickListener(controller);

        //Card Listeners
        card1.setOnClickListener(controller);
        card2.setOnClickListener(controller);
        card3.setOnClickListener(controller);
        card4.setOnClickListener(controller);
        card5.setOnClickListener(controller);
        card6.setOnClickListener(controller);
        card7.setOnClickListener(controller);
        card8.setOnClickListener(controller);
        card9.setOnClickListener(controller);
        card10.setOnClickListener(controller);
        card0.setOnClickListener(controller);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.startButton:
                setContentView(R.layout.activity_main);
                //Some kind of code to make new instance of gameState
            case R.id.quitButton:
                setContentView(R.layout.start_screen);
                break;
        }
    }
}