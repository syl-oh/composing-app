package com.example.composingapp.views.noteviewdrawer.composites

import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.graphics.withTranslation
import com.example.composingapp.utils.interfaces.PositionDict
import com.example.composingapp.utils.interfaces.componentdrawer.LeafDrawer
import com.example.composingapp.views.viewtools.positiondict.NotePositionDict
import kotlin.math.ceil
import kotlin.math.floor

class LedgerLineLeaf(
        notePositionDict: NotePositionDict,
        val paint: Paint
) : LeafDrawer {
    private val fifthLine: Float = notePositionDict.scorePositionDict.fifthLineY
    private val firstLine: Float = notePositionDict.scorePositionDict.firstLineY
    private val singleSpaceHeight: Float = notePositionDict.scorePositionDict.singleSpaceHeight
    private val ledgerLineHalfWidth: Float = 3 * notePositionDict.noteHorizontalRadius / 2


    override fun draw(canvas: Canvas?, positionDict: PositionDict) {
        if (positionDict is NotePositionDict) {
            val noteY = positionDict.noteY
            val noteX = positionDict.noteX
            fifthLine.let {
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

            firstLine.let {
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