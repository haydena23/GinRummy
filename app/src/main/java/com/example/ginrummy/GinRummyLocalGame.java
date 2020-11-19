package com.example.ginrummy;

import com.example.GameFramework.GamePlayer;
import com.example.GameFramework.LocalGame;
import com.example.GameFramework.actionMessage.GameAction;

public class GinRummyLocalGame extends LocalGame {
    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {

    }

    @Override
    protected boolean canMove(int playerIdx) {
        return false;
    }

    @Override
    protected String checkIfGameOver() {
        return null;
    }

    @Override
    protected boolean makeMove(GameAction action) {
        return false;
    }
}
