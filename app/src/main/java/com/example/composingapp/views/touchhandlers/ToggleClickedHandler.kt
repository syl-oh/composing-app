package com.example.composingapp.views.touchhandlers

import android.view.HapticFeedbackConstants
import android.view.MotionEvent
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.example.composingapp.R
import com.example.composingapp.utils.interfaces.ui.Clickable
import com.example.composingapp.utils.interfaces.ui.TouchHandler

object ToggleClickedHandler : TouchHandler {
    private const val notClickedColourID = R.color.black_note
    private const val clickedColourID = R.color.lavender

    fun toggleClicked(v: View?) {
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
    }

    override fun handleTouch(v: View, event: MotionEvent) {
        if (shouldBeClicked(v, event)) {
            v.parent.requestDisallowInterceptTouchEvent(true)
            when (event.action) {
                MotionEvent.ACTION_DOWN -> toggleClicked(v)
            }
        }
    }

    private fun shouldBeClicked(v: View, event: MotionEvent): Boolean {
        return (v is Clickable)
//                && (v.touchAreaRectF.contains(event.x, event.y))
    }
}