/**
 * GinRummyGameState.java - Class for the sole purpose
 * of sending an action to the local class, which checks which action it was
 * and in this case, its an attempt to draw when there's no cards left.
 *
 * @author Jarren Calizo, Tony Hayden, Aron Manalang, Audrey Sauter
 * @version 25 Nov 2020
 */

package com.example.ginrummy.GRActions;

import com.example.game.GameFramework.GamePlayer;

public class GinRummyNoDrawsAction extends GinRummyMoveAction {
    /**
     * Constructor automatically given due to extending move action,
     * which defines who sent that action. This is to end the game
     *
     * @param player the player who created the action
     */
    public GinRummyNoDrawsAction(GamePlayer player) {
        super(player);
    }
}
