/**
 * Card.java - utilized to describe necessary attributes and operations
 * of a card that is relevant in Gin Rummy
 *
 * @author Jarren Calizo, Tony Hayden, Aron Manalang, Audrey Sauter
 * @version 12 Nov 2020
 */

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

    public void setNumber(int number) {
        this.number = number;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public String getSuit() {
        return this.suit;
    }
}
