package com.example.ginrummy;

import android.view.View;

public class Controller implements View.OnClickListener{
    private boolean discardOn = false;
    private RummyGameState rummyGameState;
    private Card[] player1Cards;
    private Card[] player2Cards;

    public Controller(RummyGameState rummyGameState) {
        this.rummyGameState = rummyGameState;
        this.player1Cards = rummyGameState.getPlayer1Cards();
        this.player2Cards = rummyGameState.getPlayer2Cards();
    }

    //DOTHIS : Current Issues - we have to check what player called it, then change based on that.
    public void discardThisCard(int x) {
        //switch ifs
        this.player1Cards[10] = new Card(100, "Trash");
        rummyGameState.setPlayer1Cards(this.player1Cards);

        if (rummyGameState.getTurn()) {
            if (discardOn) {

                if(x == 10) {
                    rummyGameState.setDiscardedCard(this.player1Cards[10]);
                    this.player1Cards[10] = new Card (100, "Trash");
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
                } else {
                    //DOTHIS : Say something like wait until your turn!
                }
                break;
            case R.id.knockButton:
                    //DOTHIS : Not sure how to do this yet.
                break;
            case R.id.drawPile:
                if (rummyGameState.getCurrentStage() == "drawingStage") {
                    if(rummyGameState.getTurn()) {
                        player1Cards[10] = rummyGameState.drawDraw();
                        rummyGameState.setCurrentStage("discardStage");
                    } else {
                        player2Cards[10] = rummyGameState.drawDraw();
                        rummyGameState.setCurrentStage("discardStage");
                    }
                } else {
                    //DOTHIS : Say something like wait until your turn!
                }
                break;
            case R.id.discardedCard:
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
