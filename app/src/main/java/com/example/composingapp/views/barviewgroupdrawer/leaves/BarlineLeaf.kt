package com.example.composingapp.views.barviewgroupdrawer.leaves

import android.graphics.Canvas
import android.graphics.Paint
import com.example.composingapp.utils.interfaces.ui.PositionDict
import com.example.composingapp.utils.interfaces.componentdrawer.LeafDrawer
import com.example.composingapp.views.viewtools.positiondict.BarPositionDict

object BarlineLeaf : LeafDrawer {
    override fun draw(canvas: Canvas?, positionDict: PositionDict, paint: Paint) {
        if (positionDict is BarPositionDict) {
            canvas?.apply {
                // Draw at each barline y position
                positionDict.scorePositionDict.toneToBarlineYMap.values.map {
                    drawLine(0f, it, positionDict.barWidth - 1, it, paint)
                }
            }
        }
    }
}