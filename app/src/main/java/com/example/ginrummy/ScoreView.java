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

    public ScoreView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        setBackgroundColor(Color.WHITE);
    }

    public void onDraw(Canvas canvas) {
        playerOneScore.setColor(Color.RED);
        playerTwoScore.setColor(Color.RED);
        playerOneScore.setTextSize(30f);
        playerTwoScore.setTextSize(30f);

        drawScore(canvas);
    }

    public void drawScore(Canvas canvas) {
        canvas.drawText("Player 2 Score: 66", 120f,70f,playerTwoScore);
        canvas.drawText("Player 1 Score: 20", 120f,500f,playerOneScore);
    }

}
