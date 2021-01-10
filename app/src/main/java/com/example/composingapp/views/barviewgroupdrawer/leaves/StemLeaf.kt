package com.example.composingapp.views.barviewgroupdrawer.leaves

import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.graphics.withTranslation
import com.example.composingapp.utils.interfaces.ui.PositionDict
import com.example.composingapp.utils.interfaces.componentdrawer.LeafDrawer
import com.example.composingapp.views.viewtools.positiondict.NotePositionDict

object StemLeaf: LeafDrawer {
    override fun draw(
            canvas: Canvas?,
            positionDict: PositionDict,
            paint: Paint
    ) {
        if (positionDict is NotePositionDict) {
            val xDistanceFromCenter: Float = positionDict.noteHorizontalRadius - paint.strokeWidth
            canvas?.withTranslation(positionDict.noteX, positionDict.noteY) {
                if (positionDict.stemDirection == StemDirection.POINTS_UP) {
                    canvas.drawLine(xDistanceFromCenter, 0f, xDistanceFromCenter,
                            -positionDict.stemHeight, paint)
                } else {
                    canvas.drawLine(-xDistanceFromCenter, 0f,
                            -xDistanceFromCenter, positionDict.stemHeight, paint)
                }
            }
        }
    }

    /**
     *  Enum class for specifying which direction the stem should point
     */
    enum class StemDirection {
        POINTS_UP,
        POINTS_DOWN
    }
}