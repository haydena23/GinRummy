/**
 * Card.java - utilized to describe necessary attributes and operations
 * of a card that is relevant in Gin Rummy
 *
 * @author Jarren Calizo, Tony Hayden, Aron Manalang, Audrey Sauter
 * @version 12 Nov 2020
 */

package com.example.ginrummy;

public class Card {

    //instance variables stored for each card
    private int number;
    private String suit;
    private boolean isPaired;

    /**
     * constructor for objects of  card class
     *
     * @param number number value of a card
     * @param suit suit value of a card
     */
    public Card (int number, String suit) {
        this.number = number;
        this.suit = suit;
        this.isPaired = false;
    }

    /**
     * Getter method to get the value of a card
     *
     * @return The value of the card, 1-13
     */
    public int getNumber() {
        return this.number;
    }

    /**
     * Method to manually set a card. Only used in testing
     *
     * @param number Value of card to set
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * Method to manually set a card suit. Only used in testing
     *
     * @param suit String for suit of the card
     */
    public void setSuit(String suit) {
        this.suit = suit;
    }

    /**
     * Getter method to get the suit of a card
     *
     * @return The suit of the card
     */
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
