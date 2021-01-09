package com.example.composingapp.utils.interfaces.componentdrawer;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.composingapp.utils.interfaces.PositionDict;

public interface ComponentDrawer {
    void draw(Canvas canvas, PositionDict positionDict, Paint paint);
}
