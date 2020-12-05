/**
 * GinRummyGameState.java - Class that holds all the data for the
 * current state of the game
 *
 * @author Jarren Calizo, Tony Hayden, Aron Manalang, Audrey Sauter
 * @version 25 Nov 2020
 */

package com.example.ginrummy;

import com.example.game.GameFramework.infoMessage.GameState;

import java.util.Random;

public class GinRummyGameState extends GameState {
    // Instance variables that uses the Card Class
    private Card[] startingDeck;
    private Card[] player1Cards;
    private Card[] player2Cards;
    private Card[] drawPile;
    private Card discardedCard;

    // Instance variables for the players
    private int totalOfP1;
    private int totalofP2;
    private int P1Points;
    private int P2Points;
    private int P1ValueOfGrouped;
    private int P2ValueOfGrouped;

    // Important GameState information
    private int amountDrawn;
    protected int toPlay;
    private String currentStage;

    /**
     * Constructor for the game state, which sets all of the initial variable values
     * This calls methods defined below that randomly decides the drawPile, both
     * player hands, and the discarded card.
     */
    public GinRummyGameState () {
        Random random = new Random();
        this.startingDeck = createStartingDeck();
        this.player1Cards = createPlayerHand();
        this.player2Cards = createPlayerHand();
        this.drawPile = createDrawPile();
        this.discardedCard = createDiscardPile();

        this.currentStage = "drawingStage";

        this.amountDrawn = 0;
        this.totalOfP1 = 0;
        this.totalofP2 = 0;
        this.P1Points = 0;
        this.P2Points = 0;

        //This randomly decides who starts
        this.toPlay = random.nextInt(2);

        this.P1ValueOfGrouped = 0;
        this.P2ValueOfGrouped = 0;
    }

    /**
     * Deep Copy Constructor for gamestate, which
     * lets us send copies of the actual state
     */
    public GinRummyGameState(GinRummyGameState gameState) {
        // This copies the specific player's turn's hand correctly
        // (By creating new card instances)
        if (gameState.getToPlay() == 0) {
            this.player1Cards = new Card[11];
            for (int i = 0; i < gameState.player1Cards.length; i++) {
                this.player1Cards[i] = new Card(gameState.player1Cards[i].getNumber(),
                        gameState.player1Cards[i].getSuit());
                this.player1Cards[i].setInRun(
                        gameState.player1Cards[i].getIsInRun());
                this.player1Cards[i].setInSet(
                        gameState.player1Cards[i].getIsInSet());
            }
        } else {
            this.player2Cards = new Card[11];
            for (int i = 0; i < gameState.player2Cards.length; i++) {
                this.player2Cards[i] = new Card(gameState.player2Cards[i].getNumber(),
                        gameState.player2Cards[i].getSuit());
                this.player2Cards[i].setInRun(
                        gameState.player2Cards[i].getIsInRun());
                this.player2Cards[i].setInSet(
                        gameState.player2Cards[i].getIsInSet());
            }
        }

        // This copies the current drawPile correctly by creating new Cards.
        this.drawPile = new Card[32];
        for (int i = 0; i < gameState.drawPile.length; i++) {
            if (gameState.drawPile[i]!= null) {
                this.drawPile[i] = new Card(gameState.drawPile[i].getNumber(),
                        gameState.drawPile[i].getSuit());
            }
        }

        // The rest doesn't need to create new instances of the card class.
        this.currentStage = gameState.getCurrentStage();
        this.discardedCard = new Card(gameState.discardedCard.getNumber(),
                gameState.discardedCard.getSuit());
        this.totalOfP1 = gameState.totalOfP1;
        this.P1Points = gameState.P1Points;
        this.P2Points = gameState.P2Points;
        this.toPlay = gameState.toPlay;
        this.amountDrawn= gameState.amountDrawn;
    }

    /**
     * Method that creates a players hand based on the initial 52 cards
     *
     * @return Returns a fully created player value that has 10 random cards
     */
    public Card[] createPlayerHand() {
        Random random = new Random();
        Card[] returnThis = new Card[11];
        int handCount = 0;

        // Since we didn't use a listArray, there are null values.
        // Due to this, we need to use a while loop which checks if a
        // randomly "drawn" card is null or not.
        while (handCount < 10) {
            int getThisCard = random.nextInt(52);
            if (this.startingDeck[getThisCard] != null) {
                returnThis[handCount] = startingDeck[getThisCard];
                this.startingDeck[getThisCard]=null;
                handCount++;
            }
        }

        // Instead of a null card, we used a 100 Trash card to prevent errors.
        returnThis[10] = new Card(100, "Trash") ;
        return returnThis;
    }

    /**
     * Returns a randomly chosen card for the discard pile.
     *
     * @return the randomly chosen card.
     */
    public Card createDiscardPile() {
        Card returnMe = new Card(this.drawPile[31].getNumber(),
                this.drawPile[31].getSuit());
        this.drawPile[31] = null;

        return returnMe;
    }

    /**
     * Method that takes the remaining cards (after the player hands)
     * and returns it
     *
     * @return an array of cards to be used for the draw pile.
     */
    public Card[] createDrawPile() {
        Random random = new Random();
        Card[] returnThis = new Card[32];
        int pileAmount = 0;

        while (pileAmount < 32) {
            int getThisCard = random.nextInt(52);
            if (this.startingDeck[getThisCard] != null) {
                returnThis[pileAmount] = this.startingDeck[getThisCard];
                this.startingDeck[getThisCard] = null;
                pileAmount++;
            }
        }

        return returnThis;
    }

    /**
     * Method that recreates a standard deck of cards to be used.
     *
     * @return an array of 52 cards.
     */
    public Card[] createStartingDeck() {
        Card[] startingDeck = new Card[52];

        //Hearts
        startingDeck[0] = new Card(1, "Hearts");
        startingDeck[1] = new Card(2, "Hearts");
        startingDeck[2] = new Card(3, "Hearts");
        startingDeck[3] = new Card(4, "Hearts");
        startingDeck[4] = new Card(5, "Hearts");
        startingDeck[5] = new Card(6, "Hearts");
        startingDeck[6] = new Card(7, "Hearts");
        startingDeck[7] = new Card(8, "Hearts");
        startingDeck[8] = new Card(9, "Hearts");
        startingDeck[9] = new Card(10, "Hearts");
        startingDeck[10] = new Card(11, "Hearts");
        startingDeck[11] = new Card(12, "Hearts");
        startingDeck[12] = new Card(13, "Hearts");

        //Diamonds
        startingDeck[13] = new Card(1, "Diamonds");
        startingDeck[14] = new Card(2, "Diamonds");
        startingDeck[15] = new Card(3, "Diamonds");
        startingDeck[16] = new Card(4, "Diamonds");
        startingDeck[17] = new Card(5, "Diamonds");
        startingDeck[18] = new Card(6, "Diamonds");
        startingDeck[19] = new Card(7, "Diamonds");
        startingDeck[20] = new Card(8, "Diamonds");
        startingDeck[21] = new Card(9, "Diamonds");
        startingDeck[22] = new Card(10, "Diamonds");
        startingDeck[23] = new Card(11, "Diamonds");
        startingDeck[24] = new Card(12, "Diamonds");
        startingDeck[25] = new Card(13, "Diamonds");

        //Spades
        startingDeck[26] = new Card(1,"Spades");
        startingDeck[27] = new Card(2,"Spades");
        startingDeck[28] = new Card(3,"Spades");
        startingDeck[29] = new Card(4,"Spades");
        startingDeck[30] = new Card(5,"Spades");
        startingDeck[31] = new Card(6,"Spades");
        startingDeck[32] = new Card(7,"Spades");
        startingDeck[33] = new Card(8,"Spades");
        startingDeck[34] = new Card(9,"Spades");
        startingDeck[35] = new Card(10,"Spades");
        startingDeck[36] = new Card(11,"Spades");
        startingDeck[37] = new Card(12,"Spades");
        startingDeck[38] = new Card(13,"Spades");

        //Clubs
        startingDeck[39] = new Card(1, "Clubs");
        startingDeck[40] = new Card(2, "Clubs");
        startingDeck[41] = new Card(3, "Clubs");
        startingDeck[42] = new Card(4, "Clubs");
        startingDeck[43] = new Card(5, "Clubs");
        startingDeck[44] = new Card(6, "Clubs");
        startingDeck[45] = new Card(7, "Clubs");
        startingDeck[46] = new Card(8, "Clubs");
        startingDeck[47] = new Card(9, "Clubs");
        startingDeck[48] = new Card(10, "Clubs");
        startingDeck[49] = new Card(11, "Clubs");
        startingDeck[50] = new Card(12, "Clubs");
        startingDeck[51] = new Card(13, "Clubs");

        return startingDeck;
    }

    // Getters and Setters methods for the instance variables
    // Which sets and returns values for the variables.
    public Card[] getPlayer1Cards() {
        return player1Cards;
    }
    public void setPlayer1Cards(Card[] player1Cards) {
        this.player1Cards = player1Cards;
    }

    public Card[] getPlayer2Cards() {
        return player2Cards;
    }
    public void setPlayer2Cards(Card[] player2Cards) {
        this.player2Cards = player2Cards;
    }

    public Card getDiscardedCard() {
        return discardedCard;
    }
    public void setDiscardedCard(Card discardedCard) {
        this.discardedCard = discardedCard; }

    public int getP1Points() {
        return P1Points;
    }
    public void setP1Points(int p1Points) {
        P1Points = p1Points;
    }

    public int getP2Points() {
        return P2Points;
    }
    public void setP2Points(int p2Points) {
        P2Points = p2Points;
    }

    public int getAmountDrawn() {
        return amountDrawn;
    }
    public void setAmountDrawn(int amountDrawn) {
        this.amountDrawn = amountDrawn;
    }

    public String getCurrentStage() {
        return currentStage;
    }
    public void setCurrentStage(String currentStage) {
        this.currentStage = currentStage;
    }

    //No setDrawPile method
    public Card[] getDrawPile() {
        return drawPile;
    }

    public int getToPlay() {
        return toPlay;
    }
    public void setToPlay(int toPlay) {
        this.toPlay = toPlay;
    }

    public int getP1ValueOfGrouped() {
        return P1ValueOfGrouped;
    }
    public void setP1ValueOfGrouped(int p1ValueOfGrouped) {
        P1ValueOfGrouped = p1ValueOfGrouped; }

    public int getP2ValueOfGrouped() {
        return P2ValueOfGrouped;
    }
    public void setP2ValueOfGrouped(int p2ValueOfGrouped) {
        P2ValueOfGrouped = p2ValueOfGrouped; }
}
