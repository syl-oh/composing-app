package com.example.composingapp.views.barviewgroupdrawer.leaves.beams

import android.graphics.Paint
import com.example.composingapp.utils.Line
import com.example.composingapp.views.NoteView
import com.example.composingapp.views.barviewgroupdrawer.leaves.StemLeaf
import com.example.composingapp.views.barviewgroupdrawer.leaves.beams.BeamHelper.getStemX

/**
 * Constructor
 *
 * @param noteView NoteView to add the SecondaryBeamLeaf to
 * @param stemDirection StemDirection of the NoteView
 * @param paint Paint to calculate x position of the NoteView's stem
 * @param yTranslation Float - vertical shift of the SecondaryBeamLeaf. Defaults to 0.
 * @param extendsBefore Boolean - true if this SecondaryBeamLeaf should extend before the NoteView
 * @param extendsAfter Boolean - true if this SecondaryBeamLeaf should extend after the NoteView
 */
class SecondaryBeamLeaf(
        noteView: NoteView,
        stemDirection: StemLeaf.StemDirection,
        paint: Paint,
        yTranslation: Float = 0F,
        extendsBefore: Boolean,
        extendsAfter: Boolean,
        line: Line
) : BeamLeaf() {
    override val beamLine: Line = line.moveVertically(
            if (stemDirection == StemLeaf.StemDirection.POINTS_UP) yTranslation
            else -yTranslation)

    private val beamLength = noteView.notePositionDict.noteHorizontalRadius * 2
    private val stemX = getStemX(noteView, paint, stemDirection)
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