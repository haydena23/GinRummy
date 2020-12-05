package com.example.ginrummy.GRActions;

import com.example.game.GameFramework.GamePlayer;

public class GinRummyDiscardAction extends GinRummyMoveAction {

    private static final String TAG = "GinRummyDiscardAction";

    private int whichCard;
    /**
     * Constructor automatically given due to extending move action,
     * which defines who sent that action, and which card they want to discard
     *
     * @param player the player who created the action
     */
    public GinRummyDiscardAction(GamePlayer player, int whichCard) {
        super(player);
        this.whichCard = whichCard;
    }

    public int getWhichCard() {
        return this.whichCard;
    }
}
