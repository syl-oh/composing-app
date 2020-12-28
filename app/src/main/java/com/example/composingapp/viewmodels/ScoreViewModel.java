package com.example.composingapp.viewmodels;

import androidx.lifecycle.LiveData;
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


    private void populateScore() {
        // Hardcoded score for quick-access testing purposes
        ScoreObservable scoreObservable = new ScoreObservable(Music.Clef.TREBLE_CLEF,
                Music.NoteLength.QUARTER_NOTE, 4);
        BarObserver barObserver1 = new BarObserver(scoreObservable);
        BarObserver barObserver2 = new BarObserver(scoreObservable);
        BarObserver barObserver3 = new BarObserver(scoreObservable);
        BarObserver barObserver4 = new BarObserver(scoreObservable);
        Note note = new Note(Music.NoteLength.SIXTEENTH_NOTE);
        barObserver1.addNote(note);
        barObserver2.addNote(note);
        barObserver3.addNote(note);
        barObserver4.addNote(note);

        mScoreObservableLiveData.setValue(scoreObservable);
    }

}
