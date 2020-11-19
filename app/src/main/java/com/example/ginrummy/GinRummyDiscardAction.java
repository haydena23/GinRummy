package com.example.ginrummy;

import com.example.game.GameFramework.GamePlayer;
import com.example.game.GameFramework.actionMessage.GameAction;

public class GinRummyDiscardAction extends GameAction {
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
