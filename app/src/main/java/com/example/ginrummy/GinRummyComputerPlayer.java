/**
 * GinRummyComputerPlayer.java - A type of Player which contains the
 * methods that the AI will use depending if it's smart or dumb.
 *
 * @author Jarren Calizo, Tony Hayden, Aron Manalang, Audrey Sauter
 * @version 25 Nov 2020
 */

package com.example.ginrummy;

import com.example.game.GameFramework.GameComputerPlayer;
import com.example.game.GameFramework.infoMessage.GameInfo;
import com.example.game.GameFramework.infoMessage.IllegalMoveInfo;
import com.example.game.GameFramework.infoMessage.NotYourTurnInfo;
import com.example.ginrummy.GRActions.GinRummyDiscardAction;
import com.example.ginrummy.GRActions.GinRummyDrawAction;
import com.example.ginrummy.GRActions.GinRummyDrawDiscardAction;

import java.util.Random;

public class GinRummyComputerPlayer extends GameComputerPlayer {
    //Instance variables
    GinRummyGameState state;
    private boolean isSmart;

    /**
     * Constructor for the GinRummyComputerPlayer extending from the game framework
     *
     * @param name the given AI name (e.g., "John")
     * @param isSmart Whether or not the AI is going to be smart or not
     */
    public GinRummyComputerPlayer(String name, boolean isSmart) {
        super(name);
        this.isSmart = isSmart;
    }

    /**
     * This is the method called when the Local Game sends information
     * to the Computer Player. This determines if the right
     * info was called, and if so, chooses an action to take based on
     * if the AI was smart or dumb.
     * @param info
     */
    @Override
    protected void receiveInfo(GameInfo info) {
        if(info instanceof IllegalMoveInfo || info instanceof NotYourTurnInfo) {
            return;
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
            // Put Smart AI code in here.
        }
    }
}
