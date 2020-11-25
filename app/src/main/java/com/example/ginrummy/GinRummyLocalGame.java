package com.example.ginrummy;

import android.util.Log;

import com.example.game.GameFramework.GamePlayer;
import com.example.game.GameFramework.LocalGame;
import com.example.game.GameFramework.actionMessage.GameAction;

import java.lang.reflect.Array;

public class GinRummyLocalGame extends LocalGame{

    GinRummyGameState state;

    /**
     * Constructor for the SJLocalGame.
     */
    public GinRummyLocalGame() {
        Log.i("GinRummyLocalGame", "creating game");

        // create the state for the beginning of the game
        state = new GinRummyGameState();
    }

    /**
     * makes a move on behalf of a player
     *
     * @param action
     * 		the action denoting the move to be made
     * @return
     * 		true if the move was legal; false otherwise
     */
    @Override
    protected boolean makeMove(GameAction action) {
        // check that we have a GinRummyPlayerAction
        if (!(action instanceof GinRummyMoveAction)) {
            return false;
        }

        GinRummyMoveAction grma = (GinRummyMoveAction) action;

        // get the index of the player making the move
        int thisPlayerIdx = getPlayerIdx(grma.getPlayer());

        if (thisPlayerIdx < 0) { // illegal player
            return false;
        }

        if (grma instanceof GinRummyDrawAction) {
            if (thisPlayerIdx != state.toPlay) {
                // attempt to play when it's the other player's turn
                return false;
            }
            if (!(state.getCurrentStage().equals("drawingStage"))) {
                return false;
            }
            if (state.getAmountDrawn() == 31) {
                // empty deck: return false, as move is illegal
                return false;
            } else {
                if(state.getToPlay() == 0) {
                    Array.set(state.getPlayer1Cards(), 10, drawDraw());
                    sendUpdatedStateTo(players[0]);
                } else { // toPlay == 1
                    Array.set(state.getPlayer2Cards(), 10, drawDraw());
                    sendUpdatedStateTo(players[1]);
                }
            }
        } else if (grma instanceof GinRummyDrawDiscardAction) {
            if (thisPlayerIdx != state.toPlay) {
                // attempt to play when it's the other player's turn
                return false;
            }
            if (!(state.getCurrentStage().equals("drawingStage"))) {
                return false;
            }
            if (state.getAmountDrawn() == 31) {
                // game should be over, false.
                return false;
            } else {
                if(state.getToPlay() == 0) {
                    Array.set(state.getPlayer1Cards(), 10, drawDiscard());
                    sendUpdatedStateTo(players[0]);
                } else { // toPlay == 1
                    Array.set(state.getPlayer2Cards(), 10, drawDiscard());
                    sendUpdatedStateTo(players[1]);
                }
            }
        } else if (grma instanceof GinRummyDiscardAction) {
            if (thisPlayerIdx != state.toPlay) {
                // attempt to play when it's the other player's turn
                return false;
            }
            if (!(state.getCurrentStage().equals("discardStage"))) {
                return false;
            }
            else {
                if(state.getToPlay() == 0) {
                    discardCard(state.getPlayer1Cards(),
                            ((GinRummyDiscardAction) grma).getWhichCard());
                    sendUpdatedStateTo(players[0]);
                } else { // toPlay == 1
                    discardCard(state.getPlayer2Cards(),
                            ((GinRummyDiscardAction) grma).getWhichCard());
                    sendUpdatedStateTo(players[1]);
                }
            }
        } else if (grma instanceof GinRummyGroupAction) {
            if (state.getToPlay() == 0) {
                state.setP1Points(state.getP1Points() +
                        groupMethod(((GinRummyGroupAction) grma).getGroupTheseCard(),
                                ((GinRummyGroupAction) grma).getAmountOfCards()));
            } else {

            }

        } else { // some unexpected action
            return false;
        }

        // return true, because the move was successful if we get her
        return true;
    }

    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        p.sendInfo(state);
    }

    @Override
    protected boolean canMove(int playerIdx) {
        if (playerIdx < 0 || playerIdx > 1) {
            // if our player-number is out of range, return false
            return false;
        }
        else {
            // player can move if it's their turn, or if the middle deck is non-empty
            // so they can slap
            return state.getAmountDrawn() < 31 && state.getToPlay() == playerIdx;
        }
    }

    @Override
    protected String checkIfGameOver() {
        if (state.getAmountDrawn() > 31) {
            return "Game Over";
        }
        return null;
    }

    public Card drawDraw() {
        if (state.getAmountDrawn() > 31) {
            return null;
            //somehow change this into return gameOverInfo
        }
        if (state.getCurrentStage().equals("drawingStage")) {
            Card returnThis = (Card) Array.get( state.getDrawPile(), state.getAmountDrawn() );
            Array.set(state.getDrawPile(), state.getAmountDrawn(), null);
            state.setAmountDrawn(state.getAmountDrawn()+1);
            state.setCurrentStage("discardStage");
            return returnThis;
        } else {
            return null;
        }
    }

    /**
     * Method for drawing the discarded card
     */
    public Card drawDiscard() {
        if (state.getCurrentStage().equals("drawingStage")) {
            Card returnThis = state.getDiscardedCard();
            state.setDiscardedCard(new Card(100, "Trash"));
            state.setCurrentStage("discardStage");
            return returnThis;
        } else {
            return null;
        }
    }

    /**
     * Method for discarding a card from your hand
     *
     * @param cardPile Array containing user's hand
     * @param toRemove Card selected in cardPile by position
     *                 subject to be removed
     */
    public Card[] discardCard(Card[] cardPile, int toRemove) {
        if(toRemove == 10) {
            state.setDiscardedCard(cardPile[10]);
            state.setCurrentStage("drawingStage");
            cardPile[10] = new Card (100, "Trash");
            return cardPile;
        }

        state.setDiscardedCard(cardPile[toRemove]);

        for(int i = toRemove; i < 10; i++) {
            cardPile[i] = cardPile[i+1];
        }

        cardPile[10] = new Card(100, "Trash");
        state.setCurrentStage("drawingStage");
        return cardPile;
    }

    public int groupMethod(Card[] groupTheseCards, int amountGrouped) {
        int valueGrouped = 0;
        Card[] updatedCards = new Card[11];
        boolean dontPut = true;
        Card currentCard = new Card(100, "trash");

        if (isPairable(groupTheseCards, amountGrouped)) {
            for (int x = 0; x < amountGrouped; x++) {
                if (!groupTheseCards[x].getIsPaired()) { //if they're not already paired, count the value.
                    valueGrouped = valueGrouped + groupTheseCards[x].getNumber();
                }
                groupTheseCards[x].setIsPaired(true);
                updatedCards[x] = groupTheseCards[x];
            }
        }

        //updating algorithm
        if (state.getToPlay() == 0) {
            for (int x = amountGrouped; x < 11;) {
                for (Card c : state.getPlayer1Cards()) {
                    currentCard = c;
                    if (c.equals(updatedCards[x])) {
                        dontPut = true;
                    }
                }
                if (!dontPut) {
                    updatedCards[x] = currentCard;
                    x++;
                }
                x++;
            }
            state.setPlayer1Cards(updatedCards);
        } else {
            for (int x = amountGrouped; x < 11 ; x++) {
                for (Card c : state.getPlayer2Cards()) {
                    currentCard = c;
                    if (c.equals(updatedCards[x])) {
                        dontPut = true;
                    }
                }
                if (!dontPut) {
                    updatedCards[x] = currentCard;
                }
            }
            state.setPlayer2Cards(updatedCards);
        }
        return valueGrouped;
    }

    public boolean isPairable(Card[] cardList, int amountOfCards) {
        int counter = 0;

        for (int i = 0; i < amountOfCards - 1; i++) {
            if (!(cardList[i].getSuit().equals(cardList[i+1].getSuit()))) { //Checks if it they're all not the same suit.
                for (int x = 0; x < amountOfCards - 1; x++) {
                    if (!(cardList[x].getNumber()==cardList[x+1].getNumber())) { //Checks if they're the same number
                        return false;
                    } else { //
                        counter ++;
                    }
                }
            } else { //if they are the same suit
                for (int y = 0; y < amountOfCards - 2; y++) {
                    if ((cardList[y].getNumber()+1) == cardList[y+1].getNumber()) { //checks if they're a run
                        counter ++;
                    }
                }
            }
        }
        if (counter + 1 == amountOfCards) { //this will only return true if they are all cards are in a run, or in a set.
            return true;
        }
        return false;
    }
}
