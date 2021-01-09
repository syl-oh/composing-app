package com.example.composingapp.views.accidentals

import android.graphics.Canvas
import android.graphics.Paint
import com.example.composingapp.utils.interfaces.PositionDict
import com.example.composingapp.utils.interfaces.componentdrawer.LeafDrawer
import com.example.composingapp.views.accidentals.AccidentalLeaf.W_TO_H_RATIO
import com.example.composingapp.views.viewtools.ViewConstants
import com.example.composingapp.views.viewtools.positiondict.NotePositionDict

object FlatLeaf : LeafDrawer {
    override fun draw(canvas: Canvas, positionDict: PositionDict, paint: Paint) {
        if (positionDict is NotePositionDict) {
            canvas.apply {
                val halfSpace = positionDict.scorePositionDict.singleSpaceHeight / 2
                val mBoundingRectHeight = halfSpace * 4
                val boundingRectWidth = (W_TO_H_RATIO * mBoundingRectHeight).toFloat()
                val centerX = positionDict.noteX -
                        2 * (ViewConstants.NOTE_W_TO_H_RATIO * positionDict.noteVerticalRadius)
                val centerY = positionDict.noteY
                val baseRectBottomY = centerY + halfSpace
                val baseRectRightX = centerX + boundingRectWidth / 2

                // Draw the vertical line
                drawLine(centerX, centerY - mBoundingRectHeight / 2, centerX,
                        baseRectBottomY, paint)

                // Save the old style
                val oldStyle = paint.style
                paint.style = Paint.Style.STROKE
                // Draw the demi-heart
                drawLine(centerX, baseRectBottomY, baseRectRightX, centerY, paint)
                drawArc(centerX, centerY - halfSpace / 2, baseRectRightX,
                        centerY + halfSpace / 2, 0f, -180f, false, paint)
                paint.style = oldStyle
            }
        }
    }
}