package com.example.composingapp.views.noteviewdrawer.leaves;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.composingapp.utils.interfaces.PositionDict;
import com.example.composingapp.utils.interfaces.componentdrawer.LeafDrawer;
import com.example.composingapp.views.viewtools.positiondict.NotePositionDict;

import static com.example.composingapp.views.viewtools.ViewConstants.STEM_WIDTH;

/**
 * Class for drawing quarter rests
 */
public class QuarterRestLeaf implements LeafDrawer {

    @Override
    public void draw(Canvas canvas, PositionDict positionDict, Paint paint) {
        if (positionDict instanceof NotePositionDict) {

            float oldStrokeWidth = paint.getStrokeWidth();
            Paint.Style oldStyle = paint.getStyle();
            paint.setStrokeWidth(2 * STEM_WIDTH);
            paint.setStyle(Paint.Style.STROKE);

            float halfSpace = ((NotePositionDict) positionDict).getScorePositionDict().getSingleSpaceHeight() / 2;
            float firstX = ((NotePositionDict) positionDict).getNoteX() - halfSpace;
            float secondX = ((NotePositionDict) positionDict).getNoteX() + halfSpace;
            // y positions
            // Start in the middle of the top space
            float firstY = ((NotePositionDict) positionDict).getScorePositionDict().getFifthLineY() + halfSpace;
            // Move down a space
            float secondY = firstY + 2 * halfSpace;
            // Move down half a space
            float thirdY = secondY + halfSpace;
            // Move down half a space
            float fourthY = thirdY + halfSpace;

            // First Stroke
            canvas.drawLine(firstX, firstY, secondX, secondY, paint);
            // Second Stroke
            canvas.drawLine(secondX, secondY, firstX, thirdY, paint);
            // Third Stroke
            canvas.drawLine(firstX, thirdY, secondX, fourthY, paint);
            // Fourth Stroke (Curved Stroke)
            canvas.drawArc(firstX, fourthY, secondX + 2 * halfSpace, fourthY + 2 * halfSpace,
                    90, 180, false, paint);

            paint.setStrokeWidth(oldStrokeWidth);
            paint.setStyle(oldStyle);
        }
    }
}