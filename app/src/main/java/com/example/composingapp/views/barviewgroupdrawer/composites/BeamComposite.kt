package com.example.composingapp.views.barviewgroupdrawer.composites

import android.graphics.Canvas
import android.graphics.Paint
import com.example.composingapp.utils.Line
import com.example.composingapp.utils.interfaces.PositionDict
import com.example.composingapp.utils.interfaces.componentdrawer.ComponentDrawer
import com.example.composingapp.utils.interfaces.componentdrawer.CompositeDrawer
import com.example.composingapp.utils.music.Music
import com.example.composingapp.views.NoteView
import com.example.composingapp.views.barviewgroupdrawer.leaves.StemLeaf
import com.example.composingapp.views.barviewgroupdrawer.leaves.beams.BeamHelper
import com.example.composingapp.views.barviewgroupdrawer.leaves.beams.BeamHelper.findStemDirection
import com.example.composingapp.views.barviewgroupdrawer.leaves.beams.BeamHelper.groupByNoteLengthCondition
import com.example.composingapp.views.barviewgroupdrawer.leaves.beams.PrimaryBeamLeaf
import com.example.composingapp.views.barviewgroupdrawer.leaves.beams.SecondaryBeamLeaf
import com.example.composingapp.views.viewtools.positiondict.BarPositionDict
import kotlin.math.max
import kotlin.math.min

class BeamComposite(
        beamGroup: List<NoteView>,
        barPositionDict: BarPositionDict,
        val paint: Paint,
): CompositeDrawer  {
    private val drawers = mutableListOf<ComponentDrawer>()
    private val beamGroupDicts = beamGroup.map { it.notePositionDict }
    private val stemDirection = findStemDirection(beamGroupDicts)
    private val primaryBeam = createBeamLine(beamGroup, stemDirection)
    private val beamYShift = barPositionDict.scorePositionDict.singleSpaceHeight / 4

    init {
        add(PrimaryBeamLeaf(beamGroup, stemDirection, paint, line = primaryBeam))
        beamGroup.map {
            it.notePositionDict.stemDirection = stemDirection
            it.notePositionDict.stemHeight = BeamHelper.heightToBeam(it, primaryBeam, paint, stemDirection)
            it.noteViewDrawer.add(StemLeaf)
        }

        // Add another primary beam or secondary beams for sixteenth note groups
        val groupBySixteenth =
                beamGroup.groupByNoteLengthCondition({ it == Music.NoteLength.SIXTEENTH_NOTE })

        for (beamGroup in groupBySixteenth) {
            // Verify that this group contains only sixteenth notes (only need to check the first
                // element due to the implementation of .groupByNoteLengthCondition()
            if (beamGroup.first().notePositionDict.note.noteLength == Music.NoteLength.SIXTEENTH_NOTE) {
                // Function to simplify adding of secondary beams
                fun List<NoteView>.addSecondary(extendsBefore: Boolean, extendsAfter: Boolean) =
                        add(SecondaryBeamLeaf(this.first(), stemDirection, paint, beamYShift, extendsBefore,
                                extendsAfter, primaryBeam))

                // Add secondary beam if the size of the sixteenth note group is 1, otherwise add
                //     another primary beam
                if (beamGroup.size == 1) {
                    // Find the position of this specific group in the groupBySixteenth
                    with(groupBySixteenth) {
                        when {
                            // If this is the first element in the group, extend after but not before
                            this.indexOf(beamGroup) == 0 -> beamGroup.addSecondary(extendsBefore = false, extendsAfter = true)
                            this.indexOf(beamGroup) == this.lastIndex -> beamGroup.addSecondary(extendsBefore = true, extendsAfter = false)
                            else -> beamGroup.addSecondary(extendsBefore = true, extendsAfter = true)
                        }
                    }
                } else {
                    add(PrimaryBeamLeaf(beamGroup, stemDirection, paint, beamYShift, primaryBeam))
                }
            }
        }
    }

    override fun draw(canvas: Canvas, positionDict: PositionDict, paint: Paint) {
        drawers.map { it.draw(canvas, positionDict, paint) }
    }

    override fun add(drawerComponent: ComponentDrawer) {
        drawers.add(drawerComponent)
    }

    override fun remove(drawerComponent: ComponentDrawer) {
        drawers.remove(drawerComponent)
    }

    /**
     * Produces a beam line such that:
     *                      (1) its slope is less than 1/2 of a single space in magnitude
     *                      (2) its slope following the trajectory of the first and last note
     *                      (3) it is always 1/2 an octave away from every note
     *                      (4) it is always a full octave away from at least 1 note
     *
     * @return Line representing the beam line
     */
    private fun createBeamLine(noteViews: List<NoteView>, stemDirection: StemLeaf.StemDirection): Line {
        fun yPos(nV: NoteView) = nV.notePositionDict.noteY + nV.y
        fun xPos(nV: NoteView) = nV.notePositionDict.noteX + nV.x

        val octaveHeight: Float = noteViews.first().notePositionDict.scorePositionDict.octaveHeight
        val semiSpace: Float = noteViews.first().notePositionDict.scorePositionDict.singleSpaceHeight / 2
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
        private const val TAG = "BeamComposite"
    }
}