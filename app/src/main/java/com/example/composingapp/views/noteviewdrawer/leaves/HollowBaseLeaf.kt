package com.example.composingapp.views.noteviewdrawer.leaves

import android.graphics.Color
import android.graphics.Paint
import com.example.composingapp.utils.music.Music
import com.example.composingapp.views.viewtools.positiondict.NotePositionDict

class HollowBaseLeaf(
        notePositionDict: NotePositionDict,
        paint: Paint
) : BaseLeaf(notePositionDict) {
    override val notePaint: Paint = Paint(paint).apply { color = Color.WHITE }
    override val vertRadius: Float = notePositionDict.noteVerticalRadius / 2
    override val angle: Float = when (notePositionDict.note.noteLength) {
        Music.NoteLength.HALF_NOTE -> 330f
        else -> 75f
    }
}