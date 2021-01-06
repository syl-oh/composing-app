package com.example.composingapp.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.composingapp.utils.music.BarObserver;
import com.example.composingapp.utils.music.Music;
import com.example.composingapp.utils.music.Note;
import com.example.composingapp.utils.music.ScoreObservable;

public class ScoreViewModel extends ViewModel {
    private MutableLiveData<ScoreObservable> mScoreObservableLiveData;

    public ScoreViewModel() {
        mScoreObservableLiveData = new MutableLiveData<>();
        populateScore();
    }

    public MutableLiveData<ScoreObservable> getScoreObservableMutableLiveData() {
        return mScoreObservableLiveData;
    }

    public void setClef(Music.Clef clef) {
        mScoreObservableLiveData.getValue().setClef(clef);
        mScoreObservableLiveData.setValue(mScoreObservableLiveData.getValue());
    }


    private void populateScore() {
        // Hardcoded score for quick-access testing purposes
        ScoreObservable scoreObservable = new ScoreObservable(Music.Clef.TREBLE_CLEF,
                Music.NoteLength.QUARTER_NOTE, 4);
        BarObserver barObserver1 = new BarObserver(scoreObservable);
        BarObserver barObserver2 = new BarObserver(scoreObservable);
        BarObserver barObserver3 = new BarObserver(scoreObservable);
        BarObserver barObserver4 = new BarObserver(scoreObservable);
        Note note = new Note(Music.PitchClass.C_SHARP, 5, Music.NoteLength.QUARTER_NOTE);
        Note note2 = new Note(Music.PitchClass.A_NATURAL, 4, Music.NoteLength.SIXTEENTH_NOTE);
        Note note3 = new Note(Music.PitchClass.A_FLAT, 4, Music.NoteLength.HALF_NOTE);
        Note note4 = new Note(Music.PitchClass.D_NATURAL, 4, Music.NoteLength.EIGHTH_NOTE);
        Note note5 = new Note(Music.NoteLength.SIXTEENTH_NOTE);

        for (BarObserver barObserver : scoreObservable.getBarObserverList()) {
            barObserver.addNote(note3);
            barObserver.addNote(note2);
            barObserver.addNote(note2);
            barObserver.addNote(note2);
            barObserver.addNote(note2);
            barObserver.addNote(note2);
            barObserver.addNote(note2);
            barObserver.addNote(note2);
            barObserver.addNote(note2);
        }
        mScoreObservableLiveData.setValue(scoreObservable);
    }

}
