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

        ImageView card1 = findViewById(R.id.card1);

        Controller controller = new Controller(player, discardButton, card1);

        /** Assigning button listeners */
        //Button startButton = findViewById(R.id.startButton);
        //startButton.setOnClickListener(this);

        Button quitButton = findViewById(R.id.quitButton);
        quitButton.setOnClickListener(this);

        Button knockButton = findViewById(R.id.knockButton);
        knockButton.setOnClickListener(controller);

        discardButton.setOnClickListener(controller);

        ImageButton discardedCard = findViewById(R.id.discardedCard);
        discardedCard.setOnClickListener(controller);

        ImageButton drawPile = findViewById(R.id.drawPile);
        drawPile.setOnClickListener(controller);

        //Card Listeners
        card1.setOnClickListener(controller);

        ImageView card2 = findViewById(R.id.card2);
        card2.setOnClickListener(controller);

        ImageView card3 = findViewById(R.id.card3);
        card3.setOnClickListener(controller);

        ImageView card4 = findViewById(R.id.card4);
        card4.setOnClickListener(controller);

        ImageView card5 = findViewById(R.id.card5);
        card5.setOnClickListener(controller);

        ImageView card6 = findViewById(R.id.card6);
        card6.setOnClickListener(controller);

        ImageView card7 = findViewById(R.id.card7);
        card7.setOnClickListener(controller);

        ImageView card8 = findViewById(R.id.card8);
        card8.setOnClickListener(controller);

        ImageView card9 = findViewById(R.id.card9);
        card9.setOnClickListener(controller);

        ImageView card10 = findViewById(R.id.card10);
        card10.setOnClickListener(controller);

        ImageView card11 = findViewById(R.id.card0);
        card11.setOnClickListener(controller);
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