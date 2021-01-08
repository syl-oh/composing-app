package com.example.composingapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.composingapp.utils.music.BarObserver
import com.example.composingapp.utils.music.Music.*
import com.example.composingapp.utils.music.Note
import com.example.composingapp.utils.music.ScoreObservable


class ScoreViewModel : ViewModel() {
    val scoreObservableMutableLiveData: MutableLiveData<ScoreObservable?> = MutableLiveData()

    fun setClef(clef: Clef?) {
        scoreObservableMutableLiveData.value?.clef = clef
        update()
    }

    fun update() {
        scoreObservableMutableLiveData.value = scoreObservableMutableLiveData.value
    }

    private fun populateScore() {
        // Hardcoded score for quick-access testing purposes
        val scoreObservable = ScoreObservable(Clef.TREBLE_CLEF,
                NoteLength.QUARTER_NOTE, 4)
        val barObserver1 = BarObserver(scoreObservable)
//        val barObserver2 = BarObserver(scoreObservable)
//        val barObserver3 = BarObserver(scoreObservable)
//        val barObserver4 = BarObserver(scoreObservable)

        val note = Note(PitchClass.C_SHARP, 5, NoteLength.QUARTER_NOTE)
        val note2 = Note(PitchClass.A_NATURAL, 4, NoteLength.SIXTEENTH_NOTE)
        val note3 = Note(PitchClass.A_FLAT, 4, NoteLength.HALF_NOTE)
        val note4 = Note(PitchClass.D_NATURAL, 4, NoteLength.EIGHTH_NOTE)
        val note5 = Note(NoteLength.SIXTEENTH_NOTE)
        val note6 = Note(PitchClass.A_FLAT, 4, NoteLength.WHOLE_NOTE)

        for (barObserver in scoreObservable.barObserverList) {
            barObserver.addNote(note6)
//            barObserver.addNote(note2)
//            barObserver.addNote(note2)
//            barObserver.addNote(note2)
//            barObserver.addNote(note2)
//            barObserver.addNote(note2)
//            barObserver.addNote(note2)
//            barObserver.addNote(note2)
//            barObserver.addNote(note2)
        }
        scoreObservableMutableLiveData.value = scoreObservable
    }
    init {
        populateScore()
    }
}

