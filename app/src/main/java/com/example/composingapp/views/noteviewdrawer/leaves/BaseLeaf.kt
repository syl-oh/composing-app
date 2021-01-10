package com.example.composingapp.views.noteviewdrawer.leaves

import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.graphics.withRotation
import androidx.core.graphics.withTranslation
import com.example.composingapp.utils.interfaces.ui.PositionDict
import com.example.composingapp.views.viewtools.positiondict.NotePositionDict

object BaseLeaf {
    fun draw(canvas: Canvas?, positionDict: PositionDict, paint: Paint, vertRadius: Float, angle:Float) {
        if (positionDict is NotePositionDict) {
            val horzRadius = positionDict.noteHorizontalRadius
            canvas?.withTranslation(positionDict.noteX, positionDict.noteY) {
                canvas.withRotation(angle) {
                    canvas.drawOval(
                            -horzRadius, vertRadius, horzRadius, -vertRadius, paint)
                }
            }
        }
    }
}