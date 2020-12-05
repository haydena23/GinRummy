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
