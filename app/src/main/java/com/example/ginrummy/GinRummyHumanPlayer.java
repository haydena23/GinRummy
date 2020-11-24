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

    private int amountGrouped;
    private int valueGrouped; //value of grouped cards

    private Card[] groupedCards;

    private String stage;

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
    private ImageView discardCard;

    private Card[] player1Cards;

    public GinRummyHumanPlayer(String name, int layoutId) {
        super(name);
        this.layoutId = layoutId;

        groupedCards = new Card[12];
        amountGrouped = 0;
        valueGrouped = 0;

        stage = "drawingStage";
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
            state = (GinRummyGameState) info;
            player1Cards = state.getPlayer1Cards();
            updateCards(state);
            scoreView.setState(state);
            scoreView.invalidate();
            Logger.log(TAG,"receiving");
        }
    }

    @Override
    public void setAsGui(GameMainActivity activity) {
        myActivity = activity;
        activity.setContentView(layoutId);
        scoreView = (ScoreView)myActivity.findViewById(R.id.surfaceView);
        scoreView.setState(state);

        card0 = (ImageView)myActivity.findViewById(R.id.card0);
        card0.setOnClickListener(this);

        card1 = (ImageView)myActivity.findViewById(R.id.card1);
        card1.setOnClickListener(this);

        card2 = (ImageView)myActivity.findViewById(R.id.card2);
        card2.setOnClickListener(this);

        card3 = (ImageView)myActivity.findViewById(R.id.card3);
        card3.setOnClickListener(this);

        card4 = (ImageView)myActivity.findViewById(R.id.card4);
        card4.setOnClickListener(this);

        card5 = (ImageView)myActivity.findViewById(R.id.card5);
        card5.setOnClickListener(this);

        card6 = (ImageView)myActivity.findViewById(R.id.card6);
        card6.setOnClickListener(this);

        card7 = (ImageView)myActivity.findViewById(R.id.card7);
        card7.setOnClickListener(this);

        card8 = (ImageView)myActivity.findViewById(R.id.card8);
        card8.setOnClickListener(this);

        card9 = (ImageView)myActivity.findViewById(R.id.card9);
        card9.setOnClickListener(this);

        card10 = (ImageView)myActivity.findViewById(R.id.card10);
        card10.setOnClickListener(this);

        discardCard = (ImageView)myActivity.findViewById(R.id.discardCard);
        discardCard.setOnClickListener(this);
        drawPileCard = (ImageView)myActivity.findViewById(R.id.drawPile);
        drawPileCard.setOnClickListener(this);

        discardButton = (Button)myActivity.findViewById(R.id.discardButton);
        discardButton.setOnClickListener(this);
        groupButton = (Button)myActivity.findViewById(R.id.groupButton);
        groupButton.setOnClickListener(this);

        drawPileCard.setImageResource(R.drawable.blue_back);
    }

    public void updateCards(GinRummyGameState gameState) {
        player1Cards = gameState.getPlayer1Cards();

        updateCard(gameState.getDiscardedCard(), discardCard);

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
        switch (view.getId()) {
            case R.id.groupButton:
                if (discardOn) {
                    break;
                }
                this.groupOn = !this.groupOn;
                if (groupOn) {
                    groupButton.setText("Group On");
                    amountGrouped = 0;
                } else {
                    if (amountGrouped > 2) {
                        game.sendAction(new GinRummyGroupAction
                                (this, groupedCards, amountGrouped));
                    }
                    groupButton.setText("Group Off");
                    amountGrouped = 0;
                }
                groupButton.invalidate();
                break;

            case R.id.discardButton:
                if (groupOn) {
                    break;
                }
                if (state.getCurrentStage().equals("discardStage")) {
                    discardOn = !discardOn;
                    if(discardOn) {
                        discardButton.setText("Discard On");
                        discardButton.invalidate();
                    } else {
                        discardButton.setText("Discard Off");
                        discardButton.invalidate();
                    }
                }
                break;

            case R.id.discardCard:
                    game.sendAction(new GinRummyDrawDiscardAction(this));

            case R.id.knockButton:
                if (!(state.equals("discardStage"))) {
                    break;
                }
                int i = 0;
                for (Card c : player1Cards) {
                    i = i + c.getNumber();
                }
                i = i - valueGrouped;
                if (i < 10) {
                    scoreView.setPlayer1("Player 1 Score : 10");
                    scoreView.invalidate();
                }
                break;

            case R.id.drawPile:
                game.sendAction(new GinRummyDrawAction(this));
                break;

            case R.id.card0:
                if (discardOn) {
                    game.sendAction(new GinRummyDiscardAction(this, 0));
                    discardOn = !discardOn;
                    discardButton.setText("Discard Off");
                }

                if (groupOn) {
                    groupedCards[amountGrouped] = this.player1Cards[amountGrouped];
                    amountGrouped++;
                }
                break;

            case R.id.card1:
                if(discardOn) {
                    game.sendAction(new GinRummyDiscardAction(this, 1));
                    discardOn = !discardOn;
                    discardButton.setText("Discard Off");
                }

                if (groupOn) {
                    groupedCards[amountGrouped] = this.player1Cards[amountGrouped];
                    amountGrouped++;
                }
                break;

            case R.id.card2:
                if(discardOn) {
                    game.sendAction(new GinRummyDiscardAction(this, 2));
                    discardOn = !discardOn;
                    discardButton.setText("Discard Off");
                }
                if (groupOn) {
                    groupedCards[amountGrouped] = this.player1Cards[amountGrouped];
                    amountGrouped++;
                }
                break;

            case R.id.card3:
                if(discardOn) {
                    game.sendAction(new GinRummyDiscardAction(this, 3));
                    discardOn = !discardOn;
                    discardButton.setText("Discard Off");
                }
                if (groupOn) {
                    groupedCards[amountGrouped] = this.player1Cards[amountGrouped];
                    amountGrouped++;
                }
                break;

            case R.id.card4:
                if(discardOn) {
                    game.sendAction(new GinRummyDiscardAction(this, 4));
                    discardOn = !discardOn;
                    discardButton.setText("Discard Off");
                }
                if (groupOn) {
                    groupedCards[amountGrouped] = this.player1Cards[amountGrouped];
                    amountGrouped++;
                }
                break;

            case R.id.card5:
                if(discardOn) {
                    game.sendAction(new GinRummyDiscardAction(this, 5));
                    discardOn = !discardOn;
                    discardButton.setText("Discard Off");
                }
                if (groupOn) {
                    groupedCards[amountGrouped] = this.player1Cards[amountGrouped];
                    amountGrouped++;
                }
                break;

            case R.id.card6:
                if(discardOn) {
                    game.sendAction(new GinRummyDiscardAction(this, 6));
                    discardOn = !discardOn;
                    discardButton.setText("Discard Off");
                }
                if (groupOn) {
                    groupedCards[amountGrouped] = this.player1Cards[amountGrouped];
                    amountGrouped++;
                }
                break;

            case R.id.card7:
                if(discardOn) {
                    game.sendAction(new GinRummyDiscardAction(this, 7));
                    discardOn = !discardOn;
                    discardButton.setText("Discard Off");
                }
                if (groupOn) {
                    groupedCards[amountGrouped] = this.player1Cards[amountGrouped];
                    amountGrouped++;
                }
                break;

            case R.id.card8:
                if(discardOn) {
                    game.sendAction(new GinRummyDiscardAction(this, 8));
                    discardOn = !discardOn;
                    discardButton.setText("Discard Off");
                }
                if (groupOn) {
                    groupedCards[amountGrouped] = this.player1Cards[amountGrouped];
                    amountGrouped++;
                }
                break;

            case R.id.card9:
                if(discardOn) {
                    game.sendAction(new GinRummyDiscardAction(this, 9));
                    discardOn = !discardOn;
                    discardButton.setText("Discard Off");
                }
                if (groupOn) {
                    groupedCards[amountGrouped] = this.player1Cards[amountGrouped];
                    amountGrouped++;
                }
                break;

            case R.id.card10:
                if(discardOn) {
                    game.sendAction(new GinRummyDiscardAction(this, 10));
                    discardOn = !discardOn;
                    discardButton.setText("Discard Off");
                }
                if (groupOn) {
                    groupedCards[amountGrouped] = this.player1Cards[amountGrouped];
                    amountGrouped++;
                }
                break;
        }
    }

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
