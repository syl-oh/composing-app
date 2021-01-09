package com.example.composingapp.views.viewtools.positiondict;

import android.graphics.RectF;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.composingapp.utils.interfaces.PositionDict;
import com.example.composingapp.utils.music.Music;
import com.example.composingapp.utils.music.Note;
import com.example.composingapp.views.barviewgroupdrawer.leaves.StemLeaf;

import static com.example.composingapp.views.viewtools.ViewConstants.NOTE_W_TO_H_RATIO;

public class NotePositionDict implements PositionDict {
    private static final String TAG = "NotePositionDict";
    private final float mNoteX, mHeight, mWidth;
    private final float mNoteVerticalRadius;
    private final float mNoteHorizontalRadius;
    private ScorePositionDict mScorePositionDict;
    private StemLeaf.StemDirection stemDirection;
    private float stemHeight;
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
    public NotePositionDict(ScorePositionDict positionDict, Note note, float width, float height) {
        mScorePositionDict = positionDict;
        mNote = note;
        mHeight = height;
        mWidth = width;
        mNoteX = (mWidth / 2);
        if (note.getPitchClass() != Music.PitchClass.REST) {
            mNoteY = positionDict.getNoteYOf(mNote);
        } else {
            mNoteY = height / 2;
        }
        if (mNoteY <= mScorePositionDict.getThirdLineY()) {
            stemDirection = StemLeaf.StemDirection.POINTS_DOWN;
        } else {
            stemDirection = StemLeaf.StemDirection.POINTS_UP;
        }
        mNoteVerticalRadius = positionDict.getSingleSpaceHeight() / 2;
        mNoteHorizontalRadius = mNoteVerticalRadius * NOTE_W_TO_H_RATIO;
        stemHeight = mScorePositionDict.getOctaveHeight();
    }

    public StemLeaf.StemDirection getStemDirection() {
        return stemDirection;
    }

    public void setStemDirection(StemLeaf.StemDirection stemDirection) {
        this.stemDirection = stemDirection;
    }

    public float getStemHeight() {
        return stemHeight;
    }

    public void setStemHeight(float stemHeight) {
        this.stemHeight = stemHeight;
    }

    public ScorePositionDict getScorePositionDict() {
        return mScorePositionDict;
    }

    public RectF getTouchAreaRectF() {
        return new RectF(-mWidth / 2 + mNoteX, -2 * mScorePositionDict.getSingleSpaceHeight() + mNoteY,
                mWidth / 2 + mNoteX, 2 * mScorePositionDict.getSingleSpaceHeight() + mNoteY);
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
        mNoteY = mScorePositionDict.getNoteYOf(mNote);
        if (mNoteY <= mScorePositionDict.getThirdLineY()) {
            stemDirection = StemLeaf.StemDirection.POINTS_DOWN;
        } else {
            stemDirection = StemLeaf.StemDirection.POINTS_UP;
        }
        Log.d(TAG, "setNote: " + note.getPitchClass());
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

}
