package com.example.ginrummy;

import java.util.*;

public class rummyDumbAI {

    private RummyGameState rummyGameState;
    private Random rand = new Random();
    private Card[] player2Cards;

    public rummyDumbAI(RummyGameState gameStateRef) {
        this.rummyGameState = gameStateRef;
        this.player2Cards = gameStateRef.getPlayer2Cards();
    }

    public int discardRandom() {
        if(!rummyGameState.getTurn() && rummyGameState.getCurrentStage() == "drawingStage") {
            return rand.nextInt(12);
        }
        else {
            return 13;
        }
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
