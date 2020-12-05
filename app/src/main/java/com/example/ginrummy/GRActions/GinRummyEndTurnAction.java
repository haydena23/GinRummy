package com.example.ginrummy.GRActions;

import com.example.game.GameFramework.GamePlayer;

public class GinRummyEndTurnAction extends GinRummyMoveAction {
    /**
     * Constructor automatically given due to extending move action,
     * which defines who sent that action. This is an attempt to end turn.
     *
     * @param player the player who created the action
     */
    public GinRummyEndTurnAction(GamePlayer player) {
        super(player);
    }
}
