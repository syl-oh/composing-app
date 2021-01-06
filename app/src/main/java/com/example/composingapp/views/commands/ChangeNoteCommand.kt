package com.example.composingapp.views.commands

import com.example.composingapp.utils.interfaces.ui.Command
import com.example.composingapp.utils.music.BarObserver
import com.example.composingapp.utils.music.Note
import com.example.composingapp.viewmodels.ScoreViewModel
import com.example.composingapp.viewmodels.ViewModelHelper.mutation

class ChangeNoteCommand(
        private val scoreViewModel: ScoreViewModel,
        private val targetBarObserver: BarObserver,
        private val targetIndex: Int,
        private val newNote: Note,
) : Command {
    override fun execute() {
        scoreViewModel.scoreObservableMutableLiveData.mutation {
            it.value?.barObserverList?.find { barObserver -> barObserver == targetBarObserver }
                    .apply { this?.replaceNoteAt(targetIndex, newNote) }
        }
    }

    override fun undo() {
        TODO("Not yet implemented")
    }
}