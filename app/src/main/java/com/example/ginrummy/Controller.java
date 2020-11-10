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

    ImageView card1;

    public Controller(RummyGameState rummyGameState, Button discardButton, ImageView card1) {
        this.rummyGameState = rummyGameState;
        this.player1Cards = rummyGameState.getPlayer1Cards();
        this.player2Cards = rummyGameState.getPlayer2Cards();
        this.discardButton = discardButton;
        this.card1 = card1;
    }

    public void updateCard(Card card) {
        switch (card.getNumber()) {
            case 1:
                if (card.getSuit() == "Diamonds") {
                    card1.setImageDrawable(R.drawable.acediamond);
                } else if (card.getSuit() == "Hearts" ) {

                } else if (card.getSuit() == "Spades") {

                } else if (card.getSuit() == "Clubs") {

                }
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;
            case 10:
                break;
            case 11:
                break;
            case 12:
                break;
            case 13:
                break;
        }
    }
    //DOTHIS : Current Issues - we have to check what player called it, then change based on that.
    public void discardThisCard(int x) {
        //switch ifs
        //this.player1Cards[10] = new Card(100, "Trash");
        rummyGameState.setPlayer1Cards(this.player1Cards);

        if (rummyGameState.getTurn()) {
            if (discardOn) {

                if(x == 10) {
                    rummyGameState.setDiscardedCard(this.player1Cards[10]);
                    //this.player1Cards[10] = new Card (99, "Trash");
                    return;
                }

                rummyGameState.setDiscardedCard(player1Cards[x]);
                for(int i = x; i < 10; i++) {
                    player1Cards[i] = player1Cards[i+1];
                }
                player1Cards[10] = null;
                rummyGameState.setPlayer1Cards(player1Cards);
                rummyGameState.setCurrentStage("drawingStage");
                rummyGameState.toggleTurn();
                discardOn = !discardOn;
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
                break;
            case R.id.card1:
                discardThisCard(1);
                break;
            case R.id.card2:
                discardThisCard(2);
                break;
            case R.id.card3:
                discardThisCard(3);
                break;
            case R.id.card4:
                discardThisCard(4);
                break;
            case R.id.card5:
                discardThisCard(5);
                break;
            case R.id.card6:
                discardThisCard(6);
                break;
            case R.id.card7:
                discardThisCard(7);
                break;
            case R.id.card8:
                discardThisCard(8);
                break;
            case R.id.card9:
                discardThisCard(9);
                break;
            case R.id.card10:
                discardThisCard(10);
                break;
        }
    }
}
