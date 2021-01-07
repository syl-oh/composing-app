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

    public void setClef(Music.Clef mClef) {
        this.mClef = mClef;
        updateObservers();
    }

    public Music.NoteLength getBeatUnit() {
        return mBeatUnit;
    }

    public void setBeatUnit(Music.NoteLength mBeatUnit) {
        this.mBeatUnit = mBeatUnit;
        updateObservers();
    }

    public int getBeatsPerBar() {
        return mBeatsPerBar;
    }

    public void setBeatsPerBar(int mBeatsPerBar) {
        this.mBeatsPerBar = mBeatsPerBar;
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
