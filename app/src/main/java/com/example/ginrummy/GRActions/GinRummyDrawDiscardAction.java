package com.example.ginrummy.GRActions;

import com.example.game.GameFramework.GamePlayer;

public class GinRummyDrawDiscardAction extends GinRummyMoveAction {
    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public GinRummyDrawDiscardAction(GamePlayer player) {
        super(player);
    }

    public boolean isDrawDiscard() {
        return true;
    }
}
