package com.example.composingapp.views.touchhandlers

import android.graphics.Canvas
import android.util.Log
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import android.view.View.DragShadowBuilder
import com.example.composingapp.utils.interfaces.TouchHandler
import com.example.composingapp.utils.music.Music
import com.example.composingapp.utils.music.Note
import com.example.composingapp.utils.music.Tone
import com.example.composingapp.views.NoteView
import com.example.composingapp.views.viewtools.positiondict.NotePositionDict
import kotlin.math.abs

object DragHandler : TouchHandler, View.OnDragListener {
    private const val TAG = "DragHandler"

    override fun handleTouch(v: View, event: MotionEvent) {
        when (event.action) {
            MotionEvent.ACTION_MOVE ->
                if (v is NoteView && v.isClicked && v.notePositionDict.note.pitchClass != Music.PitchClass.REST) {
                    val builder = NoDragShadowBuilder(v); // Shadowless drag
                    v.startDragAndDrop(null, builder, null, 0);
                    builder.view.setOnDragListener(this);
                }
        }
    }

    override fun onDrag(v: View, event: DragEvent?): Boolean {
        if (v is NoteView && v.isClicked && v.notePositionDict.note.pitchClass != Music.PitchClass.REST) {
            when (event?.action) {
                DragEvent.ACTION_DRAG_ENTERED -> Log.d(TAG, "onDrag: started")
                DragEvent.ACTION_DRAG_LOCATION -> dragNote(v, event).also { Log.d(TAG, "onDrag: dragging!") }
            }
        }
        return true
    }

    private fun dragNote(noteView: NoteView, event: DragEvent) {
        val notePositionDict: NotePositionDict = noteView.notePositionDict
        var note = notePositionDict.note
        val semiSpace: Float = notePositionDict.singleSpaceHeight / 2
        val noteY: Float = notePositionDict.noteY
        val dy: Float = noteY - event.y

        // Move up to the note a semispace above if the note has been dragged that far
        if (abs(dy) >= semiSpace) {
//            Log.d(TAG, "dragNote: ${event.x}")
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
            noteView.noteViewDrawer.resetWith(notePositionDict)
            noteView.barViewGroup.invalidate()
        }
    }

    /**
     * Class to enable drag and drop that does not create a shadow
     */
    private class NoDragShadowBuilder(view: View?) : DragShadowBuilder(view) {
        override fun onDrawShadow(canvas: Canvas) {}
    }
}