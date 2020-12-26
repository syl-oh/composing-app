package com.example.composingapp.utils.viewtools.noteviewdrawer.leaves.bases

import android.graphics.Paint
import com.example.composingapp.utils.viewtools.NotePositionDict

class FilledBaseLeaf(
        notePositionDict: NotePositionDict,
        override val notePaint: Paint
) : BaseLeaf(notePositionDict) {
    override val vertRadius: Float = notePositionDict.noteVerticalRadius
    override val angle: Float = FILLED_NOTE_ANGLE
}