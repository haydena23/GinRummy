package com.example.ginrummy;

import com.example.game.GameFramework.GamePlayer;
import com.example.game.GameFramework.actionMessage.GameAction;

public class GinRummyDrawAction extends GameAction {
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
