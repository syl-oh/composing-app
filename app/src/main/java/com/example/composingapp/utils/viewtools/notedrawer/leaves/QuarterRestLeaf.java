package com.example.composingapp.utils.viewtools.notedrawer.leaves;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import com.example.composingapp.utils.interfaces.LeafDrawer;
import com.example.composingapp.utils.viewtools.notedrawer.NoteDrawer;

import static com.example.composingapp.utils.viewtools.ViewConstants.QUARTER_REST_NOTE_X_DEVIANCE_FACTOR;
import static com.example.composingapp.utils.viewtools.ViewConstants.REST_STROKE_WIDTH;

public class QuarterRestLeaf implements LeafDrawer {
    private final RectF curvedRect;
    float firstX, firstY, secondX, secondY, thirdX, thirdY, fourthY;
    Paint restPaint;


    public QuarterRestLeaf(NoteDrawer noteDrawer) {
        restPaint = new Paint(mNotePaint);
        restPaint.setStrokeWidth(REST_STROKE_WIDTH);
        restPaint.setStyle(Paint.Style.STROKE);
        // x positions: deviance from the initial mNoteX
        float dx = QUARTER_REST_NOTE_X_DEVIANCE_FACTOR * mNoteX;
        firstX = mNoteX - dx;
        secondX = mNoteX + dx;

        // y positions
        float halfSpace = mPositionDict.getSingleSpaceHeight() / 2;
        // Start in the middle of the top space
        try {
            firstY = mPositionDict.getToneToBarlineYMap().get(mClef.getBarlineTones()[4])
                    + halfSpace;
        } catch (NullPointerException e) {
            Log.e(TAG, "QuarterRestLeaf: NullPointerException: could not retrieve Y position" +
                    "of the middle of the top space in the bar");
        }
        // Move down a space
        secondY = firstY + 2 * halfSpace;
        // Move down half a space
        thirdY = secondY + halfSpace;
        // Move down half a space
        fourthY = thirdY + halfSpace;
        // For the curved stroke, create the rect for the oval
        curvedRect = new RectF(firstX, fourthY,
                secondX + 2 * dx,
                fourthY + 2 * halfSpace);
    }

    @Override
    public void draw(Canvas canvas) {
        // First Stroke
        canvas.drawLine(firstX, firstY, secondX, secondY, restPaint);
        // Second Stroke
        canvas.drawLine(secondX, secondY, firstX, thirdY, restPaint);
        // Third Stroke
        canvas.drawLine(firstX, thirdY, secondX, fourthY, restPaint);
        // Fourth Stroke (Curved Stroke)
        canvas.drawArc(curvedRect, 90, 180, false, restPaint);
    }
}
