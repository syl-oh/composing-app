package com.example.composingapp.views

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.widget.AppCompatImageButton
import com.example.composingapp.R

class ClefView(
        context: Context,
        val barViewGroup: BarViewGroup
) : androidx.appcompat.widget.AppCompatImageButton(context) {
    init {
        setImageResource(R.drawable.ic_treble_clef)
        setBackgroundColor(Color.TRANSPARENT)
        scaleType = ScaleType.FIT_CENTER
    }

}