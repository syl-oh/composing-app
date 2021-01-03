package com.example.composingapp.utils.interfaces

import android.graphics.Color

interface Clickable {
    var isClicked: Boolean

    fun updateColour(colour: Int)
}