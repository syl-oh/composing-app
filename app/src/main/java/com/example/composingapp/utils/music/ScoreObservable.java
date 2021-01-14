package com.example.composingapp.utils.music;

import com.example.composingapp.utils.interfaces.observer.Observable;
import com.example.composingapp.utils.interfaces.observer.Observer;

import java.util.ArrayList;

public class ScoreObservable implements Observable {
    private ArrayList<BarObserver> mBarObserverList;
    private Music.Clef mClef;
    private Music.NoteLength mBeatUnit;
    private int mBeatsPerBar;
    public ScoreObservable(Music.Clef mClef, Music.NoteLength mBeatUnit, int mBeatsPerBar) {
        this.mClef = mClef;
        this.mBeatUnit = mBeatUnit;
        this.mBeatsPerBar = mBeatsPerBar;
        mBarObserverList = new ArrayList<>();
    }

    public ArrayList<BarObserver> getBarObserverList() {
        return mBarObserverList;
    }

    public Music.Clef getClef() {
        return mClef;
    }

    /**
     * Sets the Clef of this ScoreObservable
     *
     * @param clef Clef to change to
     * Side effects: Updates Observers of this ScoreObservable
     */
    public void setClef(Music.Clef clef) {
        this.mClef = clef;
        updateObservers();
    }

    public Music.NoteLength getBeatUnit() {
        return mBeatUnit;
    }

    /**
     * Sets the beat unit of this ScoreObservable
     *
     * @param beatUnit NoteLength representing the new beat unit
     * Side effects: Updates Observers of this ScoreObservable
     */
    public void setBeatUnit(Music.NoteLength beatUnit) {
        this.mBeatUnit = beatUnit;
        updateObservers();
    }

    public int getBeatsPerBar() {
        return mBeatsPerBar;
    }

    /**
     * Sets the number of beats in a bar of this ScoreObservable
     *
     * @param beatsPerBar int representing the new number of beats in a bar
     * Side effects: Updates Observers of this ScoreObservable
     */
    public void setBeatsPerBar(int beatsPerBar) {
        this.mBeatsPerBar = beatsPerBar;
        updateObservers();
    }

    @Override
    public void addObserver(Observer barObserver) {
        mBarObserverList.add((BarObserver) barObserver);
    }

    @Override
    public void removeObserver(Observer barObserver) {
        mBarObserverList.remove(barObserver);
    }

    @Override
    public void updateObservers() {
        for (BarObserver barObserver : mBarObserverList) {
            barObserver.update();
        }
    }
}
