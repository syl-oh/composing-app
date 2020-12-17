package com.example.composingapp.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.widget.LinearLayout;

import androidx.core.view.ViewCompat;

import com.example.composingapp.music.Music;
import com.example.composingapp.music.Note;
import com.example.composingapp.music.Tone;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static com.example.composingapp.views.ViewConstants.TOTAL_LINES;

public class BarViewGroup extends LinearLayout {
    private static final String TAG = "BarViewGroup";
    private ArrayList<NoteView> mNoteViewList;
    private Paint mBarPaint;
    private Music.Clef mClef;
    private float mBarLineSize, mBarWidth, mBarHeight, mBarX, mBarY;
    private NotePositionDict yPositions;
    private float[] mBarlineYPositions;
    private LinearLayout.LayoutParams mBarViewGroupParams;

    public BarViewGroup(Context context, Music.Clef clef) {
        super(context);
        init(clef);
    }

    /**
     * Initializes the paint used in the dispatchDraw() method and its helper methods
     */
    private void init(Music.Clef clef) {
        setWillNotDraw(false); // Enable drawing of the ViewGroup
        setOrientation(LinearLayout.HORIZONTAL);
        mBarViewGroupParams = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);

        mClef = clef;
        mNoteViewList = new ArrayList<>();
        mBarPaint = new Paint();
        mBarPaint.setStyle(Paint.Style.STROKE);
        mBarPaint.setColor(Color.parseColor("black"));
        mBarPaint.setStrokeWidth(mBarLineSize);
        mBarLineSize = 6; // Value in dp
        mBarLineSize = convertDpToPx(mBarLineSize);

        Note note = new Note(Music.PitchClass.C_NATURAL, 4, Music.NoteLength.QUARTER_NOTE);
        NoteView noteView = new NoteView(getContext(), note, mClef);
        addNoteViewToChildren(noteView);
    }

    /**
     * Adds a NoteView as a child to this BarViewGroup
     */
    private void addNoteViewToChildren(NoteView noteView) {
        noteView.setId(ViewCompat.generateViewId());
        noteView.setLayoutParams(mBarViewGroupParams);
        this.addView(noteView);
        mNoteViewList.add(noteView);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // Get x and y coordinates
        int[] barXY = new int[2];
        getLocationInWindow(barXY);
        mBarX = barXY[0];
        mBarY = barXY[1];
        mBarWidth = w - mBarX;
        mBarHeight = h - mBarY;

        Log.d(TAG, "onSizeChanged: mBarX is " + mBarX);
//        Log.d(TAG, "onSizeChanged: mBarY is " + mBarY);
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
        mBarlineYPositions = new float[5];
        Tone currentTone = null;
        for (int i = 0; i < 5; i++) {
            try {
                currentTone = barlineTones[i];
            } catch (NullPointerException e) {
                Log.e(TAG, "updateBarlineYPositions: NullPointerException, could not " +
                        " retrieve the " + i + "th element from barlineTones for clef " + mClef);
            }

            try {
                mBarlineYPositions[i] = toneToBarlineYMap.get(currentTone);
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
    protected void onDraw(Canvas canvas) {
        drawBarLines(canvas);
        drawSideLines(canvas);
    }

    /**
     * Draws barlines on a canvas
     *
     * @param canvas The canvas to draw on
     */
    private void drawBarLines(Canvas canvas) {
        // Draw the 5 lines from top to bottom
        for (float currentY : mBarlineYPositions) {
            canvas.drawLine(mBarX, currentY, mBarX + mBarWidth - 1,
                    currentY, mBarPaint);
//            Log.d(TAG, "drawBarLines: currentY " + currentY);
        }
    }

    /**
     * Draws the sides of the bar
     *
     * @param canvas The canvas to draw on
     */
    private void drawSideLines(Canvas canvas) {
        float[] barlineYPositions = mBarlineYPositions.clone();
        Arrays.sort(barlineYPositions);

        float topBarlineY = barlineYPositions[0];
        float bottomBarlineY = barlineYPositions[barlineYPositions.length - 1];

        for (float xPos : new float[]{mBarX, (mBarX + mBarWidth - 1)}) {
            canvas.drawLine(xPos, topBarlineY, xPos, bottomBarlineY, mBarPaint);
//            Log.d(TAG, "drawSideLines: xPos: " + xPos);
//            Log.d(TAG, "drawSideLines: topBarlineY: " + topBarlineY);
//            Log.d(TAG, "drawSideLines: bottomBarlineY: " + bottomBarlineY);
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
