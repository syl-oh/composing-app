package com.example.composingapp.views.commands

import com.example.composingapp.utils.interfaces.ui.Command
import com.example.composingapp.utils.music.BarObserver
import com.example.composingapp.utils.music.Note
import com.example.composingapp.viewmodels.ScoreViewModel

class ChangeNoteCommand(
        private val scoreViewModel: ScoreViewModel,
        private val targetBarObserver: BarObserver,
        private val targetIndex: Int,
        private val newNote: Note,
) : Command {
    override fun execute() {
        // Mutate data without forcing a reset for any observers. This prevents any clicked item from
        // resetting
        scoreViewModel.scoreObservableMutableLiveData.value?.barObserverList
                ?.find { barObserver -> barObserver == targetBarObserver }
                .apply { this?.replaceNoteAt(targetIndex, newNote) }

    }

    override fun undo() {
        TODO("Not yet implemented")
    }
}