package com.example.composingapp.utils.viewtools.noteviewdrawer.leaves.accidentals;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.example.composingapp.utils.viewtools.NotePositionDict;
import com.example.composingapp.utils.viewtools.ViewConstants;

import static com.example.composingapp.utils.viewtools.ViewConstants.STEM_WIDTH;

public class FlatLeaf extends AccidentalLeaf {
    private Float baseRectBottomY;
    private Float halfSpace;
    private Float baseRectRightX;
    private RectF arcBoundingRect;
    private RectF baseBoundingRect;
    private Paint tempPaint;

    public FlatLeaf(NotePositionDict notePositionDict, Paint paint) {
        super(notePositionDict, paint);
        halfSpace = notePositionDict.getSingleSpaceHeight() / 2;
        initFields();
    }

    private void initFields() {
        mBoundingRectWidth = (float) (W_TO_H_RATIO * mBoundingRectHeight);
        this.centerX = mNotePositionDict.getNoteX() -
                2 * (ViewConstants.NOTE_W_TO_H_RATIO * mNotePositionDict.getNoteVerticalRadius());


        tempPaint = new Paint(mPaint);
        tempPaint.setStrokeWidth(STEM_WIDTH);
        tempPaint.setStyle(Paint.Style.STROKE);
        // Copy the paint colours and overwrite the width/ style types

        // Make the bounding box for the curved and 45 degree stroke
        Float baseRectTopY = centerY - halfSpace;
        baseRectBottomY = centerY + halfSpace;
        baseRectRightX = centerX + mBoundingRectWidth / 2;
        baseBoundingRect = new RectF(centerX, baseRectTopY,
                baseRectRightX, baseRectBottomY);

        // Make the bounding box for the circle (arc stroke)
        arcBoundingRect = new RectF(
                centerX, centerY - halfSpace / 2, baseRectRightX, centerY + halfSpace / 2
        );
    }

    @Override
    public void draw(Canvas canvas) {
        // Draw the vertical line
        canvas.drawLine(centerX, centerY - mBoundingRectHeight / 2, centerX,
                baseRectBottomY, mPaint);
        // Draw the demi-heart
//        canvas.drawRect(baseBoundingRect, tempPaint);
        canvas.drawLine(centerX, baseRectBottomY, baseRectRightX, centerY, tempPaint);
        canvas.drawArc(arcBoundingRect, 0, -180, false, tempPaint);
    }
}
