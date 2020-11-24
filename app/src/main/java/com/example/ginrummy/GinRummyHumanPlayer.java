package com.example.ginrummy;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.R;
import com.example.game.GameFramework.GameHumanPlayer;
import com.example.game.GameFramework.GameMainActivity;
import com.example.game.GameFramework.infoMessage.GameInfo;
import com.example.game.GameFramework.infoMessage.IllegalMoveInfo;
import com.example.game.GameFramework.infoMessage.NotYourTurnInfo;
import com.example.game.GameFramework.utilities.Logger;


public class GinRummyHumanPlayer extends GameHumanPlayer implements View.OnClickListener {
    private boolean discardOn = false;
    private boolean groupOn = false;

    private int whichPlayer;

    private GinRummyGameState state;
    private ScoreView scoreView;
    private Activity myActivity;
    private int layoutId;
    private static final String TAG = "GinRummyHumanPlayer";

    Button discardButton;
    Button groupButton;

    // Instance variables for all the on screen card displays
    private ImageView card0;
    private ImageView card1;
    private ImageView card2;
    private ImageView card3;
    private ImageView card4;
    private ImageView card5;
    private ImageView card6;
    private ImageView card7;
    private ImageView card8;
    private ImageView card9;
    private ImageView card10;
    private ImageView drawPileCard;
    private ImageView discardedCard;

    private Card[] player1Cards;

    /**
     * constructor
     *
     * @param name the name of the player
     */
    /*public GinRummyHumanPlayer(String name, int whichPlayer) {
        super(name);
        this.whichPlayer = whichPlayer;
    }*/

    public GinRummyHumanPlayer(String name, int layoutId) {
        super(name);
        this.layoutId = layoutId;
    }

    @Override
    public View getTopView() {
        return myActivity.findViewById(R.id.top_gui_layout);
    }

    @Override
    public void receiveInfo(GameInfo info) {
        if(scoreView == null) {
            return;
        }
        if(info instanceof IllegalMoveInfo || info instanceof NotYourTurnInfo) {
            scoreView.flash(Color.RED, 50);
        }
        else if (!(info instanceof GinRummyGameState)) {
            return;
        }
        else {
            state = (GinRummyGameState)info;
            scoreView.setState(state);
            scoreView.invalidate();
            Logger.log(TAG,"receiving");
        }
        state = (GinRummyGameState) info;
        player1Cards = state.getPlayer1Cards();
        updateCards(state);
    }

    @Override
    public void setAsGui(GameMainActivity activity) {
        myActivity = activity;
        activity.setContentView(layoutId);
        scoreView = (ScoreView)myActivity.findViewById(R.id.surfaceView);
        scoreView.setState(state);

        card0 = (ImageView)myActivity.findViewById(R.id.card0);
        card1 = (ImageView)myActivity.findViewById(R.id.card1);
        card2 = (ImageView)myActivity.findViewById(R.id.card2);
        card3 = (ImageView)myActivity.findViewById(R.id.card3);
        card4 = (ImageView)myActivity.findViewById(R.id.card4);
        card5 = (ImageView)myActivity.findViewById(R.id.card5);
        card6 = (ImageView)myActivity.findViewById(R.id.card6);
        card7 = (ImageView)myActivity.findViewById(R.id.card7);
        card8 = (ImageView)myActivity.findViewById(R.id.card8);
        card9 = (ImageView)myActivity.findViewById(R.id.card9);
        card10 = (ImageView)myActivity.findViewById(R.id.card10);

        //updateCards();
    }

    public void updateCards(GinRummyGameState gameState) {
        player1Cards = gameState.getPlayer1Cards();
        //updateCard(gameState.getDiscardedCard(), discardedCard);
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
            card10.setImageResource(R.drawable.blue_back);
        }
        else {
            updateCard(player1Cards[10], card10);
        }
    }

    @Override
    public void onClick(View view) {

    }

    /*@Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.groupButton: //WILL BREAK IF USER CLICKS CARDS MULTIPLE TIMES
                if (discardOn) {
                    break;
                }
                this.groupOn = !this.groupOn;
                if (groupOn) {
                    gameState.groupButton.setText("Group On");
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
    }*/

    public void updateCard(Card card, ImageView cardView) {
        switch (card.getNumber()) {
            case 1:
                switch (card.getSuit()) {
                    case "Diamonds":
                        cardView.setImageResource(R.drawable.acediamond);
                        cardView.invalidate();
                        break;
                    case "Hearts":
                        cardView.setImageResource(R.drawable.aceheart);
                        cardView.invalidate();
                        break;
                    case "Spades":
                        cardView.setImageResource(R.drawable.acespade);
                        cardView.invalidate();
                        break;
                    case "Clubs":
                        cardView.setImageResource(R.drawable.aceclub);
                        cardView.invalidate();
                        break;
                }
                break;
            case 2:
                switch (card.getSuit()) {
                    case "Diamonds":
                        cardView.setImageResource(R.drawable.twodiamond);
                        cardView.invalidate();
                        break;
                    case "Hearts":
                        cardView.setImageResource(R.drawable.twoheart);
                        cardView.invalidate();
                        break;
                    case "Spades":
                        cardView.setImageResource(R.drawable.twospade);
                        cardView.invalidate();
                        break;
                    case "Clubs":
                        cardView.setImageResource(R.drawable.twoclub);
                        cardView.invalidate();
                        break;
                }
                break;
            case 3:
                switch (card.getSuit()) {
                    case "Diamonds":
                        cardView.setImageResource(R.drawable.threediamond);
                        cardView.invalidate();
                        break;
                    case "Hearts":
                        cardView.setImageResource(R.drawable.threeheart);
                        cardView.invalidate();
                        break;
                    case "Spades":
                        cardView.setImageResource(R.drawable.threespade);
                        cardView.invalidate();
                        break;
                    case "Clubs":
                        cardView.setImageResource(R.drawable.threeclub);
                        cardView.invalidate();
                        break;
                }
                break;
            case 4:
                switch (card.getSuit()) {
                    case "Diamonds":
                        cardView.setImageResource(R.drawable.fourdiamond);
                        cardView.invalidate();
                        break;
                    case "Hearts":
                        cardView.setImageResource(R.drawable.fourheart);
                        cardView.invalidate();
                        break;
                    case "Spades":
                        cardView.setImageResource(R.drawable.fourspade);
                        cardView.invalidate();
                        break;
                    case "Clubs":
                        cardView.setImageResource(R.drawable.fourclub);
                        cardView.invalidate();
                        break;
                }
                break;
            case 5:
                switch (card.getSuit()) {
                    case "Diamonds":
                        cardView.setImageResource(R.drawable.fivediamond);
                        cardView.invalidate();
                        break;
                    case "Hearts":
                        cardView.setImageResource(R.drawable.fiveheart);
                        cardView.invalidate();
                        break;
                    case "Spades":
                        cardView.setImageResource(R.drawable.fivespade);
                        cardView.invalidate();
                        break;
                    case "Clubs":
                        cardView.setImageResource(R.drawable.fiveclub);
                        cardView.invalidate();
                        break;
                }
                break;
            case 6:
                switch (card.getSuit()) {
                    case "Diamonds":
                        cardView.setImageResource(R.drawable.sixdiamond);
                        cardView.invalidate();
                        break;
                    case "Hearts":
                        cardView.setImageResource(R.drawable.sixheart);
                        cardView.invalidate();
                        break;
                    case "Spades":
                        cardView.setImageResource(R.drawable.sixspade);
                        cardView.invalidate();
                        break;
                    case "Clubs":
                        cardView.setImageResource(R.drawable.sixclub);
                        cardView.invalidate();
                        break;
                }
                break;
            case 7:
                switch (card.getSuit()) {
                    case "Diamonds":
                        cardView.setImageResource(R.drawable.sevendiamond);
                        cardView.invalidate();
                        break;
                    case "Hearts":
                        cardView.setImageResource(R.drawable.sevenheart);
                        cardView.invalidate();
                        break;
                    case "Spades":
                        cardView.setImageResource(R.drawable.sevenspade);
                        cardView.invalidate();
                        break;
                    case "Clubs":
                        cardView.setImageResource(R.drawable.sevenclub);
                        cardView.invalidate();
                        break;
                }
                break;
            case 8:
                switch (card.getSuit()) {
                    case "Diamonds":
                        cardView.setImageResource(R.drawable.eightdiamond);
                        cardView.invalidate();
                        break;
                    case "Hearts":
                        cardView.setImageResource(R.drawable.eightheart);
                        cardView.invalidate();
                        break;
                    case "Spades":
                        cardView.setImageResource(R.drawable.eightspade);
                        cardView.invalidate();
                        break;
                    case "Clubs":
                        cardView.setImageResource(R.drawable.eightclub);
                        cardView.invalidate();
                        break;
                }
                break;
            case 9:
                switch (card.getSuit()) {
                    case "Diamonds":
                        cardView.setImageResource(R.drawable.ninediamond);
                        cardView.invalidate();
                        break;
                    case "Hearts":
                        cardView.setImageResource(R.drawable.nineheart);
                        cardView.invalidate();
                        break;
                    case "Spades":
                        cardView.setImageResource(R.drawable.ninespade);
                        cardView.invalidate();
                        break;
                    case "Clubs":
                        cardView.setImageResource(R.drawable.nineclub);
                        cardView.invalidate();
                        break;
                }
                break;
            case 10:
                switch (card.getSuit()) {
                    case "Diamonds":
                        cardView.setImageResource(R.drawable.tendiamond);
                        cardView.invalidate();
                        break;
                    case "Hearts":
                        cardView.setImageResource(R.drawable.tenheart);
                        cardView.invalidate();
                        break;
                    case "Spades":
                        cardView.setImageResource(R.drawable.tenspade);
                        cardView.invalidate();
                        break;
                    case "Clubs":
                        cardView.setImageResource(R.drawable.tenclub);
                        cardView.invalidate();
                        break;
                }
                break;
            case 11:
                switch (card.getSuit()) {
                    case "Diamonds":
                        cardView.setImageResource(R.drawable.jackdiamond);
                        cardView.invalidate();
                        break;
                    case "Hearts":
                        cardView.setImageResource(R.drawable.jackheart);
                        cardView.invalidate();
                        break;
                    case "Spades":
                        cardView.setImageResource(R.drawable.jackspade);
                        cardView.invalidate();
                        break;
                    case "Clubs":
                        cardView.setImageResource(R.drawable.jackclub);
                        cardView.invalidate();
                        break;
                }
                break;
            case 12:
                switch (card.getSuit()) {
                    case "Diamonds":
                        cardView.setImageResource(R.drawable.queendiamond);
                        cardView.invalidate();
                        break;
                    case "Hearts":
                        cardView.setImageResource(R.drawable.queenheart);
                        cardView.invalidate();
                        break;
                    case "Spades":
                        cardView.setImageResource(R.drawable.queenspade);
                        cardView.invalidate();
                        break;
                    case "Clubs":
                        cardView.setImageResource(R.drawable.queenclub);
                        cardView.invalidate();
                        break;
                }
                break;
            case 13:
                switch (card.getSuit()) {
                    case "Diamonds":
                        cardView.setImageResource(R.drawable.kingdiamond);
                        cardView.invalidate();
                        break;
                    case "Hearts":
                        cardView.setImageResource(R.drawable.kingheart);
                        cardView.invalidate();
                        break;
                    case "Spades":
                        cardView.setImageResource(R.drawable.kingspade);
                        cardView.invalidate();
                        break;
                    case "Clubs":
                        cardView.setImageResource(R.drawable.kingclub);
                        cardView.invalidate();
                        break;
                }
                break;
            default:
                cardView.setImageResource(R.drawable.blue_back);
                break;
        }
    }
}
