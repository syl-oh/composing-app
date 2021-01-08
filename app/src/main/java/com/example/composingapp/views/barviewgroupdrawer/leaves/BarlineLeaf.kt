package com.example.composingapp.views.barviewgroupdrawer.leaves

import android.graphics.Canvas
import android.graphics.Paint
import com.example.composingapp.utils.interfaces.componentdrawer.LeafDrawer
import com.example.composingapp.views.viewtools.positiondict.BarPositionDict

class BarlineLeaf(
        barPositionDict: BarPositionDict,
        val paint: Paint
) : LeafDrawer {
    val barlineYPositions = barPositionDict.positionDict.toneToBarlineYMap.values
    val startX = 0f
    val endX = barPositionDict.barWidth - 1

    override fun draw(canvas: Canvas?) {
        canvas?.apply {
            // Draw at each barline y position
            barlineYPositions.map {
                drawLine(startX, it, endX, it, paint)
            }
        }
    }
}