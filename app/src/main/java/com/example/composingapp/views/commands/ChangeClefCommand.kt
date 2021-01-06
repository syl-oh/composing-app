package com.example.composingapp.views.commands

import com.example.composingapp.utils.interfaces.ui.Command
import com.example.composingapp.utils.music.Music
import com.example.composingapp.utils.music.ScoreObservable
import com.example.composingapp.viewmodels.ScoreViewModel
import com.example.composingapp.viewmodels.ViewModelHelper.mutation

class ChangeClefCommand(
        private val scoreViewModel: ScoreViewModel,
        private var currentClef: Music.Clef
): Command {
    private var previousClef = scoreViewModel.scoreObservableMutableLiveData.value?.clef

    override fun execute() {
        scoreViewModel.scoreObservableMutableLiveData.mutation {
            it.value?.clef = currentClef
        }
    }

    override fun undo() {
        previousClef?.let {
            scoreViewModel.setClef(previousClef)
            previousClef = currentClef
        }
    }
}