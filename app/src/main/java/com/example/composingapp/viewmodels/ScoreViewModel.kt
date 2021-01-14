package com.example.composingapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.composingapp.utils.music.BarObserver
import com.example.composingapp.utils.music.Music.Clef
import com.example.composingapp.utils.music.Music.NoteLength
import com.example.composingapp.utils.music.ScoreObservable


class ScoreViewModel : ViewModel() {
    val scoreObservableMutableLiveData: MutableLiveData<ScoreObservable?> = MutableLiveData()

    fun update() {
        scoreObservableMutableLiveData.value = scoreObservableMutableLiveData.value
    }

    /**
     * Temporary method to populate the LiveData until a DataBase is integrated to the project
     */
    private fun populateScore() {
        // Hardcoded score for quick-access testing purposes
        val scoreObservable = ScoreObservable(Clef.TREBLE_CLEF,
                NoteLength.QUARTER_NOTE, 4)
        BarObserver(scoreObservable)

        scoreObservableMutableLiveData.value = scoreObservable
    }
    init {
        populateScore()
    }
}

