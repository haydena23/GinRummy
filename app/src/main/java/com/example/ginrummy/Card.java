/**
 * Card.java - utilized to describe necessary attributes and operations
 * of a card that is relevant in Gin Rummy
 *
 * @author Jarren Calizo, Tony Hayden, Aron Manalang, Audrey Sauter
 * @version 25 Nov 2020
 */

package com.example.ginrummy;

public class Card {

    //instance variables stored for each card
    private int number;
    private int position; //this int is used for organizing hand.
    private String suit;
    private boolean isPaired; //is grouped
    private boolean isPossibleSet;
    private boolean isPossibleRun;

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
        this.isPossibleSet = false;
        this.isPossibleRun = false;

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
     * Getter method to get the value of a card     *
     * @return The value of the card, 1-13
     */
    public int getNumber() {
        return this.number;
        }

    /**
     * Method to manually set a card. Only used in testing     *
     * @param number Value of card to set
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * Method to manually set a card suit. Only used in testing     *
     * @param suit String for suit of the card
     */
    public void setSuit(String suit) {
        this.suit = suit;
    }

    /**
     * Getter method to get the suit of a card     *
     * @return The suit of the card
     */
    public String getSuit() {
        return this.suit;
    }

    /**
     * Getter method on if a card is paired     *
     * @return whether a given card is paired in a run/set etc.
     */
    public boolean getIsPaired() {
        return this.isPaired;
    }

    /**
     * Setter method for the isPaired variable     *
     * @param isPaired Sets status of isPaired
     */
    public void setIsPaired(boolean isPaired) {
        this.isPaired = isPaired;
    }

    /**
     * Getter method to return the position of a card     *
     * @return an X value position of the card in the hand
     */
    public int getPosition() { return this.position; }

    /**
     * Setter method to set the position of a card in the hand     *
     * @param position
     */
    public void setPosition(int position) { this.position = position; }

    /**
     *  Getter method to say if a card is part of a possible set
     * @return boolean
     */
    public boolean getIsPossibleSet() { return this.isPossibleSet; }

    /**
     * Setter method to set if this card is part of a possible set
     * @param isPossibleSet
     */
    public void setIsPossibleSet(boolean isPossibleSet) { this.isPossibleSet = isPossibleSet; }

    /**
     * Getter method on if the this card is a possible run
     * @return boolean
     */
    public boolean getIsPossibleRun() { return this.isPossibleRun; }

    /**
     * Setter method to set if this card is part of a possible set
     * @param isPossibleRun
     */
    public void setIsPossibleRun(boolean isPossibleRun) { this.isPossibleRun = isPossibleRun;}
}
