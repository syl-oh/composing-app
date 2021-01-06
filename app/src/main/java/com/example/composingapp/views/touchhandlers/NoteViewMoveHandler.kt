package com.example.composingapp.views.touchhandlers

import android.util.Log
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_MOVE
import android.view.MotionEvent.ACTION_UP
import android.view.View
import com.example.composingapp.utils.interfaces.ui.TouchHandler
import com.example.composingapp.utils.music.Music
import com.example.composingapp.utils.music.Note
import com.example.composingapp.utils.music.Tone
import com.example.composingapp.views.NoteView
import com.example.composingapp.views.viewtools.positiondict.NotePositionDict
import kotlin.math.abs

object NoteViewMoveHandler : TouchHandler {
    private const val TAG = "NoteViewMoveHandler"

    override fun handleTouch(v: View, event: MotionEvent) {
        when (event.action) {
            ACTION_MOVE -> moveNoteView(v, event)
            ACTION_UP -> Log.d(TAG, "handleTouch: ${(v as NoteView).notePositionDict.note.pitchClass}")
        }
    }

    private fun moveNoteView(v: View, event: MotionEvent) {
        if (v is NoteView && isMovableNoteView(v)) {
            v as NoteView  // Cast v as a NoteView (which is true since it passed isMovableNoteView)
            val notePositionDict: NotePositionDict = v.notePositionDict
            var note = notePositionDict.note
            val semiSpace: Float = notePositionDict.singleSpaceHeight / 2
            val noteY: Float = notePositionDict.noteY
            val dy: Float = noteY - event.y

            // Move up to the note a semispace above if the note has been dragged that far
            if (abs(dy) >= semiSpace) {
                // Find the new tone
                val newToneY = if (dy > 0) noteY - semiSpace else noteY + semiSpace
                val nextTone: Tone? = notePositionDict.yToToneMap[newToneY]

                // Update the note and the NoteView, then redraw
                note = nextTone?.let {
                    Note(
                            nextTone.pitchClass,
                            nextTone.octave,
                            note.noteLength)
                }
                notePositionDict.note = note
                v.noteViewDrawer.resetWith(notePositionDict)
                v.barViewGroup.invalidate()
            }
        }
    }

    private fun isMovableNoteView(v: View): Boolean {
        return v is NoteView && v.isClicked && v.notePositionDict.note.pitchClass != Music.PitchClass.REST
    }
}