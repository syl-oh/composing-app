package com.example.composingapp.views.noteviewdrawer.leaves

import android.graphics.Paint
import com.example.composingapp.utils.music.Music
import com.example.composingapp.views.viewtools.positiondict.NotePositionDict

class FilledBaseLeaf(
        notePositionDict: NotePositionDict,
        override val notePaint: Paint
) : BaseLeaf(notePositionDict) {
    override val vertRadius: Float = notePositionDict.noteVerticalRadius
    override val angle: Float = when (notePositionDict.note.noteLength) {
        Music.NoteLength.WHOLE_NOTE -> wholeNoteBaseAngle
        else -> filledNoteAngle
    }
}