package com.example.ginrummy;

import android.view.View;

public class ControllerClass implements View.OnClickListener{

    private RummyGameState currentGS;

    public ControllerClass(RummyGameState gameStateRef) {
        currentGS = gameStateRef;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.quitButton:
                //currentGS.quitGame();
                System.exit(0);
                break;
            case R.id.doneButton:
                break;
            case R.id.pullButton:
                break;
            case R.id.discardButton:
                break;
            case R.id.knockButton:
                break;
        }
    }
}
