package com.example.composingapp.views.barviewgroupdrawer.leaves

import android.graphics.Canvas
import android.graphics.Paint
import com.example.composingapp.utils.interfaces.PositionDict
import com.example.composingapp.utils.interfaces.componentdrawer.LeafDrawer
import com.example.composingapp.views.viewtools.positiondict.BarPositionDict

class BarlineLeaf(
        barPositionDict: BarPositionDict,
        val paint: Paint
) : LeafDrawer {
    val barlineYPositions = barPositionDict.scorePositionDict.toneToBarlineYMap.values
    val startX = 0f
    val endX = barPositionDict.barWidth - 1

    override fun draw(canvas: Canvas?, positionDict: PositionDict) {
        canvas?.apply {
            // Draw at each barline y position
            barlineYPositions.map {
                drawLine(startX, it, endX, it, paint)
            }
        }
    }
}