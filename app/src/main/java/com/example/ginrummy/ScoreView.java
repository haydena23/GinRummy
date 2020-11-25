/**
 * ScoreView.java - utilized to display the current score of the game
 *
 * @author Jarren Calizo, Tony Hayden, Aron Manalang, Audrey Sauter
 * @version 12 Nov 2020
 */
package com.example.ginrummy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.example.game.GameFramework.utilities.FlashSurfaceView;

public class ScoreView extends FlashSurfaceView {

    //Instance variables
    Paint playerOneScore = new Paint();
    Paint playerTwoScore = new Paint();
    String player1;
    String player2;

    protected GinRummyGameState state;
    private static final String TAG = "ScoreView";

    /**
     * Creation of the score view for the surfaceview
     *
     * @param context
     * @param attrs
     */
    public ScoreView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        setBackgroundColor(Color.WHITE);

        player1 = "Player 1 Score: 0";
        player2 = "Player 2 Score: 0";
    }

    public ScoreView(Context context) {
        super(context);
        init();
    }

    private void init() {
        setBackgroundColor(backgroundColor());
    }

    public int backgroundColor() {
        return Color.BLUE;
    }

    public int foregroundColor() {
        return Color.YELLOW;
    }

    public void setState(GinRummyGameState state) {
        this.state = state;
    }

    /**
     * Simple on draw method to draw to the screen
     *
     * @param canvas Reference to the canvas
     */
    public void onDraw(Canvas canvas) {
        playerOneScore.setColor(Color.RED);
        playerTwoScore.setColor(Color.RED);
        playerOneScore.setTextSize(30f);
        playerTwoScore.setTextSize(30f);

        drawScore(canvas);
    }

    /**
     * Reference to player 1
     *
     * @param player1 Takes in player name
     */
    public void setPlayer1(String player1) {
        this.player1 =
                "Player 1 score: " + player1;
    }

    /**
     * Reference to player 2
     *
     * @param player2 Takes in player name
     */
    public void setPlayer2(String player2) {
        this.player2 =
                "Player 2 score: " + player2;
    }

    /**
     * Draws on a surface view the correct player score
     *
     * @param canvas Reference to the canvas
     */
    public void drawScore(Canvas canvas) {
        canvas.drawText(player1, 120f,70f,playerTwoScore);
        canvas.drawText(player2, 120f,500f,playerOneScore);
    }

}
