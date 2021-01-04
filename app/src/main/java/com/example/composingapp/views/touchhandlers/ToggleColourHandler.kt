package com.example.composingapp.views.touchhandlers

import android.util.Log
import android.view.HapticFeedbackConstants
import android.view.MotionEvent
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.example.composingapp.R
import com.example.composingapp.utils.interfaces.Clickable
import com.example.composingapp.utils.interfaces.TouchHandler

object ToggleColourHandler : TouchHandler {
    private const val notClickedColourID = R.color.black_note
    private const val clickedColourID = R.color.lavender
    private const val TAG = "ToggleColourListener"

    fun toggleColour(v: View?): Boolean {
        if (v is Clickable) {
            v.apply {
                performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                fun colourTo(ID: Int) = updateColour(ResourcesCompat.getColor(resources, ID, null))
                if (isClicked) {
                    colourTo(notClickedColourID)
                } else {
                    colourTo(clickedColourID)
                }
                isClicked = !isClicked
            }
        }
        return false
    }

    override fun handleTouch(v: View, event: MotionEvent) {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> toggleColour(v)
        }
    }
}