package com.example.composingapp.utils.interfaces

import android.view.MotionEvent
import android.view.View

interface TouchHandler {
    fun handleTouch(v: View, event: MotionEvent)
}