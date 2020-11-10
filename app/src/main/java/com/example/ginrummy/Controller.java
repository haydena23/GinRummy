package com.example.ginrummy;

import android.media.Image;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Controller implements View.OnClickListener{
    private boolean discardOn = false;
    private RummyGameState rummyGameState;
    private Card[] player1Cards;
    private Card[] player2Cards;

    Button discardButton;

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

    public Controller(RummyGameState rummyGameState, Button discardButton,
                      ImageView card0, ImageView card1, ImageView card2, ImageView card3,
                      ImageView card4, ImageView card5, ImageView card6, ImageView card7,
                      ImageView card8, ImageView card9, ImageView card10, ImageView drawPileCard) {
        this.rummyGameState = rummyGameState;
        this.player1Cards = rummyGameState.getPlayer1Cards();
        this.player2Cards = rummyGameState.getPlayer2Cards();
        this.discardButton = discardButton;

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

        updateCards();
    }

    public void updateCards() {
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
                for(int i = x; i < 10; i++) {
                    player1Cards[i] = player1Cards[i+1];
                    //updateCards();
                }
                player1Cards[10] = null;
                rummyGameState.setPlayer1Cards(player1Cards);
                rummyGameState.setCurrentStage("drawingStage");
                rummyGameState.toggleTurn();
                discardOn = !discardOn;
                //updateCards();
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

    //DOTHIS : Make a command that prompts the user to wait until its their turn

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.discardButton:
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
                if (rummyGameState.getCurrentStage() == "drawingStage") {
                    if(rummyGameState.getTurn()) {
                        player1Cards[10] = rummyGameState.drawDraw();
                        updateCard(player1Cards[10], card10);
                    } else {
                        player2Cards[10] = rummyGameState.drawDraw();
                    }
                    rummyGameState.setCurrentStage("discardStage");
                } else {
                    //DOTHIS : Say something like wait until your turn!
                }
                break;
            case R.id.card0:
                discardThisCard(0);
                updateCard(player1Cards[0], card0);
                break;
            case R.id.card1:
                discardThisCard(1);
                updateCard(player1Cards[1], card1);
                break;
            case R.id.card2:
                discardThisCard(2);
                updateCard(player1Cards[2], card2);
                break;
            case R.id.card3:
                discardThisCard(3);
                updateCard(player1Cards[3], card3);
                break;
            case R.id.card4:
                discardThisCard(4);
                updateCard(player1Cards[4], card4);
                break;
            case R.id.card5:
                discardThisCard(5);
                updateCard(player1Cards[5], card5);
                break;
            case R.id.card6:
                discardThisCard(6);
                updateCard(player1Cards[6], card6);
                break;
            case R.id.card7:
                discardThisCard(7);
                updateCard(player1Cards[7], card7);
                break;
            case R.id.card8:
                discardThisCard(8);
                updateCard(player1Cards[8], card8);
                break;
            case R.id.card9:
                discardThisCard(9);
                updateCard(player1Cards[9], card9);
                break;
            case R.id.card10:
                discardThisCard(10);
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
}
