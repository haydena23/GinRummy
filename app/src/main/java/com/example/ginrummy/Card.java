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
    private int position; //this int is used for organizing hand.
    private String suit;

    private boolean isPaired; //is grouped


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
        this.position = number;

        switch (suit) {
            case "Hearts":
                break;
            case "Diamonds":
                position = position + 13;
                break;
            case "Spades":
                position = position + 26;
                break;
            case "Clubs":
                position = position + 39;
                break;
            default:
                break;
        }
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

    public void setIsPaired(boolean isPaired) {
        this.isPaired = isPaired;
    }
}
