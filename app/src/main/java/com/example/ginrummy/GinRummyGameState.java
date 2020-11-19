package com.example.ginrummy;

import com.example.game.GameFramework.infoMessage.GameState;

import java.util.Random;

public class GinRummyGameState extends GameState {
    private Card[] startingDeck;
    private Card[] player1Cards;
    private Card[] player2Cards;
    private Card[] drawPile;
    private Card discardedCard;

    //Depending on which player, the current total of hand will display.
    private int totalOfP1;
    private int P1Points;
    private int totalofP2;
    private int P2Points;
    private int amountDrawn;

    private int toPlay;

    private String currentStage;

    /**
     * Normal Constructor for RummyGameState
     */
    public GinRummyGameState () {
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
        this.toPlay = 1; // will always be player 1 that starts.
    }

    /**
     * Method that randomly sets up the player's hand
     */
    public Card[] createPlayerHand() {
        Random random = new Random();
        Card[] returnThis = new Card[11];
        int handCount = 0;

        while (handCount < 10) {
            int getThisCard = random.nextInt(52);
            if (this.startingDeck[getThisCard] != null) {
                returnThis[handCount] = startingDeck[getThisCard];
                this.startingDeck[getThisCard]=null;
                handCount++;
            }
        }
        returnThis[10] = new Card(100, "Trash") ;
        return returnThis;
    }

    public Card createDiscardPile() {

        //Random random = new Random();

        //int getThisCard = random.nextInt(32);
        Card returnMe = new Card(this.drawPile[31].getNumber(),
                this.drawPile[31].getSuit());
        this.drawPile[31] = null;

        return returnMe;
    }

    /**
     * Method creates the card pile that holds unknown
     * cards that have not been in play yet(left side)
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
     * Method creates all 52 cards for game to use
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
        this.discardedCard = discardedCard;
    }

    public int getTotalOfP1() {
        return totalOfP1;
    }

    public void setTotalOfP1(int totalOfP1) {
        this.totalOfP1 = totalOfP1;
    }

    public int getP1Points() {
        return P1Points;
    }

    public void setP1Points(int p1Points) {
        P1Points = p1Points;
    }

    public int getTotalofP2() {
        return totalofP2;
    }

    public void setTotalofP2(int totalofP2) {
        this.totalofP2 = totalofP2;
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

    public boolean getTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    public String getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(String currentStage) {
        this.currentStage = currentStage;
    }

    public Card[] getDrawPile() {
        return drawPile;
    }

    public void setDrawPile(Card[] drawPile) {
        this.drawPile = drawPile;
    }

    public int getToPlay() {
        return toPlay;
    }

    public void setToPlay(int toPlay) {
        this.toPlay = toPlay;
    }
}
