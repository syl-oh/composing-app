package com.example.composingapp.views.viewtools.positiondict

import com.example.composingapp.utils.interfaces.ui.PositionDict

data class BarPositionDict(
        val barWidth: Float,
        val barHeight: Float,
        val scorePositionDict: ScorePositionDict
): PositionDict {}