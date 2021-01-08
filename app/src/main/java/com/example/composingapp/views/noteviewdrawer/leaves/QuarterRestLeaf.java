package com.example.composingapp.views.noteviewdrawer.leaves;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.example.composingapp.utils.interfaces.PositionDict;
import com.example.composingapp.utils.interfaces.componentdrawer.LeafDrawer;
import com.example.composingapp.views.viewtools.positiondict.NotePositionDict;

import static com.example.composingapp.views.viewtools.ViewConstants.STEM_WIDTH;

/**
 * Class for drawing quarter rests
 */
public class QuarterRestLeaf implements LeafDrawer {
    private final RectF curvedRect;
    float firstX, firstY, secondX, secondY, thirdY, fourthY, halfSpace;
    Paint restPaint;

    public QuarterRestLeaf(NotePositionDict notePositionDict, Paint paint) {
        restPaint = new Paint(paint);
        restPaint.setStrokeWidth(2 * STEM_WIDTH);
        restPaint.setStyle(Paint.Style.STROKE);
        // x positions: deviance from the initial mNoteX
        float halfSpace = notePositionDict.getScorePositionDict().getSingleSpaceHeight() / 2;
        firstX = notePositionDict.getNoteX() - halfSpace;
        secondX = notePositionDict.getNoteX() + halfSpace;

        // y positions
        // Start in the middle of the top space
        firstY = notePositionDict.getScorePositionDict().getFirstLineY() + halfSpace;
        // Move down a space
        secondY = firstY + 2 * halfSpace;
        // Move down half a space
        thirdY = secondY + halfSpace;
        // Move down half a space
        fourthY = thirdY + halfSpace;
        // For the curved stroke, create the rect for the oval
        curvedRect = new RectF();
    }

    @Override
    public void draw(Canvas canvas, PositionDict positionDict) {
        // First Stroke
        canvas.drawLine(firstX, firstY, secondX, secondY, restPaint);
        // Second Stroke
        canvas.drawLine(secondX, secondY, firstX, thirdY, restPaint);
        // Third Stroke
        canvas.drawLine(firstX, thirdY, secondX, fourthY, restPaint);
        // Fourth Stroke (Curved Stroke)
        canvas.drawArc(firstX, fourthY, secondX + 2 * halfSpace, fourthY + 2 * halfSpace,
                90, 180, false, restPaint);
    }
}