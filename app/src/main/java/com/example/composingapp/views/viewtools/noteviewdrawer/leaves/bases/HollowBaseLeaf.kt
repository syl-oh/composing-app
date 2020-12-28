package com.example.composingapp.views.viewtools.noteviewdrawer.leaves.bases

import android.graphics.Color
import android.graphics.Paint
import com.example.composingapp.views.viewtools.NotePositionDict

class HollowBaseLeaf(
        notePositionDict: NotePositionDict,
        paint: Paint
) : BaseLeaf(notePositionDict) {
    override val notePaint: Paint  = Paint(paint).apply { color = Color.WHITE }
    override val vertRadius: Float = notePositionDict.noteVerticalRadius /2
    override val angle: Float = HALF_NOTE_ANGLE_INSIDE
}