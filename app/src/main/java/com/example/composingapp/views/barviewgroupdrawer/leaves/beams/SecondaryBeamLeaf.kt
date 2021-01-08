package com.example.composingapp.views.barviewgroupdrawer.leaves.beams

import android.graphics.Paint
import com.example.composingapp.utils.Line
import com.example.composingapp.views.NoteView
import com.example.composingapp.views.barviewgroupdrawer.leaves.StemLeaf
import com.example.composingapp.views.barviewgroupdrawer.leaves.beams.BeamHelper.getStemX

class SecondaryBeamLeaf(
        val noteView: NoteView,
        stemDirection: StemLeaf.StemDirection,
        givenPaint: Paint,
        yTranslation: Float = 0F,
        extendsBefore: Boolean,
        extendsAfter: Boolean,
        override var beamLine: Line
) : BeamLeaf(givenPaint) {
    init {
        beamLine = beamLine.moveVertically(
                if (stemDirection == StemLeaf.StemDirection.POINTS_UP) yTranslation
                else -yTranslation)
    }

    val beamLength = noteView.getNotePositionDict().noteHorizontalRadius * 2
    val stemX = getStemX(noteView, paint, stemDirection)
    override val startX: Float
    override val endX: Float
    override val startY: Float
    override val endY: Float

    init {
        if (stemDirection == StemLeaf.StemDirection.POINTS_UP) {
            startX = if (extendsBefore) stemX - beamLength else stemX + paint.strokeWidth
            endX = if (extendsAfter) stemX + beamLength else stemX
        } else {
            startX = if (extendsBefore) stemX - beamLength else stemX
            endX = if (extendsAfter) stemX + beamLength else stemX - paint.strokeWidth
        }
        startY = beamLine.yAt(startX)
        endY = beamLine.yAt(endX)
    }

    companion object {
//        private const val TAG = "SecondaryBeamLeaf"
    }
}