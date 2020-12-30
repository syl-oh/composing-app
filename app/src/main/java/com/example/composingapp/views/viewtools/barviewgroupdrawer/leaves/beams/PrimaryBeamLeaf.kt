package com.example.composingapp.views.viewtools.barviewgroupdrawer.leaves.beams

import android.graphics.Canvas
import android.graphics.Paint
import com.example.composingapp.utils.Line
import com.example.composingapp.views.NoteView
import com.example.composingapp.views.viewtools.barviewgroupdrawer.leaves.beams.BeamHelper.getStemX
import com.example.composingapp.views.viewtools.noteviewdrawer.leaves.StemLeaf
import kotlin.math.max
import kotlin.math.min


class PrimaryBeamLeaf(
        noteViews: List<NoteView>,
        stemDirection: StemLeaf.StemDirection,
        givenPaint: Paint,
        yTranslation: Float = 0F
) : BeamLeaf(noteViews, stemDirection, givenPaint) {
    override val startY: Float
    override val endY: Float

    init {
        beamLine.yIntercept += yTranslation
        startY = beamLine.yAt(startX)
        endY = beamLine.yAt(endX)
    }

    override fun draw(canvas: Canvas?) {
        canvas?.apply {
            drawLine(startX, startY, endX, endY, paint)
        }
    }

    companion object {
        private const val TAG = "PrimaryBeamLeaf"
    }
}