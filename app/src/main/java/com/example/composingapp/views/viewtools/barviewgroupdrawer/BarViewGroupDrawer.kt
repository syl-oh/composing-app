package com.example.composingapp.views.viewtools.barviewgroupdrawer

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.example.composingapp.utils.interfaces.componentdrawer.ComponentDrawer
import com.example.composingapp.utils.interfaces.componentdrawer.CompositeDrawer
import com.example.composingapp.utils.music.Music
import com.example.composingapp.views.NoteView
import com.example.composingapp.views.viewtools.ViewConstants
import com.example.composingapp.views.viewtools.barviewgroupdrawer.composites.BeamComposite
import com.example.composingapp.views.viewtools.barviewgroupdrawer.composites.FlagComposite
import com.example.composingapp.views.viewtools.barviewgroupdrawer.leaves.BarlineLeaf
import com.example.composingapp.views.viewtools.barviewgroupdrawer.leaves.SidelineLeaf
import com.example.composingapp.views.viewtools.barviewgroupdrawer.leaves.beams.BeamHelper.onlyGroupsWithNoteLengthCondition
import com.example.composingapp.views.viewtools.positiondict.BarPositionDict

class BarViewGroupDrawer(
        private val barPositionDict: BarPositionDict,
) : CompositeDrawer {
    private var noteViewList = listOf<NoteView>()
    private var flaggableGroups = listOf<List<NoteView>>()

    private val drawers = mutableListOf<ComponentDrawer>()
    private val paint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.BLACK
        strokeWidth = ViewConstants.BARLINE_SIZE.toFloat()
        isAntiAlias = true
        isDither = true
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
    }

    init {
        resetDrawers()
    }

    fun setNoteViews(noteViewList: List<NoteView>) {
        this.noteViewList = noteViewList as MutableList<NoteView>
        flaggableGroups =
                noteViewList.filterNot { it.notePositionDict.note.pitchClass == Music.PitchClass.REST }
                        .onlyGroupsWithNoteLengthCondition { it.needsFlag() }

        resetDrawers()
    }

    /**
     * Recreates all the drawers
     */
    fun resetDrawers() {
        drawers.clear()
        add(BarlineLeaf(barPositionDict, paint))
        add(SidelineLeaf(barPositionDict, paint))
        flaggableGroups.filter { it.size == 1 }.map { listOfNoteViews ->
            listOfNoteViews.map {
                it.noteViewDrawer.add(FlagComposite(it.notePositionDict, paint))
            }
        }
        flaggableGroups.filter { it.size >= 2 }.map { add(BeamComposite(it, paint)) }
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
        private const val TAG = "BarViewGroupDrawer"
    }

}