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
            return rand.nextInt(12);
        }
        else {
            return 13;
        }
    }

    public Card[] sort(Card[] cardList) {
        Card[] fullySorted = new Card[11];

        int amountOfCards = cardList.length;

        //Used code in https://www.geeksforgeeks.org/java-program-for-selection-sort/
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
                for(int i = 0; i < player2Cards.length - 1; i++) {
                    if(player2Cards[i].getNumber()+1 == player2Cards[i+1].getNumber() && (player2Cards[i].getSuit()+1 == player2Cards[i+1].getSuit())) {
                        knockValues.add(player2Cards[i].getNumber());
                    }
                }
            }
            if(heartCounter > 3) {
                for(int i = 0; i < player2Cards.length - 1; i++) {
                    if((player2Cards[i].getNumber()+1 == player2Cards[i+1].getNumber()) && (player2Cards[i].getSuit()+1 == player2Cards[i+1].getSuit())) {
                        knockValues.add(player2Cards[i].getNumber());
                    }
                }
            }
            if(spadeCounter > 3) {
                for(int i = 0; i < player2Cards.length - 1; i++) {
                    if(player2Cards[i].getNumber()+1 == player2Cards[i+1].getNumber() && (player2Cards[i].getSuit()+1 == player2Cards[i+1].getSuit())) {
                        knockValues.add(player2Cards[i].getNumber());
                    }
                }
            }
            if(diamondCounter > 3) {
                for(int i = 0; i < player2Cards.length - 1; i++) {
                    if(player2Cards[i].getNumber()+1 == player2Cards[i+1].getNumber() && (player2Cards[i].getSuit()+1 == player2Cards[i+1].getSuit())) {
                        knockValues.add(player2Cards[i].getNumber());
                    }
                }
            }
            if(clubCounter + heartCounter + spadeCounter + diamondCounter == 10) {
                rummyGameState.autoGin(player2Cards);
            }
        }
    }
}
