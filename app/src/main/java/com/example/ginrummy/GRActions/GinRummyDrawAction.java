package com.example.ginrummy.GRActions;

import com.example.game.GameFramework.GamePlayer;

public class GinRummyDrawAction extends GinRummyMoveAction {
    /**
     * Constructor automatically given due to extending move action,
     * which defines who sent that action. This is an attempt to draw
     *
     * @param player the player who created the action
     */
    public GinRummyDrawAction(GamePlayer player) {
        super(player);
    }
}
