package com.example.composingapp.views.viewtools.positiondict

import com.example.composingapp.utils.interfaces.PositionDict

data class BarPositionDict(
        val barWidth: Float,
        val barHeight: Float,
        val scorePositionDict: ScorePositionDict
): PositionDict {}