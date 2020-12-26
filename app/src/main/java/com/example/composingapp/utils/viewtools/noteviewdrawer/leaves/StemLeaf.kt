package com.example.composingapp.utils.viewtools.noteviewdrawer.leaves

import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.graphics.withTranslation
import com.example.composingapp.utils.interfaces.LeafDrawer
import com.example.composingapp.utils.viewtools.NotePositionDict

class StemLeaf(
        val notePositionDict: NotePositionDict,
        val stemPaint: Paint
) : LeafDrawer {
    val noteX = notePositionDict.noteX
    val noteY = notePositionDict.noteY
    val thirdLineY: Float =
            notePositionDict.toneToBarlineYMap[notePositionDict.clef.barlineTones[2]] ?: 0f
    val stemHeight = notePositionDict.octaveHeight
    val stemWidth = stemPaint.strokeWidth
    val xDistanceFromCenter = notePositionDict.noteHorizontalRadius - stemWidth

    override fun draw(canvas: Canvas?) {
        canvas?.withTranslation(noteX, noteY) {
            if (noteY > thirdLineY) {
                canvas.drawLine(xDistanceFromCenter, 0f, xDistanceFromCenter,
                        -stemHeight, stemPaint)
            } else {
                canvas.drawLine(-xDistanceFromCenter, 0f,
                        -xDistanceFromCenter, stemHeight, stemPaint)
            }
        }
    }

    private companion object {
        const val TAG = "StemLeaf"
    }
}