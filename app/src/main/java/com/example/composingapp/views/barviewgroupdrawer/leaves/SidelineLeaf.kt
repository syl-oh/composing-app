package com.example.composingapp.views.barviewgroupdrawer.leaves

import android.graphics.Canvas
import android.graphics.Paint
import com.example.composingapp.utils.interfaces.ui.PositionDict
import com.example.composingapp.utils.interfaces.componentdrawer.LeafDrawer
import com.example.composingapp.views.viewtools.positiondict.BarPositionDict

object SidelineLeaf: LeafDrawer {
    override fun draw(canvas: Canvas?, positionDict: PositionDict, paint: Paint) {
        if (positionDict is BarPositionDict) {
            canvas?.apply {
                // Draw at both ends of the bar
                listOf(0f, positionDict.barWidth - 1).map {
                    drawLine(it, positionDict.scorePositionDict.fifthLineY, it,
                            positionDict.scorePositionDict.firstLineY, paint)
                }
            }
        }
    }
}
