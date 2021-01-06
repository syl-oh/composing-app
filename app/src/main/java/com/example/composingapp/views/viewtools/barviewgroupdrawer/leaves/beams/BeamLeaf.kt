package com.example.composingapp.views.viewtools.barviewgroupdrawer.leaves.beams

import android.graphics.Canvas
import android.graphics.Paint
import com.example.composingapp.utils.Line
import com.example.composingapp.utils.interfaces.componentdrawer.LeafDrawer
import com.example.composingapp.views.viewtools.ViewConstants

abstract class BeamLeaf(
        protected val givenPaint: Paint,
) : LeafDrawer {
    protected val paint = Paint(givenPaint).apply { strokeWidth = 2 * ViewConstants.STEM_WIDTH }
    abstract val startX: Float
    abstract val endX: Float
    abstract val startY: Float
    abstract val endY: Float
    abstract val beamLine: Line

    override fun draw(canvas: Canvas?) {
        canvas?.apply {
            drawLine(startX, startY, endX, endY, paint)
        }
    }
}