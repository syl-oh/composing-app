package com.example.composingapp.utils.music;

import com.example.composingapp.utils.interfaces.Observer;

import java.util.ArrayList;
import java.util.HashMap;

public class BarObserver implements Observer {
    private ArrayList<Note> mNoteArrayList;
    private ScoreObservable mScoreObservable;
    private Music.NoteLength mBeatUnit;
    private int mBeatsPerBar;
    private Music.Clef mClef;
    private HashMap<Music.NoteLength, Integer> noteLengthToBeatsMap;

    public BarObserver(ScoreObservable scoreObservable) {
        // Subscribe to the score and add itself
        this.mScoreObservable = scoreObservable;
        scoreObservable.addObserver(this);

        // Init field variables
        mBeatUnit = scoreObservable.getBeatUnit();
        mBeatsPerBar = scoreObservable.getBeatsPerBar();
        mClef = scoreObservable.getClef();
    }

    public Music.NoteLength getBeatUnit() {
        return mBeatUnit;
    }

    public int getBeatsPerBar() {
        return mBeatsPerBar;
    }

    public Music.Clef getClef() {
        return mClef;
    }

    private void initNoteLengthToBeatsMap() {

    }

    @Override
    public void update() {
        mBeatUnit = mScoreObservable.getBeatUnit();
        mBeatsPerBar = mScoreObservable.getBeatsPerBar();
        mClef = mScoreObservable.getClef();
    }
}
