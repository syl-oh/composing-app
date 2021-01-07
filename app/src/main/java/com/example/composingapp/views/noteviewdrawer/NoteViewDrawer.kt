package com.example.composingapp.views.noteviewdrawer

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import com.example.composingapp.utils.interfaces.componentdrawer.ComponentDrawer
import com.example.composingapp.utils.interfaces.componentdrawer.CompositeDrawer
import com.example.composingapp.utils.music.Music
import com.example.composingapp.views.accidentals.FlatLeaf
import com.example.composingapp.views.accidentals.SharpLeaf
import com.example.composingapp.views.barviewgroupdrawer.leaves.StemLeaf
import com.example.composingapp.views.noteviewdrawer.composites.LedgerLineComposite
import com.example.composingapp.views.viewtools.ViewConstants.STEM_WIDTH
import com.example.composingapp.views.noteviewdrawer.composites.ShortRestComposite
import com.example.composingapp.views.noteviewdrawer.leaves.*
import com.example.composingapp.views.viewtools.positiondict.NotePositionDict

class NoteViewDrawer(private val notePositionDict: NotePositionDict) : CompositeDrawer {
    var drawers = mutableListOf<ComponentDrawer>()
    val paint = Paint().apply {
        color = Color.BLACK
        isAntiAlias = true
        isDither = true
        strokeWidth = STEM_WIDTH.toFloat()
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
    }

    init {
        resetWith(notePositionDict)
//        Log.d(TAG, "Drawer for ${notePositionDict.note.pitchClass}, ${notePositionDict.note.octave}, " +
//                "${notePositionDict.note.noteLength} ")
    }

    /**
     *  Resets all drawers with the information provided from a given NotePositionDict
     *
     *  @param notePositionDict NotePositionDict containing coordinate information for the note
     */
    fun resetWith(notePositionDict: NotePositionDict) {
        drawers.clear()

        if (notePositionDict.note.pitchClass == Music.PitchClass.REST) {
            when (notePositionDict.note.noteLength) {
                Music.NoteLength.QUARTER_NOTE -> add(QuarterRestLeaf(notePositionDict, paint))
                Music.NoteLength.HALF_NOTE, Music.NoteLength.WHOLE_NOTE ->
                    add(LongRestLeaf(notePositionDict, paint))
                else -> add(ShortRestComposite(notePositionDict, paint))
            }
        } else {
            add(FilledBaseLeaf(notePositionDict, paint))

            with(notePositionDict.note.noteLength) {
                if (this == Music.NoteLength.WHOLE_NOTE || this == Music.NoteLength.HALF_NOTE) {
                    add(HollowBaseLeaf(notePositionDict, paint))
                }
                if (this == Music.NoteLength.QUARTER_NOTE || this == Music.NoteLength.HALF_NOTE) {
                    add(StemLeaf(notePositionDict, paint))
                }
            }

            // Add any accidentals
            with(notePositionDict.note.pitchClass.accidental) {
                if (this == Music.PitchClass.Accidental.SHARP) add(SharpLeaf(notePositionDict, paint))
                else if (this == Music.PitchClass.Accidental.FLAT) add(FlatLeaf(notePositionDict, paint))
            }

            add(LedgerLineComposite(notePositionDict, paint))
        }
    }

    override fun draw(canvas: Canvas?) {
        drawers.map {it.draw(canvas)}
    }

    override fun add(drawerComponent: ComponentDrawer) {
        if (drawerComponent is StemLeaf) {
            drawers.removeAll { it is StemLeaf }
        }
        drawers.add(drawerComponent)
    }

    override fun remove(drawerComponent: ComponentDrawer) {
        drawers.remove(drawerComponent)
    }

    companion object {
        private const val TAG = "NoteViewDrawer"
    }
}