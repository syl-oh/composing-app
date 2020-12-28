package com.example.composingapp.views.viewtools.noteviewdrawer.composites

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import androidx.core.graphics.withTranslation
import com.example.composingapp.utils.interfaces.ComponentDrawer
import com.example.composingapp.utils.interfaces.CompositeDrawer
import com.example.composingapp.utils.interfaces.LeafDrawer
import com.example.composingapp.views.viewtools.NotePositionDict
import com.example.composingapp.views.viewtools.ViewConstants.STEM_WIDTH
import kotlin.math.atan

class ShortRestComposite(
        val notePositionDict: NotePositionDict,
        val paint: Paint
) : CompositeDrawer {
    private val drawers = mutableListOf<ComponentDrawer>()

    init {
        add(ShortRestLeaf(notePositionDict, paint))
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
            val x: Float = notePositionDict.noteX,
            val y: Float = notePositionDict.thirdLineY - notePositionDict.singleSpaceHeight,
    ) : LeafDrawer {
        private val halfSpace = notePositionDict.singleSpaceHeight / 2
        private val arcPaint: Paint = Paint(paint).apply {
            style = Paint.Style.STROKE
            strokeWidth = STEM_WIDTH.toFloat()
            strokeJoin = Paint.Join.ROUND
        }
        private val arcRect: RectF =
                RectF(0F, -halfSpace, 2 * halfSpace, halfSpace)
        private val startAngle: Float = atan(halfSpace / 2 / (arcRect.width() / 2))
                .let { it * 180 / Math.PI }.toFloat()  // Convert to degrees as a float
        private val sweepAngle: Float = 90f + startAngle
        private val arcStrokeWidth = arcPaint.strokeWidth

        override fun draw(canvas: Canvas?) {
            canvas?.apply {
                withTranslation(x, y) {
                    withTranslation(halfSpace / 2, halfSpace / 2) {
                        drawOval(0F, 0F, halfSpace, halfSpace, paint)
                        drawArc(arcRect, startAngle, sweepAngle, false, arcPaint)
                        drawLine(arcRect.width() - arcStrokeWidth, halfSpace / 2,
                                arcRect.width() / 2, 3 * halfSpace, paint)
                    }
                }
            }
        }

        companion object {
            const val TAG = "ShortRestLeaf"
        }
    }
}