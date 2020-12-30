package com.example.composingapp.views.viewtools.barviewgroupdrawer.composites

import android.graphics.Canvas
import android.graphics.Paint
import com.example.composingapp.utils.interfaces.ComponentDrawer
import com.example.composingapp.utils.interfaces.CompositeDrawer
import com.example.composingapp.views.NoteView
import com.example.composingapp.views.viewtools.barviewgroupdrawer.leaves.beams.BeamHelper.findStemDirection
import com.example.composingapp.views.viewtools.barviewgroupdrawer.leaves.beams.BeamHelper.getStemX
import com.example.composingapp.views.viewtools.barviewgroupdrawer.leaves.beams.BeamHelper.heightToBeam
import com.example.composingapp.views.viewtools.barviewgroupdrawer.leaves.beams.PrimaryBeamLeaf
import com.example.composingapp.views.viewtools.noteviewdrawer.leaves.StemLeaf
import kotlin.math.abs

class BeamComposite(
        val noteViews: List<NoteView>,
        val paint: Paint,
) : CompositeDrawer {
    private val drawers = mutableListOf<ComponentDrawer>()
    private val stemDirection = findStemDirection(noteViews.map { it.notePositionDict })
    private val primaryBeam: PrimaryBeamLeaf

    init {
        primaryBeam = PrimaryBeamLeaf(noteViews, stemDirection, paint)
        add(primaryBeam)
        add(PrimaryBeamLeaf(noteViews, stemDirection, paint, noteViews.first().notePositionDict.singleSpaceHeight/4))
        noteViews.forEach {
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