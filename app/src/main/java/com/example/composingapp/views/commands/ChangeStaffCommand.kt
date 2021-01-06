package com.example.composingapp.views.commands

import com.example.composingapp.utils.interfaces.ui.Command
import com.example.composingapp.utils.music.Music
import com.example.composingapp.utils.music.ScoreObservable

class ChangeStaffCommand(
        scoreObservable: ScoreObservable,
        clef: Music.Clef
): Command {
    override fun execute() {
        TODO("Not yet implemented")
    }
}