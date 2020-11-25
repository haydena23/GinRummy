package com.example.ginrummy;

import com.example.game.GameFramework.GameComputerPlayer;
import com.example.game.GameFramework.infoMessage.GameInfo;

public class GinRummySmartAI extends GameComputerPlayer {
    GinRummyGameState state;
    private Card[] cardChecker;

    /**
     * constructor
     *
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

    @Override
    protected void receiveInfo(GameInfo info) {
        state = (GinRummyGameState) info;
        state.getPlayer1Cards();
        cardChecker = state.getPlayer2Cards();
    }
}
