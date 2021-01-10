package com.example.composingapp.views.commands

import com.example.composingapp.utils.interfaces.ui.Command
import com.example.composingapp.utils.music.Music
import com.example.composingapp.viewmodels.ScoreViewModel
import com.example.composingapp.viewmodels.ViewModelHelper.mutation

class ChangeClefCommand(
        private val scoreViewModel: ScoreViewModel,
        clef: Music.Clef
) : Command {
    private var previousClef = scoreViewModel.scoreObservableMutableLiveData.value?.clef
    private var currentClef: Music.Clef? = clef

    override fun execute() {
        currentClef?.let { changeClefTo(it) }
    }

    override fun undo() {
        previousClef?.let { changeClefTo(it) }
    }

    fun changeClefTo(clef: Music.Clef) {
        scoreViewModel.scoreObservableMutableLiveData.mutation {
            it.value?.clef = clef
        }
    }
}