package com.example.composingapp.views.viewtools.noteviewdrawer.leaves

import android.graphics.Canvas
import android.graphics.Paint
import android.util.Log
import androidx.core.graphics.withTranslation
import com.example.composingapp.utils.interfaces.LeafDrawer
import com.example.composingapp.views.viewtools.ViewConstants
import com.example.composingapp.views.viewtools.positiondict.NotePositionDict

class StemLeaf(
        val notePositionDict: NotePositionDict,
        val paint: Paint,
        val stemDirection: StemDirection =
                if (notePositionDict.noteY > notePositionDict.thirdLineY) StemDirection.POINTS_UP
                else StemDirection.POINTS_DOWN,
        val stemHeight: Float = notePositionDict.octaveHeight
) : LeafDrawer {
    private val noteX: Float = notePositionDict.noteX
    private val noteY: Float = notePositionDict.noteY
    private val stemWidth: Float = paint.strokeWidth
    private val xDistanceFromCenter: Float = notePositionDict.noteHorizontalRadius - stemWidth

    init {
        paint.apply { strokeWidth = ViewConstants.STEM_WIDTH }
    }

    override fun draw(canvas: Canvas?) {
        Log.d(TAG, "draw: stemDirection is: $stemDirection")
        canvas?.withTranslation(noteX, noteY) {
            if (stemDirection == StemDirection.POINTS_UP) {
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

    /**
     *  Enum class for specifying which direction the stem should point
     */
    enum class StemDirection {
        POINTS_UP,
        POINTS_DOWN
    }
}