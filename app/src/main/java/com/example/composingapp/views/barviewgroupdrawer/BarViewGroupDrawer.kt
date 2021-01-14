package com.example.composingapp.views.barviewgroupdrawer

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.example.composingapp.utils.interfaces.componentdrawer.ComponentDrawer
import com.example.composingapp.utils.music.Music
import com.example.composingapp.views.NoteView
import com.example.composingapp.views.barviewgroupdrawer.composites.BeamComposite
import com.example.composingapp.views.barviewgroupdrawer.composites.FlagComposite
import com.example.composingapp.views.barviewgroupdrawer.leaves.BarlineLeaf
import com.example.composingapp.views.barviewgroupdrawer.leaves.SidelineLeaf
import com.example.composingapp.views.barviewgroupdrawer.leaves.beams.BeamHelper.onlyGroupsWithNoteLengthCondition
import com.example.composingapp.views.viewtools.ViewConstants
import com.example.composingapp.views.viewtools.positiondict.BarPositionDict

class BarViewGroupDrawer(
        private val barPositionDict: BarPositionDict,
//        private val noteViewList: MutableList<NoteView> = mutableListOf(),
) {
    private val noteViewList: MutableList<NoteView> = mutableListOf()
    private val flaggableGroups = mutableListOf<List<NoteView>>()
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

    /**
     * Sets the noteViewList of this BarViewGroupDrawer to a provided list of NoteViews
     *
     * @param noteViewList List of NoteViews to assign to this BarViewGroupDrawer
     * Side effects: Clears flaggableGroups and this.noteViewList, the adds back required components
     */
    fun setNoteViews(noteViewList: List<NoteView>) {
        this.noteViewList.clear()
        flaggableGroups.clear()
        noteViewList.map { this.noteViewList.add(it) }
        noteViewList.filterNot { it.notePositionDict.note.pitchClass == Music.PitchClass.REST }
                .onlyGroupsWithNoteLengthCondition { it.needsFlag() }.map { flaggableGroups.add(it) }
        resetDrawers()
    }

    /**
     * Recreates all the drawers of this BarViewGroupDrawer
     *
     * Side Effects: Clears drawers, then fills it back up with required ComponentDrawers
     */
    private fun resetDrawers() {
        drawers.clear()
        noteViewList.map { it.noteViewDrawer.resetDrawersWith(it.notePositionDict) }
        add(BarlineLeaf)
        add(SidelineLeaf)

        for (flaggableGroup in flaggableGroups) {
            if (flaggableGroup.size == 1) {
                flaggableGroup.map { it.noteViewDrawer.add(FlagComposite(it.notePositionDict, paint)) }
            } else {
                add(BeamComposite(flaggableGroup, barPositionDict, paint))
            }
        }
    }

    fun draw(canvas: Canvas?) {
        drawers.map { it.draw(canvas, barPositionDict, paint) }
    }

    fun add(drawerComponent: ComponentDrawer) {
        drawers.add(drawerComponent)
    }

    companion object {
        private const val TAG = "BarViewGroupDrawer"
    }

}