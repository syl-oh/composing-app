package com.example.composingapp.utils.viewtools.noteviewdrawer.leaves.accidentals;

import android.graphics.Paint;

import com.example.composingapp.utils.interfaces.LeafDrawer;
import com.example.composingapp.utils.viewtools.NotePositionDict;
import com.example.composingapp.utils.viewtools.ViewConstants;

public abstract class AccidentalLeaf implements LeafDrawer {
    protected static final double W_TO_H_RATIO = (double) 1 / 2;
    protected float mBoundingRectHeight, mBoundingRectWidth;
    protected Paint mPaint;
    protected NotePositionDict mNotePositionDict;
    protected Float centerX, centerY;
    /**
     * Constructor for a NoteView
     *
     * @param notePositionDict  The NotePositionDict containing the NoteView's positional information
     * @param paint             The Paint to use to draw
     */
    protected AccidentalLeaf(NotePositionDict notePositionDict, Paint paint) {
        mNotePositionDict = notePositionDict;
        mBoundingRectHeight = (float) (mNotePositionDict.getSingleSpaceHeight() * 2);
        this.centerY = mNotePositionDict.getNoteY();
    }
}
