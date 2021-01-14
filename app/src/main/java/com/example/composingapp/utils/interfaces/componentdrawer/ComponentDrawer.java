package com.example.composingapp.utils.interfaces.componentdrawer;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.composingapp.utils.interfaces.ui.PositionDict;

public interface ComponentDrawer {
    /**
     * Draws onto the Canvas
     *
     * @param canvas Canvas to draw on
     * @param positionDict PositionDict containing coordinate information about the visual score
     * @param paint Paint to draw with
     */
    void draw(Canvas canvas, PositionDict positionDict, Paint paint);
}
