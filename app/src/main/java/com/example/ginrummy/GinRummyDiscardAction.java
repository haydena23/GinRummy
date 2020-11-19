package com.example.ginrummy;

import com.example.game.GameFramework.GamePlayer;

public class GinRummyDiscardAction extends GinRummyMoveAction {
    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public GinRummyDiscardAction(GamePlayer player) {
        super(player);
    }

    public boolean isDiscard() {
        return true;
    }
}
