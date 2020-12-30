package com.example.composingapp.utils

data class Line(
        val x: Float,
        val y: Float,
        val slope: Float,
) {
    var yIntercept = y - slope * x

    constructor(x1: Float, x2: Float, y1: Float, y2: Float) : this(x1, y1, ((y2 - y1) / (x2 - x1)))


    fun yAt(x: Float): Float {
        return slope * x + yIntercept
    }

    fun xAt(y: Float): Float {
        return (y - yIntercept) / slope
    }
}