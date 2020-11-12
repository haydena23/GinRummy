/**
 * rummyDumbAI.java - utilized to display the current score of the game
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
import android.view.SurfaceView;

public class ScoreView extends SurfaceView {

    Paint playerOneScore = new Paint();
    Paint playerTwoScore = new Paint();

    String player1;
    String player2;

    public ScoreView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        setBackgroundColor(Color.WHITE);
        player1 = "Player 2 Score: 0";
        player2 = "Player 1 Score: 0";
    }

    public void onDraw(Canvas canvas) {
        playerOneScore.setColor(Color.RED);
        playerTwoScore.setColor(Color.RED);
        playerOneScore.setTextSize(30f);
        playerTwoScore.setTextSize(30f);

        drawScore(canvas);
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public void drawScore(Canvas canvas) {
        canvas.drawText(player1, 120f,70f,playerTwoScore);
        canvas.drawText(player2, 120f,500f,playerOneScore);
    }

}
