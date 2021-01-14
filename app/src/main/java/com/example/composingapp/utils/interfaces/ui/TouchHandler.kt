package com.example.composingapp.utils.interfaces.ui

import android.view.MotionEvent
import android.view.View

interface TouchHandler {
    /**
     * Delegates the App's response to event on v
     *
     * @param v View on which the MotionEvent occured
     * @param event MotionEvent which occured
     */
    fun handleTouch(v: View, event: MotionEvent)
}