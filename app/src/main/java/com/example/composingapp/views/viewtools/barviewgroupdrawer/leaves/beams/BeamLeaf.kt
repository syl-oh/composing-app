package com.example.composingapp.views.viewtools.barviewgroupdrawer.leaves.beams

import android.graphics.Paint
import com.example.composingapp.utils.Line
import com.example.composingapp.utils.interfaces.LeafDrawer
import com.example.composingapp.views.NoteView
import com.example.composingapp.views.viewtools.ViewConstants
import com.example.composingapp.views.viewtools.noteviewdrawer.leaves.StemLeaf
import kotlin.math.max
import kotlin.math.min

abstract class BeamLeaf(
        protected val noteViews: List<NoteView>,
        protected val stemDirection: StemLeaf.StemDirection,
        givenPaint: Paint,
) : LeafDrawer {
    protected val paint = Paint(givenPaint).apply { strokeWidth = 2 * ViewConstants.STEM_WIDTH }
    protected val startX: Float
    protected val endX: Float
    abstract val startY: Float
    abstract val endY: Float
    val beamLine: Line

    init {
        fun yPos(nV: NoteView) = nV.notePositionDict.noteY + nV.y
        fun xPos(nV: NoteView) = nV.notePositionDict.noteX + nV.x
        val octaveHeight: Float = noteViews.first().notePositionDict.octaveHeight
        val semiSpace: Float = noteViews.first().notePositionDict.singleSpaceHeight / 2
        val firstY = yPos(noteViews.first())
        val lastY = yPos(noteViews.last())
        val dy = lastY - firstY
        val dx = xPos(noteViews.last()) - xPos(noteViews.first())
        // Find the "anchor" (x,y) coordinates for the beam's line
        val maxY: Float =
                (noteViews.map { it.notePositionDict.noteY }.maxOrNull()) ?: firstY
        val minY: Float =
                (noteViews.map { it.notePositionDict.noteY }.minOrNull()) ?: firstY

        // Find the slope of the beam's line
        val slope: Float = if (dy > 0) {
            -min(dy, semiSpace) / dx
        } else max(dy, -semiSpace) / dx


        if (stemDirection == StemLeaf.StemDirection.POINTS_DOWN) {
            startX = BeamHelper.getStemX(noteViews.first(), givenPaint, stemDirection)
            endX = BeamHelper.getStemX(noteViews.last(), givenPaint, stemDirection)

            // At least one of the notes should be an octave in length
            val dMaxToMinY = maxY - minY
            beamLine = if (dMaxToMinY < octaveHeight / 2) {
                Line(startX, maxY + octaveHeight - dMaxToMinY, slope)
            } else {
                Line(startX, maxY + octaveHeight / 2, slope)
            }

        } else {
            startX = BeamHelper.getStemX(noteViews.first(), givenPaint, stemDirection)
            endX = BeamHelper.getStemX(noteViews.last(), givenPaint, stemDirection)

            // At least one of the notes should be an octave in length
            val dMaxToMinY = maxY - minY
            beamLine = if (dMaxToMinY < octaveHeight / 2) {
                Line(startX, minY - octaveHeight + dMaxToMinY, slope)
            } else {
                Line(startX, minY - octaveHeight / 2, slope)
            }
        }
    }
}