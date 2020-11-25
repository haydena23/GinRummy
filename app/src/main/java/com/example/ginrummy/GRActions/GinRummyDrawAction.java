package com.example.ginrummy.GRActions;

import com.example.game.GameFramework.GamePlayer;

public class GinRummyDrawAction extends GinRummyMoveAction {
    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public GinRummyDrawAction(GamePlayer player) {
        super(player);
    }

    public boolean isDraw() {
        return true;
    }
}
