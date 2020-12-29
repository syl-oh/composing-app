package com.example.composingapp.views.viewtools.positiondict

import com.example.composingapp.utils.music.Music.Clef

class BarPositionDict(
        val barWidth: Float,
        barHeight: Float,
        clef: Clef,
) : PositionDict(barHeight, clef) {

}