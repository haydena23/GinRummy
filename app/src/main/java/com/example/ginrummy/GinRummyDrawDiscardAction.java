package com.example.ginrummy;

import com.example.game.GameFramework.GamePlayer;
import com.example.game.GameFramework.actionMessage.GameAction;

public class GinRummyDrawDiscardAction extends GameAction {
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
