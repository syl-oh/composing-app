package com.example.composingapp.utils.viewtools.noteviewdrawer.leaves.accidentals;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.composingapp.utils.viewtools.NotePositionDict;
import com.example.composingapp.utils.viewtools.ViewConstants;

public class SharpLeaf extends AccidentalLeaf {
    private static final String TAG = "SharpLeaf";
    private static final double SLOPE = (double) 1 / 6;
    private Float vertStartX, vertStartY, horzStartX, horzStartY, verticalPadding, verticalHeight;


    public SharpLeaf(NotePositionDict notePositionDict, Paint paint) {
        super(notePositionDict, paint);
        initFields();
    }

    private void initFields() {
        mBoundingRectWidth = (float) (W_TO_H_RATIO * mBoundingRectHeight);
        this.centerX = mNotePositionDict.getNoteX() -
                2 * (ViewConstants.NOTE_W_TO_H_RATIO * mNotePositionDict.getNoteVerticalRadius());


        verticalPadding = mBoundingRectHeight / 20;
        verticalHeight = mBoundingRectHeight - verticalPadding;
        // Draw the first vertical line 1/3 into the bounding rectangle
        vertStartX = centerX - mBoundingRectWidth / 6;
        vertStartY = centerY + mBoundingRectHeight / 2;
        // Draw the top diagonal line 1/3 from the top of the bounding rectangle
        horzStartX = centerX - mBoundingRectWidth / 2;
        horzStartY = (float) (centerY - mBoundingRectHeight / 6 + (SLOPE * mBoundingRectWidth));
    }

    @Override
    public void draw(Canvas canvas) {
//        Log.d(TAG, "draw: ");
        // Draw the first vertical line
        drawVertLine(canvas);
        // Move the canvas and draw the second vertical line
        canvas.save();
        canvas.translate(mBoundingRectWidth / 3, -verticalPadding);
        drawVertLine(canvas);
        canvas.restore();

        // Draw the first horizontal line
        drawHorzLine(canvas);
        // Move the canvas and draw the second horizontal line
        canvas.save();
        canvas.translate(0, mBoundingRectHeight / 3);
        drawHorzLine(canvas);
        canvas.restore();
    }

    private void drawVertLine(Canvas canvas) {
        canvas.drawLine(vertStartX, vertStartY, vertStartX,
                vertStartY - verticalHeight, mPaint);
    }

    private void drawHorzLine(Canvas canvas) {
        canvas.drawLine(horzStartX, horzStartY, horzStartX + mBoundingRectWidth,
                (float) (horzStartY - mBoundingRectWidth * SLOPE), mPaint);
    }
}
