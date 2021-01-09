package com.example.composingapp.views.noteviewdrawer.leaves

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.example.composingapp.utils.interfaces.PositionDict
import com.example.composingapp.utils.interfaces.componentdrawer.LeafDrawer
import com.example.composingapp.utils.music.Music
import com.example.composingapp.views.viewtools.positiondict.NotePositionDict

object HollowBaseLeaf : LeafDrawer {
    override fun draw(canvas: Canvas?, positionDict: PositionDict?, paint: Paint) {
        if (positionDict is NotePositionDict) {
            val prevPaintColor = paint.color
            BaseLeaf.draw(canvas, positionDict, paint.apply { color = Color.WHITE },
                    positionDict.noteHorizontalRadius / 2,
                    when (positionDict.note.noteLength) {
                        Music.NoteLength.HALF_NOTE -> 330f
                        else -> 75f
                    })
            paint.color = prevPaintColor
        }
    }
}