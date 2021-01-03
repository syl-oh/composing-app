package com.example.composingapp.views

import android.content.Context
import android.graphics.Color
import androidx.core.content.res.ResourcesCompat
import com.example.composingapp.R
import com.example.composingapp.utils.interfaces.Clickable
import com.example.composingapp.utils.music.Music
import com.example.composingapp.views.onclicklisteners.ToggleColourListener

class ClefView(
        context: Context,
) : androidx.appcompat.widget.AppCompatImageButton(context), Clickable{
    private val toggleColourListener = ToggleColourListener
    override var isClicked: Boolean = false;


    init {
        setImageResource(R.drawable.ic_treble_clef)
        setBackgroundColor(Color.TRANSPARENT)
        scaleType = ScaleType.FIT_CENTER
        setOnClickListener(toggleColourListener)
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