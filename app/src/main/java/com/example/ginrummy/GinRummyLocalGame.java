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

        // check that we have slap-jack action; if so cast it
        if (!(action instanceof GinRummyMoveAction)) {
            return false;
        }
        GinRummyMoveAction grma = (GinRummyMoveAction) action;

        // get the index of the player making the move; return false
        int thisPlayerIdx = getPlayerIdx(grma.getPlayer());

        if (thisPlayerIdx < 0) { // illegal player
            return false;
        }

        if (grma.isDraw()) {
            //Check if legal
            if (state.getAmountDrawn() == 32) {
                // empty deck: return false, as move is illegal
                return false;
            }
            else {
                giveMiddleCardsToPlayer(1-thisPlayerIdx);
            }
        }

        else if (grma.isDraw()) { // we have a "play" action
            if (thisPlayerIdx != state.toPlay()) {
                // attempt to play when it's the other player's turn
                return false;
            }
            else {
                // it's the correct player's turn: move the top card from the
                // player's deck to the top of the middle deck
                state.getDeck(thisPlayerIdx).moveTopCardTo(state.getDeck(2));
                // if the opponent has any cards, make it the opponent's move
                if (state.getDeck(1-thisPlayerIdx).size() > 0) {
                    state.setToPlay(1-thisPlayerIdx);
                }
            }
        }

        else if (grma.isDiscard()) {

        }

        else { // some unexpected action
            return false;
        }

        // return true, because the move was successful if we get her
        return true;
    }

    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
    }

    @Override
    protected boolean canMove(int playerIdx) {
        return false;
    }

    @Override
    protected String checkIfGameOver() {
        return null;
    }

    public Card drawDraw() {
        if (state.getAmountDrawn() > 31) {
            return null;
        }
        if (state.getCurrentStage() == "drawingStage") {
            Card returnThis = (Card) Array.get( state.getDrawPile(), state.getAmountDrawn() );
            this.drawPile[amountDrawn] = null;
            amountDrawn++;
            this.currentStage = "discardStage";
            return returnThis;
        } else {
            return null;
        }
    }

    /**
     * Method for drawing the discarded card
     */
    public Card drawDiscard() {
        if (this.currentStage == "drawingStage") {
            Card returnThis = this.discardedCard;
            this.discardedCard = new Card(100, "Trash");
            this.currentStage = "discardStage";
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
    public void discardCard(Card[] cardPile, int toRemove) {
        if(toRemove == 10) {
            this.discardedCard = cardPile[10];
            this.player1Cards[10] = new Card (100, "Trash");
            toggleTurn();
            return;
        }

        this.discardedCard = cardPile[toRemove];

        for(int i = toRemove; i < 10; i++) {
            cardPile[i] = cardPile[i+1];
        }

        cardPile[10] = new Card(100, "Trash");
        if(this.turn) {
            this.player1Cards = cardPile;
        } else {
            this.player2Cards = cardPile;
        }
        this.currentStage = "drawingStage";
    }
}
