/**
 * Card.java - A class that is used to simulate cards
 *
 * Most of the methods here were used for error checking / debugging.
 * As such, some of them are unused.
 *
 * @author Jarren Calizo, Tony Hayden, Aron Manalang, Audrey Sauter
 * @version 25 Nov 2020
 */

package com.example.ginrummy;

public class Card {
    //Instance Variables
    private int value;
    private int number;
    private String suit;

    // Position is used for sorting the cards
    // and checking if cards are the same suit and number
    private int position;

    // Boolean to see if a card is grouped in a set or a run
    private boolean isInSet;
    private boolean isInRun;

    // Variables used by the smartAI to determine which card to discard
    private boolean isPossibleSet;
    private boolean isPossibleRun;

    /**
     * Constructor that makes a card with the given number and suit
     *
     * @param number number value of a card
     * @param suit suit value of a card
     */
    public Card (int number, String suit) {
        this.number = number;

        this.value = Math.min(number, 10);

        //This calculates the position of the card if it's in a sorted deck.
        //1-13 is Diamonds, 14-26 is Spades, etc.
        this.position = number;
        switch (suit) {
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

        this.suit = suit;

        this.isInSet = false;
        this.isInRun = false;
        this.isPossibleSet = false;
        this.isPossibleRun = false;
    }

    // Getters and Setters are below, self explanatory.
    // Methods to set the values, and return the values, of instance variables.
    public int getNumber() { return this.number; }
    public void setNumber(int number) { this.number = number; }

    public void setSuit(String suit) { this.suit = suit; }
    public String getSuit() { return this.suit; }

    public int getPosition() { return this.position; }
    public void setPosition(int position) { this.position = position; }

    public boolean getIsPossibleSet() { return this.isPossibleSet; }
    public void setIsPossibleSet(boolean isPossibleSet) {
        this.isPossibleSet = isPossibleSet; }

    public boolean getIsPossibleRun() { return this.isPossibleRun; }
    public void setIsPossibleRun(boolean isPossibleRun) {
        this.isPossibleRun = isPossibleRun; }

    public int getValue() { return value; }
    public void setValue(int value) { this.value = value; }

    public boolean getIsInSet() { return isInSet; }
    public void setInSet(boolean inSet) { isInSet = inSet; }

    public boolean getIsInRun() { return isInRun; }
    public void setInRun(boolean inRun) { isInRun = inRun; }
}
