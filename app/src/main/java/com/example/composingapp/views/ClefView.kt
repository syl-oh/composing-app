package com.example.composingapp.views

import android.content.Context
import android.graphics.Color
import com.example.composingapp.R
import com.example.composingapp.utils.interfaces.Clickable
import com.example.composingapp.utils.interfaces.TouchHandler
import com.example.composingapp.utils.music.Music
import com.example.composingapp.views.touchhandlers.ToggleColourHandler

class ClefView(
        context: Context,
) : androidx.appcompat.widget.AppCompatImageButton(context), Clickable {
    private val handlers = listOf<TouchHandler>(ToggleColourHandler)
    override var isClicked: Boolean = false;

    init {
        setImageResource(R.drawable.ic_treble_clef)
        setBackgroundColor(Color.TRANSPARENT)
        scaleType = ScaleType.FIT_CENTER
        setOnTouchListener { v, event ->
            performClick()
            handlers.map { it.handleTouch(v, event) }
            true   // Return true at the end
        }

    }

    fun setClef(clef: Music.Clef) {
        when (clef) {
            Music.Clef.TREBLE_CLEF -> setImageResource(R.drawable.ic_treble_clef)
            Music.Clef.BASS_CLEF -> setImageResource(R.drawable.ic_bass_clef)
        }
    }

    override fun updateColour(colour: Int) {
        setColorFilter(colour)
    }

    companion object {
        private const val TAG = "ClefView"
    }
}