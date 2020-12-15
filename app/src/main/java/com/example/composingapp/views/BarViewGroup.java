package com.example.composingapp.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.widget.LinearLayout;

import com.example.composingapp.music.Music;
import com.example.composingapp.music.Tone;

import java.util.HashMap;

import static com.example.composingapp.views.ViewConstants.TOTAL_LINES;

class BarViewGroup extends LinearLayout {
    private static final String TAG = "BarViewGroup";
    private Paint mBarPaint;
    private Music.Staff mClef;
    private float mBarLineSize, mBarWidth, mBarHeight, mBarX, mBarY;
    private NotePositionDict yPositions;
    private float[] barlineYPositions;

    public BarViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * Initializes the paint used in the dispatchDraw() method and its helper methods
     */
    private void init() {
        setOrientation(LinearLayout.HORIZONTAL);
        mClef = Music.Staff.TREBLE_CLEF;
        mBarPaint = new Paint();
        mBarPaint.setStyle(Paint.Style.STROKE);
        mBarPaint.setColor(Color.parseColor("black"));
        mBarPaint.setStrokeWidth(mBarLineSize);
        mBarLineSize = 6; // Value in dp
        mBarLineSize = convertDpToPx(mBarLineSize);
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//
//        // Measure the dimensions of the bar
//        for (int i = 0; i < getChildCount(); i++) {
//            View child = getChildAt(i);
//            mBarWidth += child.getMeasuredWidth();
//            mBarHeight = child.getMeasuredHeight();
//        }
//        // ViewConstants.initNoteYPositions(mBarHeight, mClef);
//    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // Get x and y coordinates
        int[] barXY = new int[2];
        getLocationInWindow(barXY);
        mBarX = barXY[0];
        mBarY = barXY[1];
        mBarHeight = getMeasuredHeight();
        mBarWidth = getWidth();

//        Log.d(TAG, "onSizeChanged: mBarX is "+ mBarX);
//        Log.d(TAG, "onSizeChanged: mBarY is "+ mBarY);
//        Log.d(TAG, "onSizeChanged: mBarHeight is "+ mBarHeight);
//        Log.d(TAG, "onSizeChanged: mBarWidth is "+ mBarWidth);

        // Update the y position dictionaries
        yPositions = new NotePositionDict(mBarHeight, mClef);
        updateBarlineYPositions();
    }

    /**
     * Updates the array that holds the y positions for where to draw barlines on
     */
    private void updateBarlineYPositions() {
        Tone[] barlineTones = mClef.getBarlineTones();
        HashMap<Tone, Float> toneToBarlineYMap = yPositions.getToneToBarlineYMap();
        barlineYPositions = new float[5];
        Tone currentTone = null;
        for (int i = 0; i < 5; i++) {
            try {
                currentTone = barlineTones[i];
            } catch (NullPointerException e) {
                Log.e(TAG, "updateBarlineYPositions: NullPointerException, could not " +
                        " retrieve the " + i + "th element from barlineTones for clef " + mClef);
            }

            try {
                barlineYPositions[i] = toneToBarlineYMap.get(currentTone);
            } catch (NullPointerException e) {
                if (!(TOTAL_LINES < 15)) {
                    Log.e(TAG, "updateBarlineYPositions: NullPointerException, could not " +
                            "retrieve the " + i + "st/th barline y position from toneToBarlineYMap "
                            + "for clef " + mClef + " and tone with pitchclass " + currentTone.getPitchClass()
                            + " with octave " + currentTone.getOctave());
                } else {
                    Log.e(TAG, "updateBarlineYPositions: TOTAL_LINES is less than 15: " +
                            " NotePositionDict does not contain all necessary barlines.");
                }
            }
        }
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        drawBarLines(canvas);
        canvas.drawRect(0, 0, mBarWidth, mBarHeight, mBarPaint);
    }

    /**
     * Draws barlines on a canvas
     *
     * @param canvas (Canvas) The canvas to draw on
     */
    private void drawBarLines(Canvas canvas) {
        // Draw the 5 lines from top to bottom
        for (float currentY : barlineYPositions) {
            canvas.drawLine(mBarX, currentY, mBarX + mBarWidth, currentY, mBarPaint);
//            Log.d(TAG, "drawBarLines: currentY " + currentY);
        }
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
}
