package com.example.composingapp.utils

/**
 * @param x Float representing the x-coordinate of a given point on the Line
 * @param y Float representing the y-coordinate of x
 * @param slope Float representing the slope of the line
 */
data class Line(
        val x: Float,
        val y: Float,
        val slope: Float,
) {
    val yIntercept = y - slope * x

    constructor(x1: Float, x2: Float, y1: Float, y2: Float) : this(x1, y1, ((y2 - y1) / (x2 - x1)))


    /**
     * Produces the y coordinate at x
     * @param x Float - given x-coordinate
     * @return Float - matching y-coordinate to x
     */
    fun yAt(x: Float): Float {
        return slope * x + yIntercept
    }

    /**
     * Produces the a coordinate at y
     * @param y Float - given y-coordinate
     * @return Float - matching x-coordinate to y
     */
    fun xAt(y: Float): Float {
        return (y - yIntercept) / slope
    }

    /**
     * Produces a new Line shifted vertically by yTranslation
     *
     * @param yTranslation Float - vertical shift to apply to this Line
     * @return Line - a vertically shifted copy of this instance
     */
    fun moveVertically(yTranslation: Float): Line {
        return Line(x, y + yTranslation, slope)
    }
}