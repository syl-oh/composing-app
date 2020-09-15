package com.example.composingapp.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.LinearLayout;

class BarViewGroup extends LinearLayout {
    private static final String TAG = "BarViewGroup";
    private Paint mBarPaint;

    // Measurements for musical bar drawing in dip
    private static float mBarLineSize = 5;
    private static float mBarX = 50;
    private static float mBarY = 50;
    private static float mBarWidth = 2000;
    private static float mBarHeight = 100;


    public BarViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.HORIZONTAL);

        convertMeasuresToPx();
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
     * Converts from dp to px
     *
     * @param dp (float) measure of density-independent pixel
     * @return (float) measure of dp in pixels for the operating device
     */
    private float convertDpToPx(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp, getResources().getDisplayMetrics());
    }

    /**
     * Re-assigns measurements of bar components by converting from dp to px to support multiple
     * devices
     */
    private void convertMeasuresToPx() {
        mBarLineSize = convertDpToPx(mBarLineSize);
        mBarX = convertDpToPx(mBarX);
        mBarY = convertDpToPx(mBarY);
        mBarHeight = convertDpToPx(mBarHeight);
        mBarWidth = convertDpToPx(mBarWidth);
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
        }
    }
}
