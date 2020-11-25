package com.example.ginrummy;
/**
 * ScoreView.java - utilized to display the current score of the game
 *
 * @author Jarren Calizo, Tony Hayden, Aron Manalang, Audrey Sauter
 * @version 12 Nov 2020
 */

import com.example.game.GameFramework.GameComputerPlayer;
import com.example.game.GameFramework.infoMessage.GameInfo;
import com.example.game.GameFramework.infoMessage.IllegalMoveInfo;
import com.example.game.GameFramework.infoMessage.NotYourTurnInfo;
import com.example.ginrummy.GRActions.GinRummyDiscardAction;
import com.example.ginrummy.GRActions.GinRummyDrawAction;
import com.example.ginrummy.GRActions.GinRummyDrawDiscardAction;

import java.util.Random;
import android.graphics.Color;

public class GinRummyComputerPlayer extends GameComputerPlayer {
    GinRummyGameState state;

    private boolean isSmart;
    /**
     * constructor
     *
     * @param name the player's name (e.g., "John")
     */
    public GinRummyComputerPlayer(String name, boolean isSmart) {
        super(name);
        this.isSmart = isSmart;
    }

    @Override
    protected void receiveInfo(GameInfo info) {
        if(info instanceof IllegalMoveInfo || info instanceof NotYourTurnInfo) {
        }
        else if (!(info instanceof GinRummyGameState)) {
            return;
        } else {
            state = (GinRummyGameState) info;
        }

        if (!isSmart) {
            if (state.getToPlay() == 1) {
                Random rand  = new Random();
                if (rand.nextBoolean()) {
                    game.sendAction(new GinRummyDrawAction(this));
                } else {
                    game.sendAction(new GinRummyDrawDiscardAction(this));
                }

                game.sendAction(new GinRummyDiscardAction(this,
                        rand.nextInt(11)));
                state.setToPlay(0);
            }
        } else {
            //if smart
        }
    }
}
