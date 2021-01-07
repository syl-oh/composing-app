package com.example.composingapp.views.noteviewdrawer.composites

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import androidx.core.graphics.withTranslation
import com.example.composingapp.utils.interfaces.componentdrawer.ComponentDrawer
import com.example.composingapp.utils.interfaces.componentdrawer.CompositeDrawer
import com.example.composingapp.utils.interfaces.componentdrawer.LeafDrawer
import com.example.composingapp.utils.music.Music
import com.example.composingapp.views.viewtools.positiondict.NotePositionDict
import kotlin.math.atan

class ShortRestComposite(
        val notePositionDict: NotePositionDict,
        val paint: Paint
) : CompositeDrawer {
    private val drawers = mutableListOf<ComponentDrawer>()

    init {
        val x = notePositionDict.noteX
        val y = notePositionDict.fourthLineY
        val dy = notePositionDict.singleSpaceHeight
        val dx = notePositionDict.singleSpaceHeight / 4 + paint.strokeWidth / 2

        // Add the required leaves
        add(ShortRestLeaf(notePositionDict, paint, x, y))
        if (notePositionDict.note.noteLength == Music.NoteLength.SIXTEENTH_NOTE) {
            add(ShortRestLeaf(notePositionDict, paint, x - dx, y + dy))
        }
    }

    override fun draw(canvas: Canvas?) {
        drawers.map { it.draw(canvas) }
    }

    override fun add(drawerComponent: ComponentDrawer) {
        drawers.add(drawerComponent)
    }

    override fun remove(drawerComponent: ComponentDrawer) {
        drawers.remove(drawerComponent)
    }


    class ShortRestLeaf(
            val notePositionDict: NotePositionDict,
            val paint: Paint,
            val x: Float,
            val y: Float,
    ) : LeafDrawer {
        private val halfSpace = notePositionDict.singleSpaceHeight / 2
        private val arcPaint: Paint = Paint(paint).apply {
            style = Paint.Style.STROKE
            strokeJoin = Paint.Join.ROUND
        }
        private val arcRect: RectF =
                RectF(0F, -halfSpace, 2 * halfSpace, halfSpace)
        private val startAngle: Float = atan(halfSpace / 2 / (arcRect.width() / 2))
                .let { it * 180 / Math.PI }.toFloat()  // Convert to degrees and a float
        private val sweepAngle: Float = 90f + startAngle
        private val arcStrokeWidth = arcPaint.strokeWidth
        private val lineRun: Float = (arcRect.width() - arcStrokeWidth - arcRect.width() / 2)
        private val lineRise: Float = -3 * halfSpace

        override fun draw(canvas: Canvas?) {
            canvas?.apply {
                withTranslation(x - halfSpace/2, y + halfSpace / 2) {
                    drawOval(0F, 0F, halfSpace, halfSpace, paint)
                    drawArc(arcRect, startAngle, sweepAngle, false, arcPaint)
                    withTranslation(arcRect.width() / 2, -lineRise + halfSpace / 2) {
                        drawLine(0f, 0f, lineRun, lineRise, paint)
                    }
                }
            }
        }

        companion object {
//            const val TAG = "ShortRestLeaf"
        }
    }
}