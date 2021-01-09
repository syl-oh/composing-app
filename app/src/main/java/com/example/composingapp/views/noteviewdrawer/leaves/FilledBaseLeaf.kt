package com.example.composingapp.views.noteviewdrawer.leaves

import android.graphics.Canvas
import android.graphics.Paint
import com.example.composingapp.utils.interfaces.PositionDict
import com.example.composingapp.utils.interfaces.componentdrawer.LeafDrawer
import com.example.composingapp.utils.music.Music
import com.example.composingapp.views.viewtools.positiondict.NotePositionDict

object FilledBaseLeaf: LeafDrawer {
    override fun draw(canvas: Canvas?, positionDict: PositionDict, paint: Paint) {
        if (positionDict is NotePositionDict) {
            BaseLeaf.draw(canvas, positionDict, paint, positionDict.noteVerticalRadius,
                    when (positionDict.note.noteLength) {
                        Music.NoteLength.WHOLE_NOTE -> 0f
                        else -> 330f
                    })
        }
    }
}