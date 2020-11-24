package com.example.ginrummy;

import com.example.game.GameFramework.GamePlayer;

public class GinRummyDiscardAction extends GinRummyMoveAction {

    private static final String TAG = "GinRummyDiscardAction";

    private int whichCard;
    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public GinRummyDiscardAction(GamePlayer player, int whichCard) {
        super(player);
        this.whichCard = whichCard;
    }

    public boolean isDiscard() {
        return true;
    }

    public int getWhichCard() {
        return this.whichCard;
    }
}
