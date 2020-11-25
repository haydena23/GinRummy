package com.example.ginrummy.GRActions;

import com.example.game.GameFramework.GamePlayer;

public class GinRummyKnockAction extends GinRummyMoveAction {
    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public GinRummyKnockAction(GamePlayer player) {
        super(player);
    }

    public boolean isKnock() {
        return true;
    }
}
