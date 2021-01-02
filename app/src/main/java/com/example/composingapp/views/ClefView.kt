package com.example.composingapp.views

import android.content.Context
import android.graphics.Color
import androidx.core.view.updateLayoutParams
import com.example.composingapp.R
import com.example.composingapp.views.viewtools.positiondict.PositionDict

class ClefView(
        context: Context,
) : androidx.appcompat.widget.AppCompatImageButton(context) {
    init {
        setImageResource(R.drawable.ic_treble_clef)
        setBackgroundColor(Color.TRANSPARENT)
        scaleType = ScaleType.FIT_CENTER
    }

    companion object {
        private const val TAG = "ClefView"
    }
}