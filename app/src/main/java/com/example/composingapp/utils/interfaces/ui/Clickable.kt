package com.example.composingapp.utils.interfaces.ui

import android.graphics.Color
import android.graphics.RectF

interface Clickable {
    /**
     * RectF representing an area in which a TouchEvent could be registered as a click on this
     * View
     */
    val touchAreaRectF: RectF

    /**
     * Boolean representing if this View is currently Clicked
     */
    var isClicked: Boolean

    /**
     * Updates the colour of this View to a specified colour.
     *
     * @param colour Int representing the new colour
     */
    fun updateColour(colour: Int)
}