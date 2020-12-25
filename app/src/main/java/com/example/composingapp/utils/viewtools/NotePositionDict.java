package com.example.composingapp.utils.viewtools;

import androidx.annotation.NonNull;

import com.example.composingapp.utils.interfaces.Observable;
import com.example.composingapp.utils.interfaces.Observer;
import com.example.composingapp.utils.music.Music;
import com.example.composingapp.utils.music.Note;

import java.util.ArrayList;

public class NotePositionDict extends PositionDict{
    private static final String TAG = "NotePositionDict";
    private Note mNote;
    private Float mNoteX, mNoteY, mHeight, mWidth;
    private Float mBaseLeftX;
    private Float mBaseRightX;
    private Float mBaseTopY;
    private Float mBaseBottomY;
    private Float mNoteRadius;
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
        mNoteRadius = getSingleSpaceHeight() / 2;
        observerArrayList = new ArrayList<>();
    }

    public Float getNoteRadius() {
        return mNoteRadius;
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
