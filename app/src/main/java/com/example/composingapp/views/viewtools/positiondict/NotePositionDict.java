package com.example.composingapp.views.viewtools.positiondict;

import android.graphics.RectF;

import androidx.annotation.NonNull;

import com.example.composingapp.utils.music.Music;
import com.example.composingapp.utils.music.Note;

import static com.example.composingapp.views.viewtools.ViewConstants.NOTE_W_TO_H_RATIO;

public class NotePositionDict {
    private static final String TAG = "NotePositionDict";
    private final float mNoteX, mHeight, mWidth;
    private final float mNoteVerticalRadius;
    private final float mNoteHorizontalRadius;
    private PositionDict mPositionDict;
    private RectF mTouchAreaRectF;
    private Note mNote;
    private float mNoteY;
    /**
     * Constructor
     *
     * @param positionDict PositionDict containing coordinate information
     * @param note         The Note of the NoteView
     * @param width        The width of the NoteView
     * @param height       The height of the Noteview
     */
    public NotePositionDict(PositionDict positionDict, Note note, float width, float height) {
        mPositionDict = positionDict;
        mNote = note;
        mHeight = height;
        mWidth = width;
        mNoteX = (mWidth / 2);
        if (note.getPitchClass() != Music.PitchClass.REST) {
            mNoteY = positionDict.getNoteYOf(mNote);
        } else {
            mNoteY = height / 2;
        }
        mNoteVerticalRadius = positionDict.getSingleSpaceHeight() / 2;
        mNoteHorizontalRadius = mNoteVerticalRadius * NOTE_W_TO_H_RATIO;
        updateTouchAreaRectF();
    }

    public PositionDict getPositionDict() {
        return mPositionDict;
    }

    public RectF getTouchAreaRectF() {
        return mTouchAreaRectF;
    }

    public float getNoteHorizontalRadius() {
        return mNoteHorizontalRadius;
    }

    public float getNoteVerticalRadius() {
        return mNoteVerticalRadius;
    }

    public Note getNote() {
        return mNote;
    }

    /**
     * Sets the note for this NotePositionDict and recalculates its y position
     *
     * @param note The new Note
     */
    public void setNote(@NonNull Note note) {
        this.mNote = note;
        mNoteY = mPositionDict.getNoteYOf(mNote);
        updateTouchAreaRectF();
    }

    public float getNoteX() {
        return mNoteX;
    }

    public float getNoteY() {
        return mNoteY;
    }

    public float getHeight() {
        return mHeight;
    }

    public float getWidth() {
        return mWidth;
    }

    private void updateTouchAreaRectF() {
        mTouchAreaRectF = new RectF(-mWidth / 2 + mNoteX, -2 * mPositionDict.getSingleSpaceHeight() + mNoteY,
                mWidth / 2 + mNoteX, 2 * mPositionDict.getSingleSpaceHeight() + mNoteY);
    }
}
