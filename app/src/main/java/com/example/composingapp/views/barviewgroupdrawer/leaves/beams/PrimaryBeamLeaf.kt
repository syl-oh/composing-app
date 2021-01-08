package com.example.composingapp.views.barviewgroupdrawer.leaves.beams

import android.graphics.Paint
import com.example.composingapp.utils.Line
import com.example.composingapp.views.NoteView
import com.example.composingapp.views.barviewgroupdrawer.leaves.StemLeaf
import kotlin.math.max
import kotlin.math.min


class PrimaryBeamLeaf(
        private val noteViews: List<NoteView>,
        private val stemDirection: StemLeaf.StemDirection,
        givenPaint: Paint,
        yTranslation: Float = 0F,
        requestedBeamLine: Line? = null,
) : BeamLeaf(givenPaint) {

    override var beamLine: Line = requestedBeamLine ?: createBeamLine()

    init {
        beamLine = beamLine.moveVertically(
                if (stemDirection == StemLeaf.StemDirection.POINTS_UP) yTranslation
                else -yTranslation)
    }

    private val firstStemX = BeamHelper.getStemX(noteViews.first(), paint, stemDirection)
    private val lastStemX = BeamHelper.getStemX(noteViews.last(), paint, stemDirection)
    override val startX: Float =
            if (stemDirection == StemLeaf.StemDirection.POINTS_DOWN) firstStemX
            else firstStemX + paint.strokeWidth

    override val endX: Float =
            if (stemDirection == StemLeaf.StemDirection.POINTS_DOWN) lastStemX - paint.strokeWidth
            else lastStemX
    override val startY: Float = beamLine.yAt(startX)
    override val endY: Float = beamLine.yAt(endX)


    /**
     * Produces a beam line such that:
     *                      (1) its slope is less than 1/2 of a single space in magnitude
     *                      (2) its slope following the trajectory of the first and last note
     *                      (3) it is always 1/2 an octave away from every note
     *                      (4) it is always a full octave away from at least 1 note
     *
     * @return Line representing the beam line
     */
    private fun createBeamLine(): Line {
        fun yPos(nV: NoteView) = nV.notePositionDict.noteY + nV.y
        fun xPos(nV: NoteView) = nV.notePositionDict.noteX + nV.x

        val octaveHeight: Float = noteViews.first().notePositionDict.positionDict.octaveHeight
        val semiSpace: Float = noteViews.first().notePositionDict.positionDict.singleSpaceHeight / 2
        val firstY = yPos(noteViews.first())
        val lastY = yPos(noteViews.last())
        val dy = lastY - firstY
        val dx = xPos(noteViews.last()) - xPos(noteViews.first())

        // Find the "anchor" (x,y) coordinates for the beam's line
        val maxY: Float =
                (noteViews.map { it.notePositionDict.noteY }.maxOrNull()) ?: firstY
        val minY: Float =
                (noteViews.map { it.notePositionDict.noteY }.minOrNull()) ?: firstY
        val dMaxToMinY = maxY - minY

        // Find the slope of the beam's line
        val slope: Float = if (dy > 0) {
            min(dy, semiSpace) / dx
        } else max(dy, -semiSpace) / dx

        if (stemDirection == StemLeaf.StemDirection.POINTS_DOWN) {
            // Init the beamLine based on the stem direction
            val xOfMaxY: Float = with(noteViews.first { it.notePositionDict.noteY == maxY }) {
                this.notePositionDict.noteX + this.x
            }
            // At least one of the notes should be an octave in length
            return if (dMaxToMinY < octaveHeight / 2) {
                Line(xOfMaxY, maxY + octaveHeight - dMaxToMinY, slope)
            } else {
                Line(xOfMaxY, maxY + octaveHeight / 2, slope)
            }

        } else {
            // At least one of the notes should be an octave in length
            val xOfMinY: Float = with(noteViews.first { it.notePositionDict.noteY == minY }) {
                this.notePositionDict.noteX + this.x
            }
            return if (dMaxToMinY < octaveHeight / 2) {
                Line(xOfMinY, minY - octaveHeight + dMaxToMinY, slope)
            } else {
                Line(xOfMinY, minY - octaveHeight / 2, slope)
            }
        }
    }

    companion object {
//        private const val TAG = "PrimaryBeamLeaf"
    }
}