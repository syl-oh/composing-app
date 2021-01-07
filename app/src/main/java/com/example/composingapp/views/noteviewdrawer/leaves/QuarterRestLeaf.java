package com.example.composingapp.views.noteviewdrawer.leaves;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.example.composingapp.utils.interfaces.componentdrawer.LeafDrawer;
import com.example.composingapp.views.viewtools.positiondict.NotePositionDict;

import static com.example.composingapp.views.viewtools.ViewConstants.STEM_WIDTH;

/**
 * Class for drawing quarter rests
 */
public class QuarterRestLeaf implements LeafDrawer {
    private final RectF curvedRect;
    float firstX, firstY, secondX, secondY, thirdY, fourthY;
    Paint restPaint;

    public QuarterRestLeaf(NotePositionDict notePositionDict, Paint paint) {

        restPaint = new Paint(paint);
        restPaint.setStrokeWidth(2 * STEM_WIDTH);
        restPaint.setStyle(Paint.Style.STROKE);
        // x positions: deviance from the initial mNoteX
        float dx = notePositionDict.getSingleSpaceHeight() / 2;
        firstX = notePositionDict.getNoteX() - dx;
        secondX = notePositionDict.getNoteX() + dx;

        // y positions
        float halfSpace = notePositionDict.getSingleSpaceHeight() / 2;
        // Start in the middle of the top space
        try {
            firstY = notePositionDict.getToneToBarlineYMap().get(notePositionDict.getClef().getBarlineTones()[4])
                    + halfSpace;
        } catch (NullPointerException e) {
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