package com.example.composingapp.views

import android.content.Context
import android.graphics.Color
import android.graphics.RectF
import com.example.composingapp.R
import com.example.composingapp.utils.interfaces.ui.Clickable
import com.example.composingapp.utils.music.Music
import com.example.composingapp.views.touchhandlers.ToggleClickedHandler

class ClefView(
        context: Context,
) : androidx.appcompat.widget.AppCompatImageButton(context), Clickable {
    override lateinit var touchAreaRectF: RectF

    override var isClicked: Boolean = false;
    init {
        setImageResource(R.drawable.ic_treble_clef)
        setBackgroundColor(Color.TRANSPARENT)
        scaleType = ScaleType.FIT_CENTER
        setOnTouchListener { v, event ->
            performClick()
            ToggleClickedHandler.handleTouch(v, event)
            true   // Return true at the end
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        touchAreaRectF = RectF(0f, 0f, w.toFloat(), h.toFloat())
    }

    /**
     * Sets the clef of this ClefView
     *
     * @param clef Clef to change to
     *
     */
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