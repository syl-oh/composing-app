package com.example.composingapp.views.touchhandlers

import android.view.MotionEvent
import android.view.MotionEvent.ACTION_MOVE
import android.view.MotionEvent.ACTION_UP
import android.view.View
import com.example.composingapp.utils.interfaces.ui.TouchHandler
import com.example.composingapp.utils.music.Music
import com.example.composingapp.utils.music.NoteTable
import com.example.composingapp.utils.music.Tone
import com.example.composingapp.views.BarViewGroup
import com.example.composingapp.views.NoteView
import com.example.composingapp.views.viewtools.positiondict.NotePositionDict
import kotlin.math.abs

object NoteViewMoveHandler : TouchHandler {
    override fun handleTouch(v: View, event: MotionEvent) {
        when (event.action) {
            ACTION_MOVE -> moveNoteView(v, event)
            ACTION_UP -> updateDataInViewModel(v)
        }
    }

    /**
     * Updates the data in the ScoreViewModel through the parent of this NoteView
     *
     * Side effects: Notifies observers of the ScoreViewModel's LiveData
     */
    private fun updateDataInViewModel(v: View) {
        if (v is NoteView && v.parent is BarViewGroup) {
            (v.parent as BarViewGroup).updateScoreViewModel(v)
        }
    }

    /**
     * Moves a NoteView up or down the screen
     *
     * Side effects: If v is moved, it invalidates (redraws) v as a NoteView, and also invalidates
     *               the parent BarViewGroup if v needs a flag (i.e. has NoteLength EIGHTH_NOTE or
     *               shorter)
     */
    private fun moveNoteView(v: View, event: MotionEvent) {
        if (v is NoteView && isMovableNoteView(v)) {
            val notePositionDict: NotePositionDict = v.notePositionDict
            val note = notePositionDict.note
            val semiSpace: Float = notePositionDict.scorePositionDict.singleSpaceHeight / 2
            val noteY: Float = notePositionDict.noteY
            val dy: Float = noteY - event.y

            // Move up to the note a semispace above if the note has been dragged that far
            if (abs(dy) >= semiSpace) {
                // Find the new tone
                val newToneY = if (dy > 0) noteY - semiSpace else noteY + semiSpace
                val nextTone: Tone? = notePositionDict.scorePositionDict.yToToneMap[newToneY]

                // Update the note and the NoteView, then redraw
                notePositionDict.note = nextTone?.let {
                    NoteTable.get(
                            nextTone.pitchClass,
                            nextTone.octave,
                            note.noteLength)
                }

                v.noteViewDrawer.resetDrawersWith(notePositionDict)
                if (note.noteLength.needsFlag()) {
                    v.barViewGroup.invalidate()
                } else {
                    v.invalidate()
                }
            }
        }
    }

    /**
     * Determines if v is a movable NoteView
     *
     * @param v View to examine
     * @return true only if v is a clicked NoteView that is not a rest
     */
    private fun isMovableNoteView(v: View): Boolean {
        return v is NoteView && v.isClicked && v.notePositionDict.note.pitchClass != Music.PitchClass.REST
    }
}