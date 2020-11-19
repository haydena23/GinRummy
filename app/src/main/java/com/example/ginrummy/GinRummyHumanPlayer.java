package com.example.ginrummy;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.R;
import com.example.game.GameFramework.GameHumanPlayer;
import com.example.game.GameFramework.GameMainActivity;
import com.example.game.GameFramework.infoMessage.GameInfo;

public class GinRummyHumanPlayer extends GameHumanPlayer implements View.OnClickListener {
    Button discardButton;
    Button groupButton;

    // Instance variables for all the on screen card displays
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

    private boolean discardOn = false;
    private boolean groupOn = false;

    private int whichPlayer;

    GinRummyGameState gameState;
    private Activity myActivity;

    /**
     * constructor
     *
     * @param name the name of the player
     */
    public GinRummyHumanPlayer(String name, int whichPlayer) {
        super(name);
        this.whichPlayer = whichPlayer;
    }

    @Override
    public View getTopView() {
        return null;
    }

    @Override
    public void receiveInfo(GameInfo info) {
        gameState = (GinRummyGameState) info;
    }

    @Override
    public void setAsGui(GameMainActivity activity) {
        myActivity = activity;

        activity.setContentView(R.layout.activity_main);

        Button discardButton = myActivity.findViewById(R.id.discardButton);
        Button quitButton = myActivity.findViewById(R.id.quitButton);
        Button knockButton = myActivity.findViewById(R.id.knockButton);
        Button groupButton = myActivity.findViewById(R.id.groupButton);

        ImageView discardedCard = myActivity.findViewById(R.id.discardedCard);
        ImageView drawPile = myActivity.findViewById(R.id.drawPile);

        ImageView card0 = myActivity.findViewById(R.id.card0);
        ImageView card1 = myActivity.findViewById(R.id.card1);
        ImageView card2 = myActivity.findViewById(R.id.card2);
        ImageView card3 = myActivity.findViewById(R.id.card3);
        ImageView card4 = myActivity.findViewById(R.id.card4);
        ImageView card5 = myActivity.findViewById(R.id.card5);
        ImageView card6 = myActivity.findViewById(R.id.card6);
        ImageView card7 = myActivity.findViewById(R.id.card7);
        ImageView card8 = myActivity.findViewById(R.id.card8);
        ImageView card9 = myActivity.findViewById(R.id.card9);
        ImageView card10 = myActivity.findViewById(R.id.card10);

        ScoreView scoreView = myActivity.findViewById(R.id.surfaceView);

        quitButton.setOnClickListener(this);
        knockButton.setOnClickListener(this);
        discardButton.setOnClickListener(this);
        groupButton.setOnClickListener(this);

        discardedCard.setOnClickListener(this);

        drawPile.setOnClickListener(this);

        card1.setOnClickListener(this);
        card2.setOnClickListener(this);
        card3.setOnClickListener(this);
        card4.setOnClickListener(this);
        card5.setOnClickListener(this);
        card6.setOnClickListener(this);
        card7.setOnClickListener(this);
        card8.setOnClickListener(this);
        card9.setOnClickListener(this);
        card10.setOnClickListener(this);
        card0.setOnClickListener(this);
    }

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
                            for (int x = 0; x<this.groupAmount; x++) {
                                groupCards[x].toggleIsPaired();
                            }
                            for (int i = 0; i < this.groupAmount; i++) {
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
                if (!(rummyGameState.getCurrentStage().equals("discardStage"))) {
                    break;
                }
                int i = 0;
                for (Card c : player1Cards) {
                    i = i + c.getNumber();
                }
                i = i - groupTotal;
                if (i < 10) {
                    scoreView.setPlayer1("Player 1 Score : 10");
                    scoreView.invalidate();
                }
                break;
            case R.id.drawPile:
                //Amount drawn was 30, and i drew its 31.
                if (rummyGameState.getAmountDrawn() == 31) {
                    int whoWon = rummyGameState.endGame(this.groupTotal);
                    if (whoWon > 0) {
                        scoreView.setPlayer1("Player 2 Score : " + Integer.toString(whoWon));
                        scoreView.setPlayer2("Player 1 Score : 0");
                    } else {
                        scoreView.setPlayer1("Player 2 Score : 0");
                        scoreView.setPlayer2("Player 1 Score : " + Integer.toString(-whoWon));
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
}
