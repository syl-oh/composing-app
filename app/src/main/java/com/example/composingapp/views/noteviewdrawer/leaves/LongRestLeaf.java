package com.example.composingapp.views.noteviewdrawer.leaves;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.example.composingapp.utils.interfaces.componentdrawer.LeafDrawer;
import com.example.composingapp.utils.music.Music;
import com.example.composingapp.views.viewtools.positiondict.NotePositionDict;

import static com.example.composingapp.views.viewtools.ViewConstants.STEM_WIDTH;

public class LongRestLeaf implements LeafDrawer {
    private final RectF rect;
    private final float bottomLeftX;
    private final float bottomRightX;
    private final float bottomY;
    private final Paint bottomPaint, mPaint;

    public LongRestLeaf(NotePositionDict notePositionDict, Paint paint) {
        mPaint = paint;

        // X position
        float dx = 3 * notePositionDict.getPositionDict().getSingleSpaceHeight() / 4;
        float rectLeftX = notePositionDict.getNoteX() - dx;
        float rectRightX = notePositionDict.getNoteX() + dx;
        bottomLeftX = rectLeftX - dx;
        bottomRightX = rectRightX + dx;

        // Y Position of base
        bottomPaint = new Paint(paint);
        bottomPaint.setStrokeWidth(bottomPaint.getStrokeWidth() * 2);

        if (notePositionDict.getNote().getNoteLength() == Music.NoteLength.WHOLE_NOTE) {
            bottomY = notePositionDict.getPositionDict().getThirdLineY() + (STEM_WIDTH);
        } else {
            bottomY = notePositionDict.getPositionDict().getThirdLineY() - (STEM_WIDTH);
        }

        // Rect
        Float hatHeight = notePositionDict.getPositionDict().getSingleSpaceHeight() / 2;

        if (notePositionDict.getNote().getNoteLength() == Music.NoteLength.WHOLE_NOTE) {
            rect = new RectF(rectLeftX, bottomY + hatHeight, rectRightX, bottomY);
        } else {
            rect = new RectF(rectLeftX, bottomY - hatHeight, rectRightX, bottomY);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        bottomPaint.setColor(mPaint.getColor());
        canvas.drawLine(bottomLeftX, bottomY, bottomRightX, bottomY, bottomPaint);
        canvas.drawRect(rect, mPaint);
    }
}