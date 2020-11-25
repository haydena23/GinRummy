package com.example.ginrummy.GRActions;

import com.example.game.GameFramework.GamePlayer;
import com.example.ginrummy.Card;
import com.example.ginrummy.GRActions.GinRummyMoveAction;

public class GinRummyGroupAction extends GinRummyMoveAction {

    private Card[] groupTheseCard;
    private int amountOfCards;

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public GinRummyGroupAction(GamePlayer player, Card[] groupTheseCards, int amountOfCards) {
        super(player);
        this.groupTheseCard = groupTheseCards;
        this.amountOfCards = amountOfCards;
    }

    public boolean isGroup() {
        return true;
    }

    public Card[] getGroupTheseCard() {
        return this.groupTheseCard;
    }

    public int getAmountOfCards() {
        return this.amountOfCards;
    }
}
