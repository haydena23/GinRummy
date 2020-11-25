package com.example.ginrummy;

import com.example.game.GameFramework.GameComputerPlayer;
import com.example.game.GameFramework.infoMessage.GameInfo;

public class GinRummySmartAI extends GameComputerPlayer {
    GinRummyGameState state;
    private Card card1;
    private Card card2;
    private Card[] cardChecker;

    /**
     * constructor
     *
     * @param name the player's name (e.g., "John")
     */
    public GinRummySmartAI(String name) {
        super(name);
    }

    public boolean setPossibleSet (Card card1, Card card2) {
        return false;
    }
    @Override
    protected void receiveInfo(GameInfo info) {
        state = (GinRummyGameState) info;
        state.getPlayer1Cards();
        cardChecker = state.getPlayer2Cards();
    }
}
