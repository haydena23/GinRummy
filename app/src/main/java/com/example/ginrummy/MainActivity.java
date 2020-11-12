package com.example.ginrummy;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
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

        Button discardButton = findViewById(R.id.discardButton);
        Button quitButton = findViewById(R.id.quitButton);
        Button knockButton = findViewById(R.id.knockButton);
        Button groupButton = findViewById(R.id.groupButton);

        ImageView discardedCard = findViewById(R.id.discardedCard);
        ImageView drawPile = findViewById(R.id.drawPile);

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

        Controller controller = new Controller(player, discardButton, card0, card1,
                card2, card3, card4, card5, card6, card7, card8, card9, card10,
                drawPile, groupButton, discardedCard);

        quitButton.setOnClickListener(this);
        knockButton.setOnClickListener(controller);
        discardButton.setOnClickListener(controller);
        groupButton.setOnClickListener(controller);

        discardedCard.setOnClickListener(controller);

        drawPile.setOnClickListener(controller);

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
                break;
            case R.id.quitButton:
                setContentView(R.layout.start_screen);
                break;
        }
        
    }
}