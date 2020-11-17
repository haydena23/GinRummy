package com.example.ginrummy;

public class Card {
    private int number;
    private String suit;
    private boolean isPaired;

    public Card (int number, String suit) {
        this.number = number;
        this.suit = suit;
        this.isPaired = false;
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public String getSuit() {
        return this.suit;
    }

    public boolean getIsPaired() {
        return this.isPaired;
    }

    public void toggleIsPaired() {
        this.isPaired = !this.isPaired;
    }
}
