package com.example.composingapp.utils.viewtools;

import androidx.annotation.NonNull;

import com.example.composingapp.utils.interfaces.Observer;
import com.example.composingapp.utils.music.Music;
import com.example.composingapp.utils.music.Note;

import java.util.ArrayList;

import static com.example.composingapp.utils.viewtools.ViewConstants.NOTE_W_TO_H_RATIO;

public class NotePositionDict extends PositionDict {
    private static final String TAG = "NotePositionDict";
    private Note mNote;
    private Float mNoteX, mNoteY, mHeight, mWidth;
    private Float mNoteVerticalRadius;
    private Float mNoteHorizontalRadius;
    private ArrayList<Observer> observerArrayList;
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
        mNoteY = getNoteYOf(mNote);
        mNoteVerticalRadius = getSingleSpaceHeight() / 2;
        mNoteHorizontalRadius = mNoteVerticalRadius * NOTE_W_TO_H_RATIO;
        observerArrayList = new ArrayList<>();
    }

    public Float getNoteHorizontalRadius() {
        return mNoteHorizontalRadius;
    }

    public Float getNoteVerticalRadius() {
        return mNoteVerticalRadius;
    }

    public Note getNote() {
        return mNote;
    }

    public void setNote(@NonNull Note note) {
        this.mNote = note;
        mNoteY = getNoteYOf(mNote);
    }

    public Float getNoteX() {
        return mNoteX;
    }

    public Float getNoteY() {
        return mNoteY;
    }

    public Float getHeight() {
        return mHeight;
    }

    public Float getWidth() {
        return mWidth;
    }
}
