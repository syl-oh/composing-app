package com.example.composingapp.views.noteviewdrawer.leaves

import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.graphics.withRotation
import androidx.core.graphics.withTranslation
import com.example.composingapp.utils.interfaces.componentdrawer.LeafDrawer
import com.example.composingapp.views.viewtools.positiondict.NotePositionDict

abstract class BaseLeaf(
        val notePositionDict: NotePositionDict,
        ) : LeafDrawer {
    abstract val notePaint: Paint
    abstract val vertRadius: Float
    abstract val angle: Float
    protected val filledNoteAngle = 330f
    protected val halfNoteAngleInside = filledNoteAngle
    protected val wholeNoteBaseAngle = 0f
    protected val wholeNoteInnerAngle = 75f
    private val horzRadius = notePositionDict.noteHorizontalRadius
    private val noteX = notePositionDict.noteX
    private val noteY = notePositionDict.noteY
    override fun draw(canvas: Canvas?) {
        canvas?.withTranslation(noteX, noteY) {
            canvas.withRotation(angle) {
                canvas.drawOval(
                        -horzRadius, vertRadius, horzRadius, -vertRadius, notePaint)
            }
        }
    }

    companion object {
//        private const val TAG = "BaseLeaf"
    }
}