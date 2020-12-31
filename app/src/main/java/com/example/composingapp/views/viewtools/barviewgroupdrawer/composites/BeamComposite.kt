package com.example.composingapp.views.viewtools.barviewgroupdrawer.composites

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import com.example.composingapp.utils.interfaces.ComponentDrawer
import com.example.composingapp.utils.interfaces.CompositeDrawer
import com.example.composingapp.views.NoteView
import com.example.composingapp.views.viewtools.barviewgroupdrawer.leaves.beams.BeamHelper.findStemDirection
import com.example.composingapp.views.viewtools.barviewgroupdrawer.leaves.beams.BeamHelper.heightToBeam
import com.example.composingapp.views.viewtools.barviewgroupdrawer.leaves.beams.PrimaryBeamLeaf
import com.example.composingapp.views.viewtools.noteviewdrawer.leaves.StemLeaf
import com.example.composingapp.views.viewtools.positiondict.NotePositionDict
import com.example.composingapp.views.viewtools.positiondict.PositionDict

class BeamComposite(
        val beamGroup: List<NoteView>,
        val paint: Paint,
) : CompositeDrawer {
    private val drawers = mutableListOf<ComponentDrawer>()
    private val stemDirection = findStemDirection(beamGroup.map { it.notePositionDict })
    private val primaryBeam: PrimaryBeamLeaf
    private val notePositionDict: NotePositionDict = beamGroup.first().notePositionDict

    init {
        primaryBeam = PrimaryBeamLeaf(beamGroup, stemDirection, paint)
        add(primaryBeam)
        add(PrimaryBeamLeaf(beamGroup, stemDirection, paint, notePositionDict.singleSpaceHeight/4))
        beamGroup.forEach {
            val stemHeight = heightToBeam(it, primaryBeam.beamLine, paint, stemDirection)
            it.noteViewDrawer.add(StemLeaf(it.notePositionDict, paint, stemDirection, stemHeight))
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