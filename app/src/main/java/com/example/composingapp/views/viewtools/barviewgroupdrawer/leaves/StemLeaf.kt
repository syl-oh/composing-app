package com.example.composingapp.views.viewtools.barviewgroupdrawer.leaves

import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.graphics.withTranslation
import com.example.composingapp.utils.interfaces.LeafDrawer
import com.example.composingapp.views.viewtools.positiondict.NotePositionDict

class StemLeaf(
        val notePositionDict: NotePositionDict,
        val paint: Paint,
        val pointsDown: Boolean = notePositionDict.noteY > notePositionDict.thirdLineY,
        val stemHeight: Float = notePositionDict.octaveHeight
) : LeafDrawer {
    private val noteX: Float = notePositionDict.noteX
    private val noteY: Float = notePositionDict.noteY
    private val stemWidth: Float = paint.strokeWidth
    private val xDistanceFromCenter: Float = notePositionDict.noteHorizontalRadius - stemWidth

    override fun draw(canvas: Canvas?) {
        canvas?.withTranslation(noteX, noteY) {
            if (pointsDown) {
                canvas.drawLine(xDistanceFromCenter, 0f, xDistanceFromCenter,
                        -stemHeight, paint)
            } else {
                canvas.drawLine(-xDistanceFromCenter, 0f,
                        -xDistanceFromCenter, stemHeight, paint)
            }
        }
    }

    private companion object {
        const val TAG = "StraightStemLeaf"
    }
}