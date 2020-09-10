package com.example.composingapp.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.LinearLayout;

class BarViewGroup extends LinearLayout {
    private static final String TAG = "BarViewGroup";
    private Paint mBarPaint;
    private float mBarLineInPx;
    private int mBarX, mBarY, mBarWidth, mBarHeight;

    public BarViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.HORIZONTAL);

        initBarDimensions();
        initBarPaint();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        drawBarLines(canvas);
    }

    /**
     * Initializes the values of the member variables. Dictates the size of the bar on the screen
     */
    private void initBarDimensions() {
        mBarLineInPx = 5;
        mBarX = 50;
        mBarY = 50;
        mBarHeight = 100;
        mBarWidth = 400;
    }

    /**
     * Initializes the paint used in the dispatchDraw() method and its helper methods
     */
    private void initBarPaint() {
        mBarPaint = new Paint();
        mBarPaint.setStyle(Paint.Style.STROKE);
        mBarPaint.setColor(Color.parseColor("black"));
        mBarPaint.setStrokeWidth(mBarLineInPx);
    }

    /**
     * Draws bar-lines on a canvas
     *
     * @param canvas The canvas to draw on
     */
    private void drawBarLines(Canvas canvas) {
        // Position variable for the y-coordinate since it is the only one that changes in each loop
        int currentY = mBarY;
        // Draw the 5 lines from top to bottom
        for (int i = 0; i < 5; i++) {
            canvas.drawLine(mBarX, currentY, mBarX + mBarWidth, currentY, mBarPaint);
            currentY += mBarHeight / 5;
        }
    }
}
