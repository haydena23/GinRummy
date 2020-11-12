package com.example.ginrummy;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Controller implements View.OnClickListener{
    private boolean discardOn = false;
    private boolean groupOn = false;
    private RummyGameState rummyGameState;
    private Card[] player1Cards;
    private Card[] player2Cards;
    private rummyDumbAI dumbAI;


    private int groupAmount;
    private int groupTotal; //value of grouped cards
    private Card[] groupCards;

    Button discardButton;
    Button groupButton;

    ImageView card0;
    ImageView card1;
    ImageView card2;
    ImageView card3;
    ImageView card4;
    ImageView card5;
    ImageView card6;
    ImageView card7;
    ImageView card8;
    ImageView card9;
    ImageView card10;
    ImageView drawPileCard;
    ImageView discardedCard;

    ScoreView scoreView;

    public Controller(RummyGameState rummyGameState, Button discardButton,
                      ImageView card0, ImageView card1, ImageView card2, ImageView card3,
                      ImageView card4, ImageView card5, ImageView card6, ImageView card7,
                      ImageView card8, ImageView card9, ImageView card10, ImageView drawPileCard,
                      Button groupButton, ImageView discardedCard, ScoreView scoreView) {

        this.rummyGameState = rummyGameState;
        this.player1Cards = rummyGameState.getPlayer1Cards();
        this.player2Cards = rummyGameState.getPlayer2Cards();

        this.discardButton = discardButton;
        this.groupButton = groupButton;

        this.dumbAI = new rummyDumbAI(rummyGameState);

        this.card0 = card0;
        this.card1 = card1;
        this.card2 = card2;
        this.card3 = card3;
        this.card4 = card4;
        this.card5 = card5;
        this.card6 = card6;
        this.card7 = card7;
        this.card8 = card8;
        this.card9 = card9;
        this.card10 = card10;
        this.drawPileCard = drawPileCard;
        this.discardedCard = discardedCard;

        updateCards();

        groupAmount = 0;
        groupTotal = 0;
        groupCards = new Card[11];

        this.scoreView = scoreView;

        //player1Cards[0].getNumber()
    }

    public void updateCards() {
        updateCard(rummyGameState.getDiscardedCard(), discardedCard);
        updateCard(player1Cards[0], card0);
        updateCard(player1Cards[1], card1);
        updateCard(player1Cards[2], card2);
        updateCard(player1Cards[3], card3);
        updateCard(player1Cards[4], card4);
        updateCard(player1Cards[5], card5);
        updateCard(player1Cards[6], card6);
        updateCard(player1Cards[7], card7);
        updateCard(player1Cards[8], card8);
        updateCard(player1Cards[9], card9);
        if(player1Cards[10] == null) {
            setBlank();
        }
        else {
            updateCard(player1Cards[10], card10);
        }
    }

    public void setBlank() {
        card10.setImageResource(R.drawable.blue_back);
    }

    //DOTHIS : Current Issues - we have to check what player called it, then change based on that.
    public void discardThisCard(int x) {
        //switch ifs
        //this.player1Cards[10] = new Card(100, "Trash");
        rummyGameState.setPlayer1Cards(this.player1Cards);

        if (rummyGameState.getTurn()) {
            if (discardOn) {

                /*if(x == 10) {
                    rummyGameState.setDiscardedCard(this.player1Cards[10]);
                    //this.player1Cards[10] = new Card (99, "Trash");
                    return;
                }*/

                rummyGameState.setDiscardedCard(player1Cards[x]);
                discardedCard.setImageResource(R.drawable.blue_back);
                for(int i = x; i < 10; i++) {
                    player1Cards[i] = player1Cards[i+1];
                    //updateCards();
                }
                player1Cards[10] = new Card(100, "Trash");
                rummyGameState.setPlayer1Cards(player1Cards);
                rummyGameState.setCurrentStage("drawingStage");
                rummyGameState.toggleTurn();
                discardOn = !discardOn;
                discardButton.setText("Discard Off");
                if (rummyGameState.getAmountDrawn() < 31) {
                    dumbAI.act();
                } else {
                    rummyGameState.endGame(this.groupTotal);
                }
            }
        } else {
            if (discardOn) {
                rummyGameState.setDiscardedCard(player2Cards[x]);
                for(int i = x; i < 10; i++) {
                    player2Cards[i] = player2Cards[i+1];
                }
                player2Cards[10] = null;
                rummyGameState.setPlayer2Cards(player2Cards);
                rummyGameState.setCurrentStage("drawingStage");
                rummyGameState.toggleTurn();
                discardOn = !discardOn;
            }
        }
        updateCards();
        //discardButton.invalidate();
    }

    public boolean checkCards(Card[] cardList, int amountOfCards) {
        int counter = 0;
        for (int i = 0; i < amountOfCards - 1; i++) {
            if (!(cardList[i].getSuit().equals(cardList[i+1].getSuit()))) { //Checks if it they're all the same suit.
                for (int x = 0; x < amountOfCards - 1; x++) {
                    if (!(cardList[x].getNumber()==cardList[x+1].getNumber())) { //Checks if they're all the same number.
                        return false;
                    } else { // if they are the same number
                        counter ++;
                    }
                }
            } else { //if they are the same suit
                for (int y = 0; y < amountOfCards - 1; y++) {
                    if ((cardList[y].getNumber()+1) == cardList[y].getNumber()) {
                        counter ++;
                    }
                }
            }
        }
        if (counter + 1 == amountOfCards) { //this will only return true if they are all cards are in a run, or in a set.
            return true;
        }
        return false;
    }

    //DOTHIS : Make a command that prompts the user to wait until its their turn
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.groupButton: //WILL BREAK IF USER CLICKS CARDS MULTIPLE TIMES
                if (discardOn) {
                    break;
                }
                this.groupOn = !this.groupOn;
                if (groupOn) {
                    groupButton.setText("Group On");
                    this.groupAmount = 0;
                } else {
                    if (this.groupAmount > 2) {
                        if (checkCards(this.groupCards, this.groupAmount)) {
                            for (int i = 0; i < this.groupAmount - 1; i++) {
                                //adds a running total of the value of grouped cards in the players hand.
                                //Currently doesn't check if the player has already grouped up certain cards
                                //Also doesn't reduce this total if the grouped cards are thrown away.
                                this.groupTotal = this.groupTotal + this.groupCards[i].getNumber();
                            }
                        }
                    }
                    groupButton.setText("Group Off");
                    this.groupAmount = 0;
                }
                break;
            case R.id.discardButton:
                if (groupOn) {
                    break;
                }
                if (rummyGameState.getCurrentStage() == "discardStage") {
                    discardOn = !discardOn;
                    if(discardOn) {
                        discardButton.setText("Discard On");
                        discardButton.invalidate();
                    } else {
                        discardButton.setText("Discard Off");
                        discardButton.invalidate();
                    }
                } else {
                    //DOTHIS : Say something like wait until your turn!
                }
                break;
            case R.id.discardedCard:
                if (rummyGameState.getCurrentStage() == "drawingStage") {
                    if(rummyGameState.getTurn()) {
                        player1Cards[10] = rummyGameState.drawDiscard();
                        updateCard(player1Cards[10], card10);
                        discardedCard.setImageResource(R.drawable.blue_back);
                    }
                    else {
                        player2Cards[10] = rummyGameState.drawDiscard();
                    }
                    rummyGameState.setCurrentStage("discardStage");
                }
            case R.id.knockButton:
                    //DOTHIS : Not sure how to do this yet.
                break;
            case R.id.drawPile:
                //Amount drawn was 30, and i drew its 31.
                if (rummyGameState.getAmountDrawn() == 31) {
                    int whoWon = rummyGameState.endGame(this.groupTotal);
                    if (whoWon < 0) {
                        scoreView.setPlayer1("Player 2 Score : " + Integer.toString(-whoWon));
                        scoreView.setPlayer2("Player 1 Score : 0");
                    } else {
                        scoreView.setPlayer1("Player 2 Score : 0");
                        scoreView.setPlayer2("Player 1 Score : " + Integer.toString(whoWon));
                    }
                    drawPileCard.setImageResource(R.drawable.gray_back);
                    scoreView.invalidate();
                    break;
                }
                if (rummyGameState.getCurrentStage() == "drawingStage") {
                    if(rummyGameState.getTurn()) {
                        player1Cards[10] = rummyGameState.drawDraw();
                        updateCard(player1Cards[10], card10);
                    } else {
                        player2Cards[10] = rummyGameState.drawDraw();
                    }
                    rummyGameState.setCurrentStage("discardStage");
                }
                break;
            case R.id.card0:
                discardThisCard(0);
                if (groupOn) {
                this.groupCards[this.groupAmount] = this.player1Cards[groupAmount];
                    this.groupAmount++;
                }
                //updateCard(player1Cards[0], card0);
                break;
            case R.id.card1:
                discardThisCard(1);
                if (groupOn) {
                    this.groupCards[this.groupAmount] = this.player1Cards[groupAmount];
                    this.groupAmount++;
                }
                updateCard(player1Cards[1], card1);
                break;
            case R.id.card2:
                discardThisCard(2);
                if (groupOn) {
                    this.groupCards[this.groupAmount] = this.player1Cards[groupAmount];
                    this.groupAmount++;
                }
                updateCard(player1Cards[2], card2);
                break;
            case R.id.card3:
                discardThisCard(3);
                if (groupOn) {
                    this.groupCards[this.groupAmount] = this.player1Cards[groupAmount];
                    this.groupAmount++;
                }
                updateCard(player1Cards[3], card3);
                break;
            case R.id.card4:
                discardThisCard(4);
                if (groupOn) {
                    this.groupCards[this.groupAmount] = this.player1Cards[groupAmount];
                    this.groupAmount++;
                }
                updateCard(player1Cards[4], card4);
                break;
            case R.id.card5:
                discardThisCard(5);
                if (groupOn) {
                    this.groupCards[this.groupAmount] = this.player1Cards[groupAmount];
                    this.groupAmount++;
                }
                updateCard(player1Cards[5], card5);
                break;
            case R.id.card6:
                discardThisCard(6);
                if (groupOn) {
                    this.groupCards[this.groupAmount] = this.player1Cards[groupAmount];
                    this.groupAmount++;
                }
                updateCard(player1Cards[6], card6);
                break;
            case R.id.card7:
                discardThisCard(7);
                if (groupOn) {
                    this.groupCards[this.groupAmount] = this.player1Cards[groupAmount];
                    this.groupAmount++;
                }
                updateCard(player1Cards[7], card7);
                break;
            case R.id.card8:
                discardThisCard(8);
                if (groupOn) {
                    this.groupCards[this.groupAmount] = this.player1Cards[groupAmount];
                    this.groupAmount++;
                }
                updateCard(player1Cards[8], card8);
                break;
            case R.id.card9:
                discardThisCard(9);
                if (groupOn) {
                    this.groupCards[this.groupAmount] = this.player1Cards[groupAmount];
                    this.groupAmount++;
                }
                updateCard(player1Cards[9], card9);
                break;
            case R.id.card10:
                discardThisCard(10);
                if (groupOn) {
                    this.groupCards[this.groupAmount] = this.player1Cards[groupAmount];
                    this.groupAmount++;
                }
                updateCard(player1Cards[10], card10);
                break;
        }
    }

    public void updateCard(Card card, ImageView cardView) {
        switch (card.getNumber()) {
            case 1:
                if (card.getSuit() == "Diamonds") {
                    cardView.setImageResource(R.drawable.acediamond);
                    cardView.invalidate();
                } else if (card.getSuit() == "Hearts" ) {
                    cardView.setImageResource(R.drawable.aceheart);
                    cardView.invalidate();
                } else if (card.getSuit() == "Spades") {
                    cardView.setImageResource(R.drawable.acespade);
                    cardView.invalidate();
                } else if (card.getSuit() == "Clubs") {
                    cardView.setImageResource(R.drawable.aceclub);
                    cardView.invalidate();
                }
                break;
            case 2:
                if (card.getSuit() == "Diamonds") {
                    cardView.setImageResource(R.drawable.twodiamond);
                    cardView.invalidate();
                } else if (card.getSuit() == "Hearts" ) {
                    cardView.setImageResource(R.drawable.twoheart);
                    cardView.invalidate();
                } else if (card.getSuit() == "Spades") {
                    cardView.setImageResource(R.drawable.twospade);
                    cardView.invalidate();
                } else if (card.getSuit() == "Clubs") {
                    cardView.setImageResource(R.drawable.twoclub);
                    cardView.invalidate();
                }
                break;
            case 3:
                if (card.getSuit() == "Diamonds") {
                    cardView.setImageResource(R.drawable.threediamond);
                    cardView.invalidate();
                } else if (card.getSuit() == "Hearts" ) {
                    cardView.setImageResource(R.drawable.threeheart);
                    cardView.invalidate();
                } else if (card.getSuit() == "Spades") {
                    cardView.setImageResource(R.drawable.threespade);
                    cardView.invalidate();
                } else if (card.getSuit() == "Clubs") {
                    cardView.setImageResource(R.drawable.threeclub);
                    cardView.invalidate();
                }
                break;
            case 4:
                if (card.getSuit() == "Diamonds") {
                    cardView.setImageResource(R.drawable.fourdiamond);
                    cardView.invalidate();
                } else if (card.getSuit() == "Hearts" ) {
                    cardView.setImageResource(R.drawable.fourheart);
                    cardView.invalidate();
                } else if (card.getSuit() == "Spades") {
                    cardView.setImageResource(R.drawable.fourspade);
                    cardView.invalidate();
                } else if (card.getSuit() == "Clubs") {
                    cardView.setImageResource(R.drawable.fourclub);
                    cardView.invalidate();
                }
                break;
            case 5:
                if (card.getSuit() == "Diamonds") {
                    cardView.setImageResource(R.drawable.fivediamond);
                    cardView.invalidate();
                } else if (card.getSuit() == "Hearts" ) {
                    cardView.setImageResource(R.drawable.fiveheart);
                    cardView.invalidate();
                } else if (card.getSuit() == "Spades") {
                    cardView.setImageResource(R.drawable.fivespade);
                    cardView.invalidate();
                } else if (card.getSuit() == "Clubs") {
                    cardView.setImageResource(R.drawable.fiveclub);
                    cardView.invalidate();
                }
                break;
            case 6:
                if (card.getSuit() == "Diamonds") {
                    cardView.setImageResource(R.drawable.sixdiamond);
                    cardView.invalidate();
                } else if (card.getSuit() == "Hearts" ) {
                    cardView.setImageResource(R.drawable.sixheart);
                    cardView.invalidate();
                } else if (card.getSuit() == "Spades") {
                    cardView.setImageResource(R.drawable.sixspade);
                    cardView.invalidate();
                } else if (card.getSuit() == "Clubs") {
                    cardView.setImageResource(R.drawable.sixclub);
                    cardView.invalidate();
                }
                break;
            case 7:
                if (card.getSuit() == "Diamonds") {
                    cardView.setImageResource(R.drawable.sevendiamond);
                    cardView.invalidate();
                } else if (card.getSuit() == "Hearts" ) {
                    cardView.setImageResource(R.drawable.sevenheart);
                    cardView.invalidate();
                } else if (card.getSuit() == "Spades") {
                    cardView.setImageResource(R.drawable.sevenspade);
                    cardView.invalidate();
                } else if (card.getSuit() == "Clubs") {
                    cardView.setImageResource(R.drawable.sevenclub);
                    cardView.invalidate();
                }
                break;
            case 8:
                if (card.getSuit() == "Diamonds") {
                    cardView.setImageResource(R.drawable.eightdiamond);
                    cardView.invalidate();
                } else if (card.getSuit() == "Hearts" ) {
                    cardView.setImageResource(R.drawable.eightheart);
                    cardView.invalidate();
                } else if (card.getSuit() == "Spades") {
                    cardView.setImageResource(R.drawable.eightspade);
                    cardView.invalidate();
                } else if (card.getSuit() == "Clubs") {
                    cardView.setImageResource(R.drawable.eightclub);
                    cardView.invalidate();
                }
                break;
            case 9:
                if (card.getSuit() == "Diamonds") {
                    cardView.setImageResource(R.drawable.ninediamond);
                    cardView.invalidate();
                } else if (card.getSuit() == "Hearts" ) {
                    cardView.setImageResource(R.drawable.nineheart);
                    cardView.invalidate();
                } else if (card.getSuit() == "Spades") {
                    cardView.setImageResource(R.drawable.ninespade);
                    cardView.invalidate();
                } else if (card.getSuit() == "Clubs") {
                    cardView.setImageResource(R.drawable.nineclub);
                    cardView.invalidate();
                }
                break;
            case 10:
                if (card.getSuit() == "Diamonds") {
                    cardView.setImageResource(R.drawable.tendiamond);
                    cardView.invalidate();
                } else if (card.getSuit() == "Hearts" ) {
                    cardView.setImageResource(R.drawable.tenheart);
                    cardView.invalidate();
                } else if (card.getSuit() == "Spades") {
                    cardView.setImageResource(R.drawable.tenspade);
                    cardView.invalidate();
                } else if (card.getSuit() == "Clubs") {
                    cardView.setImageResource(R.drawable.tenclub);
                    cardView.invalidate();
                }
                break;
            case 11:
                if (card.getSuit() == "Diamonds") {
                    cardView.setImageResource(R.drawable.jackdiamond);
                    cardView.invalidate();
                } else if (card.getSuit() == "Hearts" ) {
                    cardView.setImageResource(R.drawable.jackheart);
                    cardView.invalidate();
                } else if (card.getSuit() == "Spades") {
                    cardView.setImageResource(R.drawable.jackspade);
                    cardView.invalidate();
                } else if (card.getSuit() == "Clubs") {
                    cardView.setImageResource(R.drawable.jackclub);
                    cardView.invalidate();
                }
                break;
            case 12:
                if (card.getSuit() == "Diamonds") {
                    cardView.setImageResource(R.drawable.queendiamond);
                    cardView.invalidate();
                } else if (card.getSuit() == "Hearts" ) {
                    cardView.setImageResource(R.drawable.queenheart);
                    cardView.invalidate();
                } else if (card.getSuit() == "Spades") {
                    cardView.setImageResource(R.drawable.queenspade);
                    cardView.invalidate();
                } else if (card.getSuit() == "Clubs") {
                    cardView.setImageResource(R.drawable.queenclub);
                    cardView.invalidate();
                }
                break;
            case 13:
                if (card.getSuit() == "Diamonds") {
                    cardView.setImageResource(R.drawable.kingdiamond);
                    cardView.invalidate();
                } else if (card.getSuit() == "Hearts" ) {
                    cardView.setImageResource(R.drawable.kingheart);
                    cardView.invalidate();
                } else if (card.getSuit() == "Spades") {
                    cardView.setImageResource(R.drawable.kingspade);
                    cardView.invalidate();
                } else if (card.getSuit() == "Clubs") {
                    cardView.setImageResource(R.drawable.kingclub);
                    cardView.invalidate();
                }
                break;
            default:
                cardView.setImageResource(R.drawable.blue_back);
                break;
        }
    }

    public void autoGin() {

    }
}
