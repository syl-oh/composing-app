package com.example.composingapp.views.utils;

import android.graphics.Canvas;
import android.view.View;

public class NoDragShadowBuilder extends View.DragShadowBuilder {
    public NoDragShadowBuilder(View view) {
        super(view);
    }
    @Override
    public void onDrawShadow(Canvas canvas) {
    }
}
