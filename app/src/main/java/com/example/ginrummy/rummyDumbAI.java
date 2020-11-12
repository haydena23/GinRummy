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

    public void act() {
        Random random = new Random();
        //Randomly decides to draw from draw pile or draw from discard pile.
        if (random.nextBoolean()) {
            player2Cards[10] = rummyGameState.drawDraw();
        } else {
            player2Cards[10] = rummyGameState.drawDiscard();
        }
        int discardThis = random.nextInt(12);
        rummyGameState.discardCard(player2Cards, discardThis);
        rummyGameState.toggleTurn();
    }

    public int discardLow() {
        if(!rummyGameState.getTurn() && rummyGameState.getCurrentStage() == "drawingStage") {
            int clubCounter2 = 0;
            int heartCounter2 = 0;
            int spadeCounter2 = 0;
            int diamondCounter2 = 0;
            Arrays.sort(player2Cards);
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

    public Card[] sort(Card[] cardList) {
        Card[] fullySorted = new Card[11];

        int amountOfCards = cardList.length-1;//last element null

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
