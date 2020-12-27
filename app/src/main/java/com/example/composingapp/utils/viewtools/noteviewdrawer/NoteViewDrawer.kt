package com.example.composingapp.utils.viewtools.noteviewdrawer

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.example.composingapp.utils.interfaces.ComponentDrawer
import com.example.composingapp.utils.interfaces.CompositeDrawer
import com.example.composingapp.utils.music.Music
import com.example.composingapp.utils.viewtools.NotePositionDict
import com.example.composingapp.utils.viewtools.ViewConstants.STEM_WIDTH
import com.example.composingapp.utils.viewtools.noteviewdrawer.composites.NoteComposite
import com.example.composingapp.utils.viewtools.noteviewdrawer.composites.RestComposite

class NoteViewDrawer(private val notePositionDict: NotePositionDict) : CompositeDrawer {
    private val drawers = mutableListOf<ComponentDrawer>()
    private val paint = Paint().apply {
        color = Color.BLACK
        isAntiAlias = true
        isDither = true
        strokeWidth = STEM_WIDTH.toFloat()
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
    }

    init {
        if (notePositionDict.note.pitchClass == Music.PitchClass.REST) {
            add(RestComposite(notePositionDict, paint))
        } else {
            add(NoteComposite(notePositionDict, paint))
        }
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
        private const val TAG = "NoteViewDrawer"
    }
}