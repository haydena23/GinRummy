/**
 * GinRummyGameState.java - Class for the sole purpose
 * of sending an action to the local class, which checks which action it was
 * and in this case, its an attempt to group cards.
 *
 * @author Jarren Calizo, Tony Hayden, Aron Manalang, Audrey Sauter
 * @version 25 Nov 2020
 */

package com.example.ginrummy.GRActions;

import com.example.game.GameFramework.GamePlayer;
import com.example.ginrummy.Card;
import com.example.ginrummy.GRActions.GinRummyMoveAction;

public class GinRummyGroupAction extends GinRummyMoveAction {

    private Card[] groupTheseCard;
    private int amountOfCards;

    /**
     * Constructor automatically given due to extending move action, which
     * defines who sent that action, which cards to check to group, and how many.
     * This is for an attempt to knock
     *
     * @param player the player who created the action
     */
    public GinRummyGroupAction(GamePlayer player, Card[] groupTheseCards, int amountOfCards) {
        super(player);
        this.groupTheseCard = groupTheseCards;
        this.amountOfCards = amountOfCards;
    }

    public Card[] getGroupTheseCard() {
        return this.groupTheseCard;
    }

    public int getAmountOfCards() {
        return this.amountOfCards;
    }
}
