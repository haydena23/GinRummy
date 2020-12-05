package com.example.ginrummy.GRActions;

import com.example.game.GameFramework.GamePlayer;

public class GinRummyDrawDiscardAction extends GinRummyMoveAction {
    /**
     * Constructor automatically given due to extending move action,
     * which defines who sent that action. This is an attempt to draw discard
     *
     * @param player the player who created the action
     */
    public GinRummyDrawDiscardAction(GamePlayer player) {
        super(player);
    }
}
