/**
 * rummySmartAI.java - utilized to display attributes and operations
 * with regards how a smart computer player works
 *
 * @author Jarren Calizo, Tony Hayden, Aron Manalang, Audrey Sauter
 * @version 12 Nov 2020
 *
 * KEY: PLACEHOLDER FOR FUTURE DEVELOPMENT PAST ALPHA RELEASE!
 */


package com.example.ginrummy;

import java.util.*;

public class rummySmartAI {

    private RummyGameState rummyGameState;
    private Random rand = new Random();
    private Card[] player2Cards;

    public rummySmartAI(RummyGameState gameStateRef){
        this.rummyGameState = gameStateRef;
        this.player2Cards = gameStateRef.getPlayer2Cards();
        this.player2Cards = sort(this.player2Cards);
    }

    public void attemptKnock() {
        int clubCounter = 0;
        int heartCounter = 0;
        int spadeCounter = 0;
        int diamondCounter = 0;
        ArrayList<Integer> knockValues = new ArrayList<>();

        if(!rummyGameState.getTurn() && rummyGameState.getCurrentStage() == "drawingStage") {
            Arrays.sort(player2Cards);
            for(int i = 0; i < 11; i++) {
                if(player2Cards[i].getSuit() == "Clubs") {
                    clubCounter++;
                }
                if(player2Cards[i].getSuit() == "Hearts") {
                    heartCounter++;
                }
                if(player2Cards[i].getSuit() == "Spades") {
                    spadeCounter++;
                }
                if(player2Cards[i].getSuit() == "Diamonds") {
                    diamondCounter++;
                }
            }
            if(clubCounter > 3) {
                //Out of bounds error
                for(int i = 0; i < player2Cards.length - 1; i++) {
                    if(player2Cards[i].getNumber()+1 == player2Cards[i+1].getNumber()
                            && (player2Cards[i].getSuit()+1 == player2Cards[i+1].getSuit())) {
                        knockValues.add(player2Cards[i].getNumber());
                    }
                }
            }
            if(heartCounter > 3) {
                for(int i = 0; i < player2Cards.length - 1; i++) {
                    if((player2Cards[i].getNumber()+1 == player2Cards[i+1].getNumber())
                            && (player2Cards[i].getSuit()+1 == player2Cards[i+1].getSuit())) {
                        knockValues.add(player2Cards[i].getNumber());
                    }
                }
            }
            if(spadeCounter > 3) {
                for(int i = 0; i < player2Cards.length - 1; i++) {
                    if(player2Cards[i].getNumber()+1 == player2Cards[i+1].getNumber()
                            && (player2Cards[i].getSuit()+1 == player2Cards[i+1].getSuit())) {
                        knockValues.add(player2Cards[i].getNumber());
                    }
                }
            }
            if(diamondCounter > 3) {
                for(int i = 0; i < player2Cards.length - 1; i++) {
                    if(player2Cards[i].getNumber()+1 == player2Cards[i+1].getNumber()
                            && (player2Cards[i].getSuit()+1 == player2Cards[i+1].getSuit())) {
                        knockValues.add(player2Cards[i].getNumber());
                    }
                }
            }
            if(clubCounter + heartCounter + spadeCounter + diamondCounter == 10) {
                rummyGameState.autoGin(player2Cards);
            }
        }
    }

    public Card[] sort(Card[] cardList) {
        Card[] fullySorted = new Card[11];

        int amountOfCards = cardList.length;

        /**
         * External Citation
         *    Date: 12 November 2020
         *    Problem: Needed a solution to simply sort the cards
         *    Resource:
         *         https://www.geeksforgeeks.org/java-program-for-selection-sort/
         *    Solution: Implemented a card version for selection sort
         */
        for (int i = 0; i < amountOfCards-1; i++)
        {
            // Find the minimum element in unsorted array
            int min_idx = i; // sorting alg will start at the min val based on repeated sort amt.

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
