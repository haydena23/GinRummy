package com.example.ginrummy.GRActions;

import com.example.game.GameFramework.GamePlayer;

public class GinRummyGinAction extends GinRummyMoveAction {
    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public GinRummyGinAction(GamePlayer player) {
        super(player);
    }

    public boolean isGin() {
        return true;
    }
}
