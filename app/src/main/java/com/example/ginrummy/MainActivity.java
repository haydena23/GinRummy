package com.example.ginrummy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RummyGameState player = new RummyGameState();
        RummyGameState dumbAI = new RummyGameState(player);
        ControllerClass controller = new ControllerClass(player);

        /** Assigning button listeners */
        Button quitButton = findViewById(R.id.quitButton);
        quitButton.setOnClickListener(controller);

        Button doneButton = findViewById(R.id.doneButton);
        quitButton.setOnClickListener(controller);

        Button pullButton = findViewById(R.id.pullButton);
        quitButton.setOnClickListener(controller);

        Button discardButton = findViewById(R.id.discardButton);
        quitButton.setOnClickListener(controller);

        Button knockButton = findViewById(R.id.knockButton);
        quitButton.setOnClickListener(controller);
    }
}