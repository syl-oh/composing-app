package com.example.composingapp.views.onclicklisteners

import android.content.Context
import android.os.Vibrator
import android.view.HapticFeedbackConstants
import android.view.View
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.example.composingapp.R
import com.example.composingapp.utils.interfaces.Clickable

object ToggleColourListener : View.OnClickListener {
    private const val notClickedColourID = R.color.black_note
    private const val clickedColourID = R.color.lavender

    override fun onClick(v: View?) {
        if (v is Clickable) {
            v.apply {
                performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                if (isClicked) {
                    updateColour(ResourcesCompat.getColor(resources, notClickedColourID, null))
                } else {
                    updateColour(ResourcesCompat.getColor(resources, clickedColourID, null))
                }
                isClicked = !isClicked
            }
        }
    }
}