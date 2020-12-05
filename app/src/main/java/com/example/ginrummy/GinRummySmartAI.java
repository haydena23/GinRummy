/**
 * GinRummySmartAI - Class for the SmartAI in the game, which contains
 * a more complex algorithm for the AI to play by
 *
 * @author Jarren Calizo, Tony Hayden, Aron Manalang, Audrey Sauter
 * @version 25 Nov 2020
 */

package com.example.ginrummy;

import com.example.game.GameFramework.GameComputerPlayer;
import com.example.game.GameFramework.infoMessage.GameInfo;

public class GinRummySmartAI extends GameComputerPlayer {
    //Instance variables
    GinRummyGameState state;
    GinRummyLocalGame decide;
    private Card[] cardChecker;

    /**
     * Constructor for the SmartAI extending from the game framework     *
     * @param name the player's name (e.g., "John")
     */
    public GinRummySmartAI(String name) {
        super(name);
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
                        card1.setIsPossibleSet(true);
                        card2.setIsPossibleSet(true);
                    }
                }
                if(card2.getPosition() == 1) {
                    if(card1.getPosition() == card2.getPosition() + 1) {
                        card1.setIsPossibleSet(true);
                        card2.setIsPossibleSet(true);
                    }
                }
                if(card1.getPosition() == 13) {
                    if(card2.getPosition() == card1.getPosition() - 1) {
                        card1.setIsPossibleSet(true);
                        card2.setIsPossibleSet(true);
                    }
                }
                if(card2.getPosition() == 13) {
                    if(card1.getPosition() == card2.getPosition() - 1) {
                        card1.setIsPossibleSet(true);
                        card2.setIsPossibleSet(true);
                    }
                }
                else {
                    if ((card1.getPosition() == card2.getPosition() - 1) ||
                    card1.getPosition() == card2.getPosition() + 1) {
                        card1.setIsPossibleSet(true);
                        card2.setIsPossibleSet(true);
                    }
                    else {
                        card1.setIsPossibleSet(false);
                        card2.setIsPossibleSet(false);
                    }
                }
            }

            if (card1.getSuit().equals("Diamonds")) {
                if(card1.getPosition() == 14) {
                    if(card2.getPosition() == card1.getPosition() + 1) {
                        card1.setIsPossibleSet(true);
                        card2.setIsPossibleSet(true);
                    }
                }
                if(card2.getPosition() == 14) {
                    if(card1.getPosition() == card2.getPosition() + 1) {
                        card1.setIsPossibleSet(true);
                        card2.setIsPossibleSet(true);
                    }
                }
                if(card1.getPosition() == 26) {
                    if(card2.getPosition() == card1.getPosition() - 1) {
                        card1.setIsPossibleSet(true);
                        card2.setIsPossibleSet(true);
                    }
                }
                if(card2.getPosition() == 26) {
                    if(card1.getPosition() == card2.getPosition() - 1) {
                        card1.setIsPossibleSet(true);
                        card2.setIsPossibleSet(true);
                    }
                }
                else {
                    if ((card1.getPosition() == card2.getPosition() - 1) ||
                            card1.getPosition() == card2.getPosition() + 1) {
                        card1.setIsPossibleSet(true);
                        card2.setIsPossibleSet(true);
                    }
                    else {
                        card1.setIsPossibleSet(false);
                        card2.setIsPossibleSet(false);
                    }
                }
            }

            if (card1.getSuit().equals("Spades")) {
                if(card1.getPosition() == 27) {
                    if(card2.getPosition() == card1.getPosition() + 1) {
                        card1.setIsPossibleSet(true);
                        card2.setIsPossibleSet(true);
                    }
                }
                if(card2.getPosition() == 27) {
                    if(card1.getPosition() == card2.getPosition() + 1) {
                        card1.setIsPossibleSet(true);
                        card2.setIsPossibleSet(true);
                    }
                }
                if(card1.getPosition() == 39) {
                    if(card2.getPosition() == card1.getPosition() - 1) {
                        card1.setIsPossibleSet(true);
                        card2.setIsPossibleSet(true);
                    }
                }
                if(card2.getPosition() == 39) {
                    if(card1.getPosition() == card2.getPosition() - 1) {
                        card1.setIsPossibleSet(true);
                        card2.setIsPossibleSet(true);
                    }
                }
                else {
                    if ((card1.getPosition() == card2.getPosition() - 1) ||
                            card1.getPosition() == card2.getPosition() + 1) {
                        card1.setIsPossibleSet(true);
                        card2.setIsPossibleSet(true);
                    }
                    else {
                        card1.setIsPossibleSet(false);
                        card2.setIsPossibleSet(false);
                    }
                }
            }

            if (card1.getSuit().equals("Clubs")) {
                if(card1.getPosition() == 40) {
                    if(card2.getPosition() == card1.getPosition() + 1) {
                        card1.setIsPossibleSet(true);
                        card2.setIsPossibleSet(true);
                    }
                }
                if(card2.getPosition() == 40) {
                    if(card1.getPosition() == card2.getPosition() + 1) {
                        card1.setIsPossibleSet(true);
                        card2.setIsPossibleSet(true);
                    }
                }
                if(card1.getPosition() == 52) {
                    if(card2.getPosition() == card1.getPosition() - 1) {
                        card1.setIsPossibleSet(true);
                        card2.setIsPossibleSet(true);
                    }
                }
                if(card2.getPosition() == 52) {
                    if(card1.getPosition() == card2.getPosition() - 1) {
                        card1.setIsPossibleSet(true);
                        card2.setIsPossibleSet(true);
                    }
                }
                else {
                    if ((card1.getPosition() == card2.getPosition() - 1) ||
                            card1.getPosition() == card2.getPosition() + 1) {
                        card1.setIsPossibleSet(true);
                        card2.setIsPossibleSet(true);
                    }
                    else {
                        card1.setIsPossibleSet(false);
                        card2.setIsPossibleSet(false);
                    }
                }
            }
        }
    }


    public void loopPossibleRun(Card card1) {
        for (int i = 0; i < cardChecker.length; i++) {
            identifyPossibleRun(card1, cardChecker[i]);
        }
    }

    public void loopPossibleSet(Card card1) {
        for (int i = 0; i < cardChecker.length; i++) {
            identifyPossibleSet(card1, cardChecker[i]);
        }
    }

    public void discardOrDrawPile(Card cardTester) {
        cardTester = state.getDiscardedCard();
        int cardValue = cardTester.getNumber();
        for (int x = 0; x < cardChecker.length;)
        loopPossibleRun(cardTester);
        loopPossibleSet(cardTester);

        if (cardTester.getIsPossibleRun() || cardTester.getIsPossibleSet() ||
                cardTester.getIsInRun() || cardTester.getIsInSet() ) {
            decide.drawDiscard();
        }
        else {
            decide.drawDraw();
        }
    }


    /**
     * Receive info from the GameState, and set the card checker
     * based on the game state Player2Cards
     * @param info
     */
    @Override
    protected void receiveInfo(GameInfo info) {
        state = (GinRummyGameState) info;
        state.getPlayer1Cards();
        cardChecker = state.getPlayer2Cards();
    }
}
