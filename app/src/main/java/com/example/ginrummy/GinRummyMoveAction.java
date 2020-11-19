package com.example.ginrummy;

import com.example.game.GameFramework.GamePlayer;
import com.example.game.GameFramework.actionMessage.GameAction;

public class GinRummyMoveAction extends GameAction {
    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public GinRummyMoveAction(GamePlayer player) {
        super(player);
    }

    public boolean isDiscard() {
        return true;
    }

    public boolean isDraw() {
        return true;
    }

    public boolean isDrawDiscard() {
        return true;
    }
}
