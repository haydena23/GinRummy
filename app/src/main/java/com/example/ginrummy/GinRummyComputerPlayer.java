/**
 * GinRummyComputerPlayer.java - A type of Player which contains the
 * methods that the AI will use depending if it's smart or dumb.
 *
 * What needs to be done : Testing for runs when drawing both the draw pile
 * and the discarded pile. Testing for groups for each card so the AI knows
 * specifically what to discard (it doesn't discard potential sets or runs
 * due to it not being grouped yet)
 *
 *
 * @author Jarren Calizo, Tony Hayden, Aron Manalang, Audrey Sauter
 * @version 25 Nov 2020
 */

package com.example.ginrummy;

import android.graphics.Paint;

import com.example.game.GameFramework.GameComputerPlayer;
import com.example.game.GameFramework.infoMessage.GameInfo;
import com.example.ginrummy.GRActions.GinRummyDiscardAction;
import com.example.ginrummy.GRActions.GinRummyDrawAction;
import com.example.ginrummy.GRActions.GinRummyDrawDiscardAction;
import com.example.ginrummy.GRActions.GinRummyGroupAction;

import java.util.Random;

public class GinRummyComputerPlayer extends GameComputerPlayer {
    //Instance variables
    GinRummyGameState state;
    private boolean isSmart;
    private Card[] playerHand;

    /**
     * Constructor for the GinRummyComputerPlayer extending from the game framework
     *
     * @param name the given AI name (e.g., "John")
     * @param isSmart Whether or not the AI is going to be smart or not
     */
    public GinRummyComputerPlayer(String name, boolean isSmart) {
        super(name);
        this.isSmart = isSmart;
    }

    /**
     * This is the method called when the Local Game sends information
     * to the Computer Player. This determines if the right
     * info was called, and if so, chooses an action to take based on
     * if the AI was smart or dumb.
     * @param info
     */
    @Override
    protected void receiveInfo(GameInfo info) {
        // This updates the state if the right info was sent.
        if (!(info instanceof GinRummyGameState)) {
            return;
        } else {
            state = (GinRummyGameState) info;
            playerHand = state.getPlayer2Cards();
        }

        if (!isSmart) { // DumbAI Algorithm
            // Checks if it's the computer's turn.
            if (state.getToPlay() == playerNum) {

                // Randomly draws the discard or drawPile
                Random rand = new Random();
                if (rand.nextBoolean()) {
                    game.sendAction(new GinRummyDrawAction(this));
                } else {
                    game.sendAction(new GinRummyDrawDiscardAction(this));
                }

                // Randomly discards a card in its hand.
                game.sendAction(new GinRummyDiscardAction(this,
                        rand.nextInt(11)));
            }

        } else { // SmartAI Algorithm
            if (state.getToPlay() == playerNum) {
                Card discardedCard = state.getDiscardedCard();
                Card drawCard;
                Card[] discardedCardMatches = new Card[11];
                int matchesCounters = 0;
                int discardedCardNumber = discardedCard.getNumber();
                int drawCardNumber = 0;

                loopPossibleRun(discardedCard);
                loopPossibleSet(discardedCard);

                if (discardedCard.getIsPossibleRun() || discardedCard.getIsPossibleSet()) {
                    game.sendAction(new GinRummyDrawDiscardAction(this));

                    // If the discarded card is in a possible set, check the hand and group if possible.
                    if (discardedCard.getIsPossibleSet()) {
                        //This checks for sets for the drawn discarded Card
                        for (int x = 0; x < playerHand.length; x++) {
                            if (playerHand[x].getNumber() == discardedCardNumber) {
                                discardedCardMatches[matchesCounters] = playerHand[x];
                                matchesCounters++;
                            }
                            discardedCardMatches[matchesCounters] = discardedCard;
                        }
                        if (matchesCounters > 2) {
                            game.sendAction(new GinRummyGroupAction(
                                    this, discardedCardMatches, matchesCounters));
                        }
                    }
                    if (discardedCard.getIsPossibleRun()) {
                        //Sorting algorithm
                        Card[] updatedHand = new Card[11];
                        int counter = 0;
                        for (int y = 0; y < 52; y++) {
                            for (int x = 0; x < playerHand.length; x++) {
                                if (playerHand[x].getPosition() == y) {
                                    updatedHand[counter] = playerHand[x];
                                    counter++;
                                }
                            }
                        }
                    }
                } else {
                    game.sendAction(new GinRummyDrawAction(this));
                    drawCard = playerHand[10];
                    drawCardNumber = drawCard.getNumber();
                    Card[] drawCardMatches = new Card[11];

                    if (drawCard.getIsPossibleSet()) {
                        for (int x = 0; x < playerHand.length; x++) {
                            if (playerHand[x].getNumber() == drawCardNumber) {
                                playerHand[matchesCounters] = playerHand[x];
                                matchesCounters++;
                            }
                            drawCardMatches[matchesCounters] = drawCard;
                        }
                        if (matchesCounters > 2) {
                            game.sendAction(new GinRummyGroupAction(
                                    this, drawCardMatches, matchesCounters));
                        }
                    } else {

                    }
                }

                Card discardThis = new Card(0, "Trash");
                int discardThisElement = -1;

                for (int x = 0; x < playerHand.length; x++) {
                    if (!(playerHand[x].getIsPossibleSet() && (!(playerHand[x].getIsPossibleRun())))) {
                        if (discardThis.getNumber() < playerHand[x].getNumber()) {
                            discardThis = playerHand[x];
                            discardThisElement = x;
                        }
                    }
                }

                if (discardThis.getNumber() == 0) {

                    Card highestPairedCard = new Card(0, "Trash");
                    int highestPairedElement = 0;
                    for (int x = 0; x < playerHand.length; x++) {
                        if (playerHand[x].getNumber() > highestPairedCard.getNumber()) {
                            if (!(playerHand[x].getIsInRun() || playerHand[x].getIsInSet())) {
                                highestPairedCard = playerHand[x];
                                highestPairedElement = x;
                            }
                        }
                    }

                    for (int x = 0; x < playerHand.length; x++) {
                        if (playerHand[x].getNumber() == highestPairedCard.getNumber()) {
                            playerHand[x].setIsPossibleSet(false);
                        }
                    }

                    game.sendAction(new GinRummyDiscardAction(this, highestPairedElement));
                } else {
                    game.sendAction(new GinRummyDiscardAction(this, discardThisElement));
                }
            }
        }
    }


    public void loopPossibleRun(Card card) {
        for (int i = 0; i < playerHand.length; i++) {
            identifyPossibleRun(card, playerHand[i]);
        }
    }

    public void loopPossibleSet(Card card) {
        for (int i = 0; i < playerHand.length; i++) {
            identifyPossibleSet(card, playerHand[i]);
        }
    }

    public void identifyPossibleSet(Card card1, Card card2) {
        if (card1.getNumber() == card2.getNumber()) {
            card1.setIsPossibleSet(true);
            card2.setIsPossibleSet(true);
        }
    }

    /**
     * Method to identify if there is a possible run between cards in the AI hand
     *
     * @param card1 Card 1 to check
     * @param card2 Card 2 to check
     */
    public void identifyPossibleRun(Card card1, Card card2) {
        if (card1.getSuit().equals(card2.getSuit())) {
            if (card1.getSuit().equals("Hearts")) {
                if(card1.getPosition() == 1) {
                    if(card2.getPosition() == card1.getPosition() + 1) {
                        card1.setIsPossibleRun(true);
                        card2.setIsPossibleRun(true);
                        return;
                    }
                }
                if(card2.getPosition() == 1) {
                    if(card1.getPosition() == card2.getPosition() + 1) {
                        card1.setIsPossibleRun(true);
                        card2.setIsPossibleRun(true);
                        return;
                    }
                }
                if(card1.getPosition() == 13) {
                    if(card2.getPosition() == card1.getPosition() - 1) {
                        card1.setIsPossibleRun(true);
                        card2.setIsPossibleRun(true);
                        return;
                    }
                }
                if(card2.getPosition() == 13) {
                    if(card1.getPosition() == card2.getPosition() - 1) {
                        card1.setIsPossibleRun(true);
                        card2.setIsPossibleRun(true);
                        return;
                    }
                }
                else {
                    if ((card1.getPosition() == card2.getPosition() - 1) ||
                            card1.getPosition() == card2.getPosition() + 1) {
                        card1.setIsPossibleRun(true);
                        card2.setIsPossibleRun(true);
                        return;
                    }
                    else {
                        card1.setIsPossibleRun(false);
                        card2.setIsPossibleRun(false);
                    }
                }
            }

            if (card1.getSuit().equals("Diamonds")) {
                if(card1.getPosition() == 14) {
                    if(card2.getPosition() == card1.getPosition() + 1) {
                        card1.setIsPossibleRun(true);
                        card2.setIsPossibleRun(true);
                        return;
                    }
                }
                if(card2.getPosition() == 14) {
                    if(card1.getPosition() == card2.getPosition() + 1) {
                        card1.setIsPossibleRun(true);
                        card2.setIsPossibleRun(true);
                        return;
                    }
                }
                if(card1.getPosition() == 26) {
                    if(card2.getPosition() == card1.getPosition() - 1) {
                        card1.setIsPossibleRun(true);
                        card2.setIsPossibleRun(true);
                        return;
                    }
                }
                if(card2.getPosition() == 26) {
                    if(card1.getPosition() == card2.getPosition() - 1) {
                        card1.setIsPossibleRun(true);
                        card2.setIsPossibleRun(true);
                        return;
                    }
                }
                else {
                    if ((card1.getPosition() == card2.getPosition() - 1) ||
                            card1.getPosition() == card2.getPosition() + 1) {
                        card1.setIsPossibleRun(true);
                        card2.setIsPossibleRun(true);
                        return;
                    }
                    else {
                        card1.setIsPossibleRun(false);
                        card2.setIsPossibleRun(false);
                    }
                }
            }

            if (card1.getSuit().equals("Spades")) {
                if(card1.getPosition() == 27) {
                    if(card2.getPosition() == card1.getPosition() + 1) {
                        card1.setIsPossibleRun(true);
                        card2.setIsPossibleRun(true);
                        return;
                    }
                }
                if(card2.getPosition() == 27) {
                    if(card1.getPosition() == card2.getPosition() + 1) {
                        card1.setIsPossibleRun(true);
                        card2.setIsPossibleRun(true);
                        return;
                    }
                }
                if(card1.getPosition() == 39) {
                    if(card2.getPosition() == card1.getPosition() - 1) {
                        card1.setIsPossibleRun(true);
                        card2.setIsPossibleRun(true);
                        return;
                    }
                }
                if(card2.getPosition() == 39) {
                    if(card1.getPosition() == card2.getPosition() - 1) {
                        card1.setIsPossibleRun(true);
                        card2.setIsPossibleRun(true);
                        return;
                    }
                }
                else {
                    if ((card1.getPosition() == card2.getPosition() - 1) ||
                            card1.getPosition() == card2.getPosition() + 1) {
                        card1.setIsPossibleRun(true);
                        card2.setIsPossibleRun(true);
                        return;
                    }
                    else {
                        card1.setIsPossibleRun(false);
                        card2.setIsPossibleRun(false);
                    }
                }
            }

            if (card1.getSuit().equals("Clubs")) {
                if(card1.getPosition() == 40) {
                    if(card2.getPosition() == card1.getPosition() + 1) {
                        card1.setIsPossibleRun(true);
                        card2.setIsPossibleRun(true);
                        return;
                    }
                }
                if(card2.getPosition() == 40) {
                    if(card1.getPosition() == card2.getPosition() + 1) {
                        card1.setIsPossibleRun(true);
                        card2.setIsPossibleRun(true);
                        return;
                    }
                }
                if(card1.getPosition() == 52) {
                    if(card2.getPosition() == card1.getPosition() - 1) {
                        card1.setIsPossibleRun(true);
                        card2.setIsPossibleRun(true);
                        return;
                    }
                }
                if(card2.getPosition() == 52) {
                    if(card1.getPosition() == card2.getPosition() - 1) {
                        card1.setIsPossibleRun(true);
                        card2.setIsPossibleRun(true);
                        return;
                    }
                }
                else {
                    if ((card1.getPosition() == card2.getPosition() - 1) ||
                            card1.getPosition() == card2.getPosition() + 1) {
                        card1.setIsPossibleRun(true);
                        card2.setIsPossibleRun(true);
                        return;
                    }
                    else {
                        card1.setIsPossibleRun(false);
                        card2.setIsPossibleRun(false);
                    }
                }
            }
        }
    }
}
