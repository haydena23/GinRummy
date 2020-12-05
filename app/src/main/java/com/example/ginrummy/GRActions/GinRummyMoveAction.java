/**
 * GinRummyGameState.java - Class for the sole purpose
 * of sending an action to the local class, which checks which action it was
 * and in this case, its a general move action which is extended
 * by every other action.
 *
 * @author Jarren Calizo, Tony Hayden, Aron Manalang, Audrey Sauter
 * @version 25 Nov 2020
 */

package com.example.ginrummy.GRActions;

import com.example.game.GameFramework.GamePlayer;
import com.example.game.GameFramework.actionMessage.GameAction;

public class GinRummyMoveAction extends GameAction {
    /**
     * Constructor automatically given due to extending move action,
     * which defines who sent that action. This is a general class
     *
     * @param player the player who created the action
     */
    public GinRummyMoveAction(GamePlayer player) {
        super(player);
    }
}
