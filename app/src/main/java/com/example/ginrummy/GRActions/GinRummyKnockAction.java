package com.example.ginrummy.GRActions;

import com.example.game.GameFramework.GamePlayer;

public class GinRummyKnockAction extends GinRummyMoveAction {
    /**
     * Constructor automatically given due to extending move action,
     * which defines who sent that action. This is for a knock
     *
     * @param player the player who created the action
     */
    public GinRummyKnockAction(GamePlayer player) {
        super(player);
    }
}
