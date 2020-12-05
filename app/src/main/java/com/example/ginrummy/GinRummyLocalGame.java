/**
 * GinRummyLocalGame.java - Class extended from the game framework that
 * creates a local game to allow the state
 * to be built and the game to be played.
 *
 * @author Jarren Calizo, Tony Hayden, Aron Manalang, Audrey Sauter
 * @version 25 Nov 2020
 */

package com.example.ginrummy;

import android.os.SystemClock;
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
import com.example.ginrummy.GRActions.GinRummyNoDrawsAction;

import java.lang.reflect.Array;

public class GinRummyLocalGame extends LocalGame{

    //Instance variable
    GinRummyGameState state;

    /**
     * Constructor for the GinRummyLocalGame class
     */
    public GinRummyLocalGame() {
        Log.i("GinRummyLocalGame", "creating game");

        // Initializes the game state.
        state = new GinRummyGameState();
    }

    /**
     * This checks a move action sent to the local game, and acts based on what it is.
     *
     * @param action the action sent
     * @return true if the move was legal; false otherwise
     */
    @Override
    protected boolean makeMove(GameAction action) {
        int P1HandValue = 0;
        int P2HandValue = 0;

        // check that we have a GinRummyPlayerAction
        if (!(action instanceof GinRummyMoveAction)) {
            return false;
        }

        // If it is a move action, we save it to a variable grma.
        GinRummyMoveAction grma = (GinRummyMoveAction) action;

        // get the index of the player making the move
        int thisPlayerIdx = getPlayerIdx(grma.getPlayer());

        if (thisPlayerIdx < 0) { // illegal player
            return false;
        }

        //If it was a draw Action
        if (grma instanceof GinRummyDrawAction) {
            if (thisPlayerIdx != state.toPlay) {
                // attempt to play when it's the other player's turn
                return false;
            }

            // Attempts to draw when they've already drawn thus return false.
            if (!(state.getCurrentStage().equals("drawingStage"))) {
                return false;
            }

            // Attempts to draw when there are no cards left thus returns false (shouldn't happen)
            if (state.getAmountDrawn() == 31) {
                return false;
            } else {
                // Finally, it checks who sent the action.
                // If 0, it sends the card the local game drew to player 1.
                if(state.getToPlay() == 0) {
                    Array.set(state.getPlayer1Cards(), 10, drawDraw());
                    sendUpdatedStateTo(players[0]);
                } else {
                    //Otherwise, it sends the card to the player 2.
                    Array.set(state.getPlayer2Cards(), 10, drawDraw());
                    sendUpdatedStateTo(players[1]);
                }
            }

            //If it was a Draw the Discard action.
        } else if (grma instanceof GinRummyDrawDiscardAction) {
            // Similar to checks with the draw action. Checks if its the right turn
            // Checks if you're supposed to be drawing, and checks if the game should be over.
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

            //This is if the action was a discard action.
        } else if (grma instanceof GinRummyDiscardAction) {
            Card attemptOnThis;
            // Similar checks to all of the other actions. Checks if it was the right
            // player, and checks if they should be discarding.
            if (thisPlayerIdx != state.toPlay) {
                // attempt to play when it's the other player's turn
                return false;
            }
            if (!(state.getCurrentStage().equals("discardStage"))) {
                return false;
            }
            // This just checks if the card the user is trying to throw away
            // Has already been grouped. Which we aren't allowing for now.
            if (state.getToPlay() == 0) {
                attemptOnThis = (Card)Array.get(state.getPlayer1Cards(),
                        ((GinRummyDiscardAction) grma).getWhichCard());
            } else {
                attemptOnThis = (Card)Array.get(state.getPlayer2Cards(),
                        ((GinRummyDiscardAction) grma).getWhichCard());
            }
            if (attemptOnThis.getIsInSet() || attemptOnThis.getIsInRun()) {
                return false;
            }
            if(state.getToPlay() == 0) {
                discardCard(state.getPlayer1Cards(),
                        ((GinRummyDiscardAction) grma).getWhichCard());
                // Since discarding ends your turn, it switches the toPlay value.
                state.setToPlay(1);
                sendUpdatedStateTo(players[0]);

            } else { // toPlay == 1
                discardCard(state.getPlayer2Cards(),
                        ((GinRummyDiscardAction) grma).getWhichCard());
                // Since discarding ends your turn, it switches the toPlay value.
                state.setToPlay(0);
                sendUpdatedStateTo(players[1]);
            }

            // If it was an instance of Group Action
        } else if (grma instanceof GinRummyGroupAction) {
            if (state.getToPlay() == 0) {
                // This calls the groupMethod, which is best explained below.
                state.setP1ValueOfGrouped(state.getP1ValueOfGrouped() +
                        groupMethod(((GinRummyGroupAction) grma).getGroupTheseCard(),
                                ((GinRummyGroupAction) grma).getAmountOfCards()));
            } else {
                state.setP2ValueOfGrouped(state.getP2ValueOfGrouped() +
                        groupMethod(((GinRummyGroupAction) grma).getGroupTheseCard(),
                                ((GinRummyGroupAction) grma).getAmountOfCards()));
            }

            //If the instance was a Gin action
        } else if (grma instanceof GinRummyGinAction) {
            //it checks the hand values first
            P1HandValue = getP1HandValue() - state.getP1ValueOfGrouped();
            P2HandValue = getP2HandValue() - state.getP2ValueOfGrouped();

            // Then, it checks who Ginned based on the hand value.
            // Whoever it was first receives 20 points as a bonus
            // then whatever the total of the other player was.
            if (P1HandValue == 0) {
                state.setP1Points(state.getP1Points() +
                        P2HandValue + 20);
            }
            if (P2HandValue == 0) {
                state.setP2Points(state.getP2Points() +
                        P1HandValue + 20);
            }

            // If the instance was a knock action. Similar to gin,
            // So an explanation is needed, read above ^^^.
        } else if (grma instanceof GinRummyKnockAction) {
            P1HandValue = getP1HandValue() - state.getP1ValueOfGrouped();
            P2HandValue = getP2HandValue() - state.getP2ValueOfGrouped();

            //Player 1 tried to knock.
            if (state.getToPlay() == 0 ) {
                if (P1HandValue > 10) {
                    // But the hand was too large!
                    return false;
                }

                //P1 wins knock, P1 knocked
                if (P1HandValue < P2HandValue) {
                    // No extra points.
                    state.setP1Points(state.getP1Points() +
                            P2HandValue - P1HandValue);
                } else {
                    //P1 loses their knock, so P2 gets extra points.
                    state.setP2Points(state.getP2Points() +
                            P1HandValue - P2HandValue + 10);
                }
            } else { //Player 2 tried to knock. Exactly the same as above but flipped.
                if (P2HandValue > 10) {
                    return false;
                }
                if (P2HandValue < P1HandValue) { //P2 wins knock, p2 knocked
                    state.setP2Points(state.getP2Points() +
                            P1HandValue - P2HandValue);
                } else {
                    state.setP1Points(state.getP1Points() +
                            P2HandValue - P1HandValue + 10);
                }
            }

            //This is called if the drawPile ran out of cards.
        } else if (grma instanceof GinRummyNoDrawsAction) {
            //This checks if it did run out.
            if (state.getAmountDrawn() < 31) {
                return false;
            }

            //If it did, it gets the value of the hand of both players.
            P1HandValue = getP1HandValue() - state.getP1ValueOfGrouped();
            P2HandValue = getP2HandValue() - state.getP2ValueOfGrouped();

            if (P2HandValue > P1HandValue) { // P2 loses
                state.setP1Points(state.getP1Points() +
                        P2HandValue - P1HandValue);
            } else { //P1 wins
                state.setP2Points(state.getP2Points() +
                        P1HandValue - P2HandValue);
            }
            //And gives them whoever won the necessary amount of points.
        }
        else { // Some unexpected action so simply return false.
            return false;
        }

        // Return true, because the move was successful if we get here
        return true;
    }

    /**
     * Method to calculate the value of Player1Hand
     * @return Integer of the player hand
     */
    public int getP1HandValue() {
        int P1HandValue = 0;
        for (Card c : state.getPlayer1Cards()) {
            if (!(c.getSuit().equals("Trash"))) {
                P1HandValue = P1HandValue + c.getValue();
            }
        }
        return P1HandValue;
    }

    /**
     * Method to calculate the value of Player2Hand
     * @return Integer of the player 2 hand
     */
    public int getP2HandValue() {
        int P2HandValue = 0;
        for (Card c : state.getPlayer2Cards()) {
            if (!(c.getSuit().equals("Trash"))) {
                P2HandValue = P2HandValue + c.getValue();
            }
        }
        return P2HandValue;
    }

    /**
     * Sends the new updated COPY state to a player based on who made an action
     * @param p The player to send the info to
     */
    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        GinRummyGameState copy = new GinRummyGameState(state);
        p.sendInfo(copy);
    }

    /**
     * Method to check on whether or not the player is allowed to make a move
     *
     * @param playerIdx the player's player-number (ID)
     *
     * @return Whether or not the player can move
     */
    @Override
    protected boolean canMove(int playerIdx) {
        if (playerIdx < 0 || playerIdx > 1) {
            // if our player-number is out of range, return false
            return false;
        }
        else {
            // player can move if it's their turn
            return state.getAmountDrawn() < 31 && state.getToPlay() == playerIdx;
        }
    }

    /**
     * Method to check if the game is over
     * @return Who won the game
     */
    @Override
    protected String checkIfGameOver() {
        if (state.getP1Points() > 0) {
            return "Game Over, Player 1 has won! ";
        }
        if (state.getP2Points() > 0) {
            return "Game Over, Player 2 has won! ";
        }
        return null;
    }

    /**
     * Method for a player to draw a card from the draw pile
     * @return The card that the player drew
     */
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
     * Method for a player to draw a card from the discard pile
     * @return The card that the player drew
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
     *  @param cardPile Array containing user's hand
     *
     * @param toRemove Card selected in cardPile by position
     */
    public void discardCard(Card[] cardPile, int toRemove) {
        if(toRemove == 10) {
            state.setDiscardedCard(cardPile[10]);
            state.setCurrentStage("drawingStage");
            cardPile[10] = new Card (100, "Trash");
            return;
        }

        state.setDiscardedCard(cardPile[toRemove]);

        for(int i = toRemove; i < 10; i++) {
            cardPile[i] = cardPile[i+1];
        }

        cardPile[10] = new Card(100, "Trash");
        state.setCurrentStage("drawingStage");
    }

    /**
     * Method to check and group cards that are put given
     *
     * @param cardList The cards selected to check for grouping
     * @param amountOfCards The amount of cards selected.
     *
     * @return The value of the grouped cards
     */
    public int groupMethod(Card[] cardList, int amountOfCards) {
        Card[] updatedCards = new Card[11];
        int valueGrouped = 0;

        boolean dontPut;
        boolean runPossible = canBeInRun(cardList, amountOfCards);
        boolean setPossible = canBeInSet(cardList, amountOfCards);

        // If the given cards are neither sets nor runs, simply return 0.
        if (!runPossible && !setPossible) {
            return 0;
        }

        // Anything beyond this means that the cards being input
        // Can be put in either a run or a set.
        // Therefore, we do exactly that. Put them in a group.
        if (runPossible) {
            // This is if the cards were a run.
            for (int i = 0; i < amountOfCards; i++) {
                // This algorithm only counts the value if the cards weren't already grouped.
                if (!cardList[i].getIsInRun()) {
                    valueGrouped = valueGrouped + cardList[i].getValue();
                }
                // Then it sets each card to InRun TRUE
                cardList[i].setInRun(true);
            }
        }

        if (setPossible) {
            //This is if the cards were in a set.
            for (int i = 0; i < amountOfCards; i++) {
                // If they were already not in a set, the values are counted.
                if (!cardList[i].getIsInSet()) {
                    valueGrouped = valueGrouped + cardList[i].getValue();
                }
                // Then finally, it sets InSet TRUE
                cardList[i].setInSet(true);
            }
        }

        // This is an updating algorithm for the human's hand.
        // So we check if the getToPlay is 0, which is the human.
        if (state.getToPlay() == 0) {

            // First, it moves the grouped cards to the left.
            for (int x = 0; x < amountOfCards; x++) {
                updatedCards[x] = cardList[x];
            }

            //Then it fills in the rest with the remaining, not-grouped-this-time cards.
            int elementToPut = amountOfCards;

            for (Card c : state.getPlayer1Cards()) {
                dontPut = false;
                for (int x = 0; x < amountOfCards; x++) {
                    // If a card already exists in updated cards, don't put it in.
                    if (c.getPosition() == updatedCards[x].getPosition()) {
                        dontPut = true;
                        break;
                    }
                }
                if (!dontPut) {
                    //Otherwise, put it into the updated array, then increase the counter.
                    updatedCards[elementToPut] = c;
                    elementToPut++;
                }
            }

            // Then, update player1Cards with the updated Cards.
            state.setPlayer1Cards(updatedCards);
        }

        return valueGrouped;
    }

    /**
     * Method to check if a set of cards are in a set
     *
     * @param cardList The cards selected to check for a set
     * @param amountOfCards The amount of cards to check
     *
     * @return This is simply if it is possible to be in a set or not. (Boolean)
     */
    public boolean canBeInSet(Card[] cardList, int amountOfCards) {
        for (int x = 0; x < amountOfCards - 1; x++) {
            // I set it up this way so I can reuse the same for-loop.
            // The first two if's check if all the cards are in a run.
            if (cardList[x].getIsInRun()) {
                return false;
            }
            if (cardList[amountOfCards-1].getIsInRun()) {
                return false;
            }
            // This if statement checks if 1 matches 2, 2 matches 3, etc.
            if (cardList[x].getNumber() != cardList[x+1].getNumber()) {
                return false;
            }
        }
        // If all of these pass for each card, the cards
        // aren't in a run, and they all have the same number.
        // Therefore, this check is done and can return true

        return true;
    }

    /**
     * Method to check if a set of cards are in a run
     *
     * @param cardList The cards selected to check for a run
     * @param amountOfCards The amount of cards to check
     *
     * @return This is simply if it is possible to be in a run or not. (Boolean)
     */
    public boolean canBeInRun(Card[] cardList, int amountOfCards) {
        for (int x = 0; x < amountOfCards -1; x++) {
            // Very similar to the canBeInSet, but this checks if any
            // of the cards given are in a set already.
            if (cardList[x].getIsInSet()) {
                return false;
            }
            if (cardList[amountOfCards-1].getIsInRun()) {
                return false;
            }
            if (!(cardList[x].getSuit().equals(cardList[x+1].getSuit()))) {
                return false;
            }
            // Then, it checks if the second card is 1 bigger than the first, etc.
            if (cardList[x].getNumber() != cardList[x+1].getNumber() - 1) {
                return false;
            }
        }
        // If all of these checks pass, that means the cards passed through are
        // all not in a set already, they are all the same suit, and they
        // are in an increasing order. Thus, I can return true.

        return true;
    }
}
