package com.example.ginrummy;

import com.example.game.GameFramework.GamePlayer;

public class GinRummyGroupAction extends GinRummyMoveAction {

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public GinRummyGroupAction(GamePlayer player) {
        super(player);
    }

    public boolean isGroup() {
        return true;
    }
}
