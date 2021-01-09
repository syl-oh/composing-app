package com.example.composingapp.views.noteviewdrawer.composites

import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.graphics.withTranslation
import com.example.composingapp.utils.interfaces.PositionDict
import com.example.composingapp.utils.interfaces.componentdrawer.LeafDrawer
import com.example.composingapp.views.viewtools.positiondict.NotePositionDict
import kotlin.math.ceil
import kotlin.math.floor

object LedgerLineLeaf : LeafDrawer {
    override fun draw(canvas: Canvas?, positionDict: PositionDict, paint: Paint) {
        if (positionDict is NotePositionDict) {
            val noteY = positionDict.noteY
            val noteX = positionDict.noteX
            val singleSpaceHeight: Float = positionDict.scorePositionDict.singleSpaceHeight
            val ledgerLineHalfWidth: Float = 3 * positionDict.noteHorizontalRadius / 2

            positionDict.scorePositionDict.fifthLineY.let {
                if (noteY < it) {
                    var currentY = it - singleSpaceHeight
                    while (floor(noteY) <= currentY) {
                        canvas?.withTranslation(noteX, currentY) {
                            canvas.drawLine(-ledgerLineHalfWidth, 0f, ledgerLineHalfWidth, 0f, paint)
                        }
                        currentY -= singleSpaceHeight
                    }
                }
            }

            positionDict.scorePositionDict.firstLineY.let {
                if (noteY > it) {
                    var currentY = it + singleSpaceHeight
                    while (ceil(noteY) >= currentY) {
                        canvas?.withTranslation(noteX, currentY) {
                            canvas.drawLine(-ledgerLineHalfWidth, 0f, ledgerLineHalfWidth, 0f, paint)
                        }
                        currentY += singleSpaceHeight
                    }
                }
            }
        }
    }
}