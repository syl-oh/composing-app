package com.example.composingapp.views.accidentals;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.composingapp.utils.interfaces.PositionDict;
import com.example.composingapp.views.viewtools.positiondict.NotePositionDict;
import com.example.composingapp.views.viewtools.ViewConstants;

public class NaturalLeaf extends AccidentalLeaf {
    private static final float SLOPE = (float) 1 / 20;
    private final float paintStrokeWidth;
    private float horzLineWidth;
    private float vertLineHeight;
    private float normalX, normalY;
    private float vertBuffer;
    private float slopeDY;

    /**
     * Constructor for a NoteView
     *
     * @param notePositionDict The NotePositionDict containing the NoteView's positional information
     * @param paint            The Paint to use to draw
     */
    public NaturalLeaf(NotePositionDict notePositionDict, Paint paint) {
        super(notePositionDict, paint);

        mBoundingRectWidth = (float) (W_TO_H_RATIO * mBoundingRectHeight) / 2;
        this.centerX = mNotePositionDict.getNoteX() -
                (ViewConstants.NOTE_W_TO_H_RATIO * mNotePositionDict.getNoteVerticalRadius());


        slopeDY = mBoundingRectWidth * SLOPE;
        vertBuffer = mBoundingRectHeight / 3;
        vertLineHeight = mBoundingRectHeight - vertBuffer + slopeDY;
        paintStrokeWidth = paint.getStrokeWidth();
        horzLineWidth = mBoundingRectWidth - 2 * paintStrokeWidth;
        normalX = centerX - mBoundingRectWidth;
        normalY = centerY - mBoundingRectHeight / 2;

    }

    @Override
    public void draw(Canvas canvas, PositionDict positionDict, Paint paint) {
        canvas.save();
        canvas.translate(normalX, normalY);

        // Draw the first vertical line
        drawVertLine(canvas);

        //Draw the second vertical line
        canvas.save();
        canvas.translate(mBoundingRectWidth - paintStrokeWidth, vertBuffer);
        drawVertLine(canvas);
        canvas.restore();

        // Draw the first horizontal line
        canvas.save();
        canvas.translate(paintStrokeWidth, vertBuffer + slopeDY);
        drawHorzLine(canvas);
        canvas.restore();

        // Draw the second horizontal line
        canvas.save();
        canvas.translate(paintStrokeWidth, vertLineHeight);
        drawHorzLine(canvas);
        canvas.restore();

        canvas.restore();
    }

    private void drawVertLine(Canvas canvas) {
        canvas.drawLine(0, 0, 0, vertLineHeight, mPaint);
    }
    private void drawHorzLine(Canvas canvas) {
        canvas.drawLine(0, 0, horzLineWidth, - slopeDY, mPaint);
    }
}
