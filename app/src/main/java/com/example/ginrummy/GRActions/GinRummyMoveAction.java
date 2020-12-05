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
