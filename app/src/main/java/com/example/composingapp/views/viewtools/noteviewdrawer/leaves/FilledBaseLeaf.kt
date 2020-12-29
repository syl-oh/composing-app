package com.example.composingapp.views.viewtools.noteviewdrawer.leaves

import android.graphics.Paint
import com.example.composingapp.views.viewtools.positiondict.NotePositionDict

class FilledBaseLeaf(
        notePositionDict: NotePositionDict,
        override val notePaint: Paint
) : BaseLeaf(notePositionDict) {
    override val vertRadius: Float = notePositionDict.noteVerticalRadius
    override val angle: Float = FILLED_NOTE_ANGLE
}