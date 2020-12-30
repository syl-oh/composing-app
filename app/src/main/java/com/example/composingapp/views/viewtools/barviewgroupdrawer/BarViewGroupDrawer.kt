package com.example.composingapp.views.viewtools.barviewgroupdrawer

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import com.example.composingapp.utils.interfaces.ComponentDrawer
import com.example.composingapp.utils.interfaces.CompositeDrawer
import com.example.composingapp.views.NoteView
import com.example.composingapp.views.viewtools.ViewConstants
import com.example.composingapp.views.viewtools.barviewgroupdrawer.composites.BeamComposite
import com.example.composingapp.views.viewtools.barviewgroupdrawer.leaves.BarlineLeaf
import com.example.composingapp.views.viewtools.barviewgroupdrawer.leaves.SidelineLeaf
import com.example.composingapp.views.viewtools.barviewgroupdrawer.leaves.beams.PrimaryBeamLeaf
import com.example.composingapp.views.viewtools.positiondict.BarPositionDict

class BarViewGroupDrawer(
        private val barPositionDict: BarPositionDict,
) : CompositeDrawer {
    private var noteViewList = mutableListOf<NoteView>()
    private var beamGroups = mutableListOf<List<NoteView>>()

    private val drawers = mutableListOf<ComponentDrawer>()
    private val paint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.BLACK
        strokeWidth = ViewConstants.BARLINE_SIZE.toFloat()
    }

    init {
        resetDrawers()
    }

    fun setNoteViews(noteViewList: List<NoteView>) {
        this.noteViewList = noteViewList as MutableList<NoteView>
        collectBeamGroups()
    }

    private fun collectBeamGroups() {
        beamGroups.add(noteViewList.subList(0, 4))
        resetDrawers()
    }

    fun resetDrawers() {
        drawers.clear()
        add(BarlineLeaf(barPositionDict, paint))
        add(SidelineLeaf(barPositionDict, paint))
        beamGroups.forEach { add(BeamComposite(it, paint)) }
    }

    override fun draw(canvas: Canvas?) {
        drawers.forEach { it.draw(canvas) }
    }

    override fun add(drawerComponent: ComponentDrawer) {
        drawers.add(drawerComponent)
    }

    override fun remove(drawerComponent: ComponentDrawer) {
        drawers.remove(drawerComponent)
    }

    companion object {
        private const val TAG = "BarViewGroupDrawer"
    }

}