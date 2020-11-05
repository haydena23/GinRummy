package com.example.ginrummy;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen);

        RummyGameState player = new RummyGameState();
        RummyGameState dumbAI = new RummyGameState(player);

        Controller controller = new Controller();

        /** Assigning button listeners */
        Button quitButton = findViewById(R.id.quitButton);
        quitButton.setOnClickListener(this);

        Button knockButton = findViewById(R.id.knockButton);
        knockButton.setOnClickListener(this);

        Button knock = findViewById(R.id.knockButton);


        ImageButton discardButton = findViewById(R.id.discardedCard);
        discardButton.setOnClickListener(controller);

        ImageButton drawPile = findViewById(R.id.drawPile);
        drawPile.setOnClickListener(controller);
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