package com.example.composingapp.views.viewtools.barviewgroupdrawer.leaves

import android.graphics.Canvas
import android.graphics.Paint
import com.example.composingapp.utils.interfaces.LeafDrawer
import com.example.composingapp.views.viewtools.positiondict.BarPositionDict
import com.example.composingapp.views.viewtools.positiondict.PositionDict

class BarlineLeaf(
        barPositionDict: BarPositionDict,
        val paint: Paint
) : LeafDrawer {
    val barlineYPositions = barPositionDict.toneToBarlineYMap.values
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