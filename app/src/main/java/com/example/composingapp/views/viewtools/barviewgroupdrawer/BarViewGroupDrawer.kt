package com.example.composingapp.views.viewtools.barviewgroupdrawer

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import com.example.composingapp.utils.interfaces.ComponentDrawer
import com.example.composingapp.utils.interfaces.CompositeDrawer
import com.example.composingapp.views.NoteView
import com.example.composingapp.views.viewtools.ViewConstants
import com.example.composingapp.views.viewtools.barviewgroupdrawer.leaves.BarlineLeaf
import com.example.composingapp.views.viewtools.barviewgroupdrawer.leaves.SidelineLeaf
import com.example.composingapp.views.viewtools.positiondict.BarPositionDict
import com.example.composingapp.views.viewtools.positiondict.NotePositionDict
import java.util.ArrayList

class BarViewGroupDrawer(
        private val barPositionDict: BarPositionDict,
) : CompositeDrawer {
    private var noteViewPositionDicts = mutableListOf<NotePositionDict>()
    private val drawers = mutableListOf<ComponentDrawer>()
    private val paint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.BLACK
        strokeWidth = ViewConstants.BARLINE_SIZE.toFloat()
    }

    init {
        add(BarlineLeaf(barPositionDict, paint))
        add(SidelineLeaf(barPositionDict, paint))
    }

    fun setPositionDictsFromNoteViews(noteViewList: ArrayList<NoteView>) {
        noteViewPositionDicts = noteViewList.map { it.notePositionDict } as MutableList<NotePositionDict>
    }

    override fun draw(canvas: Canvas?) {
        drawers.map {it.draw(canvas)}
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