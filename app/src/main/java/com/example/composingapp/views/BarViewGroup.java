package com.example.composingapp.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import com.example.composingapp.music.Music;

class BarViewGroup extends LinearLayout {
    private static final String TAG = "BarViewGroup";

    private Paint mBarPaint;
    private Music.Staff mClef;
    private float mBarLineSize, mBarX, mBarY, mBarWidth, mBarHeight;
    private float[] mNoteYPositions;

    public BarViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.HORIZONTAL);
        mClef = Music.Staff.TREBLE_CLEF;
        initMeasurements();
        initBarPaint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // Measure the dimensions of the bar
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            mBarWidth += child.getMeasuredWidth();
            mBarHeight = child.getMeasuredHeight();
        }
        ViewConstants.initNoteYPositions(mBarHeight, mClef);
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        drawBarLines(canvas);
    }

    /**
     * Converts from dp to px
     *
     * @param dp (float) measure of density-independent pixel
     * @return (float) measure of dp in pixels for the operating device
     */
    private float convertDpToPx(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp, getResources().getDisplayMetrics());
    }

    private void initMeasurements() {
        // Values in dp
        mBarLineSize = 5;
        mBarX = 0;
        mBarY = 0;
        convertMeasuresToPx();
    }

    /**
     * Re-assigns measurements of bar components by converting from dp to px to support multiple
     * devices
     */
    private void convertMeasuresToPx() {
        mBarLineSize = convertDpToPx(mBarLineSize);
        mBarX = convertDpToPx(mBarX);
        mBarY = convertDpToPx(mBarY);
    }

    /**
     * Initializes the paint used in the dispatchDraw() method and its helper methods
     */
    private void initBarPaint() {
        mBarPaint = new Paint();
        mBarPaint.setStyle(Paint.Style.STROKE);
        mBarPaint.setColor(Color.parseColor("black"));
        mBarPaint.setStrokeWidth(mBarLineSize);
    }

    /**
     * Draws bar-lines on a canvas
     *
     * @param canvas (Canvas) The canvas to draw on
     */
    private void drawBarLines(Canvas canvas) {
        // Position variable for the y-coordinate since it is the only one that changes in each loop
        float currentY = mBarY;

        // Draw the 5 lines from top to bottom
        for (int i = 0; i < 5; i++) {
            canvas.drawLine(mBarX, currentY, mBarX + mBarWidth, currentY, mBarPaint);
            currentY += mBarHeight / 5;
            Log.d(TAG, "drawBarLines: currentY " + currentY);

        }
    }
}
