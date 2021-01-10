package com.example.composingapp.views.barviewgroupdrawer.leaves.beams

import android.graphics.Canvas
import android.graphics.Paint
import com.example.composingapp.utils.Line
import com.example.composingapp.utils.interfaces.ui.PositionDict
import com.example.composingapp.utils.interfaces.componentdrawer.LeafDrawer
import com.example.composingapp.views.viewtools.ViewConstants.STEM_WIDTH

abstract class BeamLeaf() : LeafDrawer {
    abstract val startX: Float
    abstract val endX: Float
    abstract val startY: Float
    abstract val endY: Float
    abstract val beamLine: Line

    override fun draw(canvas: Canvas?, positionDict: PositionDict, paint: Paint) {
        canvas?.apply {
            val originalStrokeWidth: Float = paint.strokeWidth
            drawLine(startX, startY, endX, endY, paint.apply { strokeWidth = 2* STEM_WIDTH })
            paint.apply { strokeWidth = originalStrokeWidth }
        }
    }
}