package com.example.ginrummy;

public class Card {
    private int number;
    private String suit;

    public Card (int number, String suit) {
        this.number = number;
        this.suit = suit;
    }

    public int getNumber() {
        return this.number;
    }

    public String getSuit() {
        return this.suit;
    }
}
