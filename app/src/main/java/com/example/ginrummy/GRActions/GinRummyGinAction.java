package com.example.ginrummy.GRActions;

import com.example.game.GameFramework.GamePlayer;

public class GinRummyGinAction extends GinRummyMoveAction {
    /**
     * Constructor automatically given due to extending move action,
     * which defines who sent that action. This is an attempt to gin
     *
     * @param player the player who created the action
     */
    public GinRummyGinAction(GamePlayer player) {
        super(player);
    }
}
