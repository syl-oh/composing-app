package com.example.composingapp.views.viewtools.positiondict;

import android.graphics.RectF;

import androidx.annotation.NonNull;

import com.example.composingapp.utils.music.Music;
import com.example.composingapp.utils.music.Note;

import static com.example.composingapp.views.viewtools.ViewConstants.NOTE_W_TO_H_RATIO;

public class NotePositionDict extends PositionDict {
    private static final String TAG = "NotePositionDict";
    private final float mNoteX, mHeight, mWidth;
    private final float mNoteVerticalRadius;
    private final float mNoteHorizontalRadius;
    private RectF mTouchAreaRectF;
    private Note mNote;
    private float mNoteY;

    /**
     * Constructor
     *
     * @param note   The Note of the NoteView
     * @param clef   The Clef passed into the NoteView upon construction
     * @param width  The width of the NoteView
     * @param height The height of the Noteview
     */
    public NotePositionDict(Note note, Music.Clef clef, float width, float height) {
        super(height, clef);
        mNote = note;
        mHeight = height;
        mWidth = width;
        mNoteX = (mWidth / 2);
        if (note.getPitchClass() != Music.PitchClass.REST) {
            mNoteY = getNoteYOf(mNote);
        } else {
            mNoteY = height / 2;
        }
        mNoteVerticalRadius = getSingleSpaceHeight() / 2;
        mNoteHorizontalRadius = mNoteVerticalRadius * NOTE_W_TO_H_RATIO;
        updateTouchAreaRectF();
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
        mNoteY = getNoteYOf(mNote);
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
        mTouchAreaRectF = new RectF(-mWidth / 2 + mNoteX, -2 * getSingleSpaceHeight() + mNoteY,
                mWidth / 2 + mNoteX, 2 * getSingleSpaceHeight() + mNoteY);
    }
}
