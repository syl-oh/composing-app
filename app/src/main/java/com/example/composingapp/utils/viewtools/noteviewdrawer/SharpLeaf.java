package com.example.composingapp.utils.viewtools.noteviewdrawer;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.composingapp.utils.interfaces.LeafDrawer;
import com.example.composingapp.utils.interfaces.Observer;
import com.example.composingapp.utils.viewtools.NotePositionDict;

public class SharpLeaf implements LeafDrawer {
    private static final String TAG = "SharpLeaf";
    private static final double W_TO_H_RATIO = (double) 1 / 2;
    private static final double SLOPE = (double) 1 / 6;
    float boundingRectHeight, boundingRectWidth;
    Paint mSharpPaint;
    private Float vertStartX, vertStartY, horzStartX, horzStartY, verticalPadding, verticalHeight;
    private NotePositionDict mNotePositionDict;

    public SharpLeaf(NotePositionDict notePositionDict, Paint paint) {
        mSharpPaint = paint;
        mNotePositionDict = notePositionDict;
        initFields();
    }

    void initFields() {
        boundingRectHeight = (float) (mNotePositionDict.getSingleSpaceHeight() * 2);
        boundingRectWidth = (float) (W_TO_H_RATIO * boundingRectHeight);
        Float xPos = mNotePositionDict.getNoteX();
        Float yPos = mNotePositionDict.getNoteY();
        verticalPadding = boundingRectHeight / 20;
        verticalHeight = boundingRectHeight - verticalPadding;
        // Draw the first vertical line 1/3 into the bounding rectangle
        vertStartX = xPos - boundingRectWidth / 6;
        vertStartY = yPos + boundingRectHeight / 2;
        // Draw the top diagonal line 1/3 from the top of the bounding rectangle
        horzStartX = xPos - boundingRectWidth / 2;
        horzStartY = (float) (yPos - boundingRectHeight / 6 + (SLOPE * boundingRectWidth));
    }

    @Override
    public void draw(Canvas canvas) {
//        Log.d(TAG, "draw: ");
        // Draw the first vertical line
        drawVertLine(canvas);
        // Move the canvas and draw the second vertical line
        canvas.save();
        canvas.translate(boundingRectWidth / 3, -verticalPadding);
        drawVertLine(canvas);
        canvas.restore();

        // Draw the first horizontal line
        drawHorzLine(canvas);
        // Move the canvas and draw the second horizontal line
        canvas.save();
        canvas.translate(0, boundingRectHeight / 3);
        drawHorzLine(canvas);
        canvas.restore();
    }

    private void drawVertLine(Canvas canvas) {
        canvas.drawLine(vertStartX, vertStartY, vertStartX,
                vertStartY - verticalHeight, mSharpPaint);
    }

    private void drawHorzLine(Canvas canvas) {
        canvas.drawLine(horzStartX, horzStartY, horzStartX + boundingRectWidth,
                (float) (horzStartY - boundingRectWidth * SLOPE), mSharpPaint);
    }
}
