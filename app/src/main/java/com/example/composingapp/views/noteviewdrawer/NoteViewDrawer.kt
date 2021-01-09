package com.example.composingapp.views.noteviewdrawer

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.example.composingapp.utils.interfaces.componentdrawer.ComponentDrawer
import com.example.composingapp.utils.music.Music
import com.example.composingapp.views.accidentals.FlatLeaf
import com.example.composingapp.views.accidentals.SharpLeaf
import com.example.composingapp.views.barviewgroupdrawer.leaves.StemLeaf
import com.example.composingapp.views.noteviewdrawer.composites.LedgerLineLeaf
import com.example.composingapp.views.noteviewdrawer.composites.ShortRestComposite
import com.example.composingapp.views.noteviewdrawer.leaves.FilledBaseLeaf
import com.example.composingapp.views.noteviewdrawer.leaves.HollowBaseLeaf
import com.example.composingapp.views.noteviewdrawer.leaves.LongRestLeaf
import com.example.composingapp.views.noteviewdrawer.leaves.QuarterRestLeaf
import com.example.composingapp.views.viewtools.ViewConstants.STEM_WIDTH
import com.example.composingapp.views.viewtools.positiondict.NotePositionDict

class NoteViewDrawer(val notePositionDict: NotePositionDict) {
    var drawers = mutableListOf<ComponentDrawer>()
    val paint = Paint().apply {
        color = Color.BLACK
        isAntiAlias = true
        isDither = true
        strokeWidth = STEM_WIDTH
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
    }

    init {
        resetDrawersWith(notePositionDict)
//        Log.d(TAG, "Drawer for ${notePositionDict.note.pitchClass}, ${notePositionDict.note.octave}, " +
//                "${notePositionDict.note.noteLength} ")
    }

    /**
     *  Resets all drawers with the information provided from a given NotePositionDict
     *
     *  @param notePositionDict NotePositionDict containing coordinate information for the note
     */
    fun resetDrawersWith(notePositionDict: NotePositionDict) {
        drawers.clear()
        if (notePositionDict.note.pitchClass == Music.PitchClass.REST) {
            when (notePositionDict.note.noteLength) {
                Music.NoteLength.QUARTER_NOTE -> add(QuarterRestLeaf(notePositionDict, paint))
                Music.NoteLength.HALF_NOTE, Music.NoteLength.WHOLE_NOTE ->
                    add(LongRestLeaf(notePositionDict, paint))
                else -> add(ShortRestComposite(notePositionDict, paint))
            }
        } else {
            add(FilledBaseLeaf)
            with(notePositionDict.note.noteLength) {
                if (this == Music.NoteLength.WHOLE_NOTE || this == Music.NoteLength.HALF_NOTE) {
                    add(HollowBaseLeaf)
                }
                if (this == Music.NoteLength.QUARTER_NOTE || this == Music.NoteLength.HALF_NOTE) {
                    add(StemLeaf)
                }
            }

            // Add any accidentals
            with(notePositionDict.note.pitchClass.accidental) {
                if (this == Music.PitchClass.Accidental.SHARP) add(SharpLeaf(notePositionDict, paint))
                else if (this == Music.PitchClass.Accidental.FLAT) add(FlatLeaf(notePositionDict, paint))
            }
            add(LedgerLineLeaf)
        }
    }

    fun draw(canvas: Canvas?) {
        drawers.map { it.draw(canvas, notePositionDict, paint) }
    }

    fun add(drawerComponent: ComponentDrawer) {
        drawers.add(drawerComponent)
    }

    companion object {
        private const val TAG = "NoteViewDrawer"
    }
}