package com.example.composingapp.views.barviewgroupdrawer.composites

import android.graphics.Canvas
import android.graphics.Paint
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

class BeamComposite(
        beamGroup: List<NoteView>,
        barPositionDict: BarPositionDict,
        val paint: Paint,
) : CompositeDrawer {
    private val drawers = mutableListOf<ComponentDrawer>()
    private val beamGroupDicts = beamGroup.map { it.notePositionDict }
    private val stemDirection = findStemDirection(beamGroupDicts)
    private val primaryBeam: PrimaryBeamLeaf
    private val beamYShift = barPositionDict.positionDict.singleSpaceHeight / 4

    init {
        primaryBeam = PrimaryBeamLeaf(beamGroup, stemDirection, paint)
        add(primaryBeam)

        beamGroup.map {
            it.noteViewDrawer.add(StemLeaf(it.notePositionDict, it.noteViewDrawer.paint, stemDirection,
                    BeamHelper.heightToBeam(it, primaryBeam.beamLine, paint, stemDirection)))
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
                                extendsAfter, primaryBeam.beamLine))

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
                    add(PrimaryBeamLeaf(beamGroup, stemDirection, paint, beamYShift, primaryBeam.beamLine))
                }
            }
        }
    }

    override fun draw(canvas: Canvas?) {
        drawers.map { it.draw(canvas) }
    }

    override fun add(drawerComponent: ComponentDrawer) {
        drawers.add(drawerComponent)
    }

    override fun remove(drawerComponent: ComponentDrawer) {
        drawers.remove(drawerComponent)
    }



    companion object {
        private const val TAG = "BeamComposite"
    }
}