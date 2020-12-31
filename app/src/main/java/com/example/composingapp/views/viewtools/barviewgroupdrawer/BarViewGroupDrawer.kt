package com.example.composingapp.views.viewtools.barviewgroupdrawer

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.example.composingapp.utils.interfaces.ComponentDrawer
import com.example.composingapp.utils.interfaces.CompositeDrawer
import com.example.composingapp.views.NoteView
import com.example.composingapp.views.viewtools.ViewConstants
import com.example.composingapp.views.viewtools.barviewgroupdrawer.composites.BeamComposite
import com.example.composingapp.views.viewtools.barviewgroupdrawer.composites.FlagComposite
import com.example.composingapp.views.viewtools.barviewgroupdrawer.leaves.BarlineLeaf
import com.example.composingapp.views.viewtools.barviewgroupdrawer.leaves.SidelineLeaf
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
    }

    init {
        resetDrawers()
    }

    fun setNoteViews(noteViewList: List<NoteView>) {
        this.noteViewList = noteViewList as MutableList<NoteView>
        flaggableGroups = collectFlaggableGroups(noteViewList)
        resetDrawers()
    }

    /**
     *  Produces a 2D list where each element is a group of "flaggable" notes, that is eigth notes
     *  or shorter.
     *
     *  @param noteViewList NoteViewList to find consecutive flaggable notes to make the 2D array
     *  @param accumulator Collects the flaggable groups: used for tail recursion
     *
     *  @return 2D list containing groups of flaggable NoteViews (groups.size >= 1)
     */
    private tailrec fun collectFlaggableGroups(
            noteViewList: List<NoteView>,
            accumulator: List<List<NoteView>> = emptyList()): List<List<NoteView>> {

        // Store the requirement of the first NoteView
        val firstNeedsFlag: Boolean = noteViewList.first().notePositionDict.note.noteLength.needsFlag()
        // Collect all proceeding notes that have the same flag requirement as the first
        val firstGroup: List<NoteView> =
                noteViewList.takeWhile { it.notePositionDict.note.noteLength.needsFlag() == firstNeedsFlag }
        // Once we reach a NoteView with the opposite requirement, store the rest of the list for
        //    further processing
        val restOfList: List<NoteView> =
                noteViewList.takeLast(noteViewList.size - firstGroup.size) // the rest of the list

        return when {
            restOfList.isEmpty() ->
                if (firstNeedsFlag) accumulator + listOf(firstGroup)
                else accumulator

            else -> collectFlaggableGroups(restOfList,
                    if (firstNeedsFlag) accumulator + listOf(firstGroup)
                    else accumulator)
        }
    }

    /**
     * Recreates all the drawers
     */
    fun resetDrawers() {
        drawers.clear()
        add(BarlineLeaf(barPositionDict, paint))
        add(SidelineLeaf(barPositionDict, paint))
        flaggableGroups.filter { it.size == 1 }.forEach { listOfNoteViews ->
            listOfNoteViews.forEach { it.noteViewDrawer.add(FlagComposite(it.notePositionDict, paint)) }
        }

        flaggableGroups.filter { it.size >= 2 }.forEach { add(BeamComposite(it, paint)) }
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