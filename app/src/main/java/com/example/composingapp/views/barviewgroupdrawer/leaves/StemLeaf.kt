package com.example.composingapp.views.barviewgroupdrawer.leaves

import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.graphics.withTranslation
import com.example.composingapp.utils.interfaces.PositionDict
import com.example.composingapp.utils.interfaces.componentdrawer.LeafDrawer
import com.example.composingapp.views.viewtools.ViewConstants
import com.example.composingapp.views.viewtools.positiondict.NotePositionDict

class StemLeaf(
        val notePositionDict: NotePositionDict,
        val paint: Paint,
        val stemDirection: StemDirection =
                if (notePositionDict.noteY > notePositionDict.scorePositionDict.thirdLineY) StemDirection.POINTS_UP
                else StemDirection.POINTS_DOWN,
        val stemHeight: Float = notePositionDict.scorePositionDict.octaveHeight
) : LeafDrawer {
    private val stemWidth: Float = paint.strokeWidth
    private val xDistanceFromCenter: Float = notePositionDict.noteHorizontalRadius - stemWidth

    init {
        paint.apply { strokeWidth = ViewConstants.STEM_WIDTH }
    }

    override fun draw(canvas: Canvas?, positionDict: PositionDict) {
        if (positionDict is NotePositionDict) {
            canvas?.withTranslation(positionDict.noteX, positionDict.noteY) {
                if (stemDirection == StemDirection.POINTS_UP) {
                    canvas.drawLine(xDistanceFromCenter, 0f, xDistanceFromCenter,
                            -stemHeight, paint)
                } else {
                    canvas.drawLine(-xDistanceFromCenter, 0f,
                            -xDistanceFromCenter, stemHeight, paint)
                }
            }
        }
    }

    private companion object {
//        const val TAG = "StraightStemLeaf"
    }

    /**
     *  Enum class for specifying which direction the stem should point
     */
    enum class StemDirection {
        POINTS_UP,
        POINTS_DOWN
    }
}