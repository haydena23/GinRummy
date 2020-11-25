package com.example.ginrummy;

import android.util.Log;

import com.example.game.GameFramework.GamePlayer;
import com.example.game.GameFramework.LocalGame;
import com.example.game.GameFramework.actionMessage.GameAction;
import com.example.ginrummy.GRActions.GinRummyDiscardAction;
import com.example.ginrummy.GRActions.GinRummyDrawAction;
import com.example.ginrummy.GRActions.GinRummyDrawDiscardAction;
import com.example.ginrummy.GRActions.GinRummyGinAction;
import com.example.ginrummy.GRActions.GinRummyGroupAction;
import com.example.ginrummy.GRActions.GinRummyKnockAction;
import com.example.ginrummy.GRActions.GinRummyMoveAction;

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
                    state.setToPlay(1);
                    sendUpdatedStateTo(players[0]);

                } else { // toPlay == 1
                    discardCard(state.getPlayer2Cards(),
                            ((GinRummyDiscardAction) grma).getWhichCard());
                    state.setToPlay(0);
                    sendUpdatedStateTo(players[1]);
                }
            }
        } else if (grma instanceof GinRummyGroupAction) {
            if (state.getToPlay() == 0) {
                state.setP1ValueOfGrouped(state.getP1ValueOfGrouped() +
                        groupMethod(((GinRummyGroupAction) grma).getGroupTheseCard(),
                                ((GinRummyGroupAction) grma).getAmountOfCards()));
                return true;
            } else {
                state.setP2ValueOfGrouped(state.getP2ValueOfGrouped() +
                        groupMethod(((GinRummyGroupAction) grma).getGroupTheseCard(),
                                ((GinRummyGroupAction) grma).getAmountOfCards()));
            }

        } else if (grma instanceof GinRummyGinAction) {
            int P1HandValue = 0;
            int P2HandValue = 0;

            for (Card c : state.getPlayer1Cards()) {
                if (!(c.getSuit().equals("Trash"))) {
                    P1HandValue = P1HandValue + c.getNumber();
                }
            }
            P1HandValue = P1HandValue - state.getP1ValueOfGrouped();

            for (Card c : state.getPlayer2Cards()) {
                if (!(c.getSuit().equals("Trash"))) {
                    P2HandValue = P2HandValue + c.getNumber();
                }
            }
            P2HandValue = P2HandValue - state.getP2ValueOfGrouped();

            if (P1HandValue == 0) {
                state.setP1Points(state.getP1Points() +
                        P2HandValue + 20);
            }
            if (P2HandValue == 0) {
                state.setP2Points(state.getP2Points() +
                        P1HandValue + 20);
            }

        } else if (grma instanceof GinRummyKnockAction) {
            int P1HandValue = 0;
            int P2HandValue = 0;

            for (Card c : state.getPlayer1Cards()) {
                if (!(c.getSuit().equals("Trash"))) {
                    P1HandValue = P1HandValue + c.getNumber();
                }
            }
            P1HandValue = P1HandValue - state.getP1ValueOfGrouped();

            for (Card c : state.getPlayer2Cards()) {
                if (!(c.getSuit().equals("Trash"))) {
                    P2HandValue = P2HandValue + c.getNumber();
                }
            }
            P2HandValue = P2HandValue - state.getP2ValueOfGrouped();

            if (state.getToPlay() == 0 ) { //Player 1 turn
                if (P1HandValue < P2HandValue) { //P1 wins knock, p1 knocked
                    state.setP1Points(state.getP1Points() +
                            P2HandValue - P1HandValue);
                } else {
                    state.setP2Points(state.getP2Points() +
                            P1HandValue - P2HandValue + 10);
                }
            } else { //Players 2 turn
                if (P2HandValue < P1HandValue) { //P2 wins knock, p2 knocked
                    state.setP2Points(state.getP2Points() +
                            P2HandValue - P1HandValue);

                } else {
                    state.setP1Points(state.getP1Points() +
                            P1HandValue - P2HandValue + 10);
                }
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
        if (state.getAmountDrawn() == 31) {
            //gotta compute points for this
            return "Game Over";
        }
        if (state.getP1Points() > 0) {
            return "Game Over, P1 has won! ";
        }
        if (state.getP2Points() > 0) {
            return "Game Over, P2 has won! ";
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
        } else {
            return 0;
        }

        //updating algorithm
        if (state.getToPlay() == 0) {
            int counter = amountGrouped;

            for (Card c : state.getPlayer1Cards()) { //
                dontPut = false;
                for (int x = 0; x < amountGrouped; x++) {
                    if (c.equals(updatedCards[x])) {
                        dontPut = true;
                    }
                }
                if (!dontPut) {
                    updatedCards[counter] = c;
                    counter++;
                }
            }
            state.setPlayer1Cards(updatedCards);
        } else {
            int counter = amountGrouped;

            for (Card c : state.getPlayer2Cards()) {
                dontPut = false;
                for (int x = 0; x < amountGrouped; x++) {
                    if (c.equals(updatedCards[x])) {
                        dontPut = true;
                    }
                }
                if (!dontPut) {
                    updatedCards[counter] = c;
                    counter++;
                }
            }
            state.setPlayer2Cards(updatedCards);
        }
        return valueGrouped;
    }

    public boolean isPairable(Card[] cardList, int amountOfCards) {
        int counter = 0;

        for (int i = 0; i < amountOfCards - 1; i++) {
            if (cardList[i].getNumber()==cardList[i+1].getNumber()) { //Checks if they're the same number
                counter++;
            } else { //
                counter = 0;
                break;
            }
        }
        if (counter + 1 == amountOfCards) {
            return true;
        }

        for (int x = 0; x < amountOfCards - 1; x++) {
            if (cardList[x].getSuit().equals(cardList[x+1].getSuit())) { //Checks if they're all the same suit.
                counter++;
            } else {
                counter = 0;
                break;
            }
        }
        if (counter + 1 == amountOfCards) {
            counter = 0;
            for (int y = 0; y < amountOfCards - 1; y++ ) {
                if (cardList[y].getNumber() == cardList[y+1].getNumber()-1) {
                    counter ++;
                }
            }
            if (counter + 1 == amountOfCards) {
                return true;
            }
        }
        return false;
    }
}
