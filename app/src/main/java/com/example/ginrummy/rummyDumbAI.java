/**
 * rummyDumbAI.java - utilized to display attributes and operations
 * with regards how a dumb computer player works
 *
 * @author Jarren Calizo, Tony Hayden, Aron Manalang, Audrey Sauter
 * @version 12 Nov 2020
 */

package com.example.ginrummy;

import java.util.*;

public class rummyDumbAI {

    // Instance variables that the dumb AI needs
    private RummyGameState rummyGameState;
    private Random rand = new Random();
    private Card[] player2Cards;

    /**
     * This is the constructor that we are using for the dumb AI. Pretty simple, just creating the AI and getting the game state
     * reference.
     *
     * @param gameStateRef This is the Game State that we are passing into the constructor to ensure
     *                     we have the correct reference
     */
    public rummyDumbAI(RummyGameState gameStateRef) {
        this.rummyGameState = gameStateRef;
        this.player2Cards = gameStateRef.getPlayer2Cards();
        //this.player2Cards = sort(this.player2Cards); We intend to use this, but can't get it working, which is why it is commented
        //                                             out
    }

    /**
     * This act method decides a 50/50 chance on whether or not the AI will draw from the draw pile or
     * the discard pile
     */
    public void act() {
        Random random = new Random();
        //Randomly decides to draw from draw pile or draw from discard pile.
        if (random.nextBoolean()) {
            player2Cards[10] = rummyGameState.drawDraw();
        } else {
            player2Cards[10] = rummyGameState.drawDiscard();
        }
        int discardThis = random.nextInt(11);
        rummyGameState.discardCard(player2Cards, discardThis);
        rummyGameState.toggleTurn();
    }

    /**
     * This is the method for the AI to discard a random card in their hand
     *
     * CAVEAT: We have not utilized this method yet, as we are currently using another method to do this
     *         It will be used soon, as we have another purpose for it
     *
     * @return A value X indicated which card in the AI hand needs to be discarded
     */
    public int discardLow() {
        if(!rummyGameState.getTurn() && rummyGameState.getCurrentStage() == "drawingStage") {
            int clubCounter2 = 0;
            int heartCounter2 = 0;
            int spadeCounter2 = 0;
            int diamondCounter2 = 0;
            //Arrays.sort(player2Cards);
            for(int i = 0; i < 11; i++) {
                if(player2Cards[i].getSuit() == "Clubs") {
                    clubCounter2++;
                }
                if(player2Cards[i].getSuit() == "Hearts") {
                    heartCounter2++;
                }
                if(player2Cards[i].getSuit() == "Spades") {
                    spadeCounter2++;
                }
                if(player2Cards[i].getSuit() == "Diamonds") {
                    diamondCounter2++;
                }
            }
            return rand.nextInt(12);
        }
        else {
            return 13;
        }
    }

    /**
     * This method is the sorting algorithm that we decided to use to sort the cards based on suits
     * and the value of the card. It is using a simple selection sort.
     *
     * CAVEAT: This method has not been utilized yet, as we do not need to sort the dumb AI hand yet.
     *         Currently, we just select a random card in AI hand to discard
     *
     * @param cardList The current players hand that needs to be sorted
     * @return fullySorted The player hand that is now properly sorted
     */
    public Card[] sort(Card[] cardList) {
        Card[] fullySorted = new Card[11];

        int amountOfCards = cardList.length-1;//last element null

        /**
         * External Citation
         *    Date: 3 November 2020
         *    Problem: Needed a solution to simply sort the cards
         *    Resource:
         *         https://www.geeksforgeeks.org/java-program-for-selection-sort/
         *    Solution: Implemented a card version for selection sort
         */
        for (int i = 0; i < amountOfCards-1; i++)
        {
            // Find the minimum element in unsorted array
            int min_idx = i; // this sorting algorithm will start at the minimum value based on repeated sorting amount.

            for (int j = i+1; j < amountOfCards; j++) {
                if (cardList[j].getNumber() < cardList[min_idx].getNumber()) {
                    min_idx = j;
                }
            }
            // Swap the found minimum element with the first element
            Card temp = cardList[min_idx];
            cardList[min_idx] = cardList[i];
            cardList[i] = temp;
        }

        int counter = 0;
        //Do this second
        for (Card c : cardList ) {
            if (c.getSuit().equals("Hearts")) {
                fullySorted[counter] = c;
                counter++;
            }
        }
        for (Card c : cardList ) {
            if (c.getSuit().equals("Diamonds")) {
                fullySorted[counter] = c;
                counter++;
            }
        }
        for (Card c : cardList ) {
            if (c.getSuit().equals("Spades")) {
                fullySorted[counter] = c;
                counter++;
            }
        }
        for (Card c : cardList ) {
            if (c.getSuit().equals("Clubs")) {
                fullySorted[counter] = c;
                counter++;
            }
        }

        fullySorted[10] = cardList[10];
        return fullySorted;
    }
}
