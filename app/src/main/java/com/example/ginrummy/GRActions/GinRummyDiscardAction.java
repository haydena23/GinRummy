/**
 * GinRummyGameState.java - Class for the sole purpose
 * of sending an action to the local class, which checks which action it was
 * and in this case, its a discard action.
 *
 * @author Jarren Calizo, Tony Hayden, Aron Manalang, Audrey Sauter
 * @version 25 Nov 2020
 */

package com.example.ginrummy.GRActions;

import com.example.game.GameFramework.GamePlayer;

public class GinRummyDiscardAction extends GinRummyMoveAction {

    private static final String TAG = "GinRummyDiscardAction";

    private int whichCard;
    /**
     * Constructor automatically given due to extending move action,
     * which defines who sent that action, and which card they want to discard
     *
     * @param player the player who created the action
     */
    public GinRummyDiscardAction(GamePlayer player, int whichCard) {
        super(player);
        this.whichCard = whichCard;
    }

    public int getWhichCard() {
        return this.whichCard;
    }
}
