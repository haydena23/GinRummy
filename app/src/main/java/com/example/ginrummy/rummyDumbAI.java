package com.example.ginrummy;

import java.util.*;

public class rummyDumbAI {

    private RummyGameState rummyGameState;
    private Random rand = new Random();
    private Card[] player2Cards;

    public rummyDumbAI(RummyGameState gameStateRef) {
        this.rummyGameState = gameStateRef;
        this.player2Cards = gameStateRef.getPlayer2Cards();
        this.player2Cards = sort(this.player2Cards);
    }

    public int discardRandom() {
        if(!rummyGameState.getTurn() && rummyGameState.getCurrentStage() == "drawingStage") {
            return rand.nextInt(11);
        }
        else {
            return 13;
        }
    }

    public Card[] sort(Card[] cardList) {
        Card[] fullySorted = new Card[11];

        int amountOfCards = cardList.length;

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
        /*
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
        */
        return fullySorted;
    }
}
