package com.example.composingapp.views.barviewgroupdrawer.leaves

import android.graphics.Canvas
import android.graphics.Paint
import com.example.composingapp.utils.interfaces.componentdrawer.LeafDrawer
import com.example.composingapp.views.viewtools.positiondict.BarPositionDict

class SidelineLeaf(
        private val barPositionDict: BarPositionDict,
        val paint: Paint
) : LeafDrawer {
    private val barlineYPositions = barPositionDict.positionDict.toneToBarlineYMap.values
    private val startX = 0f
    private val endX = barPositionDict.barWidth - 1
    private val topBarlineY: Float = barlineYPositions.minOrNull() ?: barlineYPositions.first()
    private val bottomBarlineY: Float = barlineYPositions.maxOrNull() ?: barlineYPositions.last()

    override fun draw(canvas: Canvas?) {
        canvas?.apply {
            // Draw at both ends of the bar
            listOf(startX, endX).map {
                drawLine(it, topBarlineY, it, bottomBarlineY, paint)
            }
        }
    }
}
