package com.example.composingapp.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

public class NoteView extends View {
    private static final String TAG = "NoteView";
    private Paint mNotePaint;
    private int mNoteX, mNoteY;
    private float mNoteRadius;
    private int canvasHeight;
    private int canvasWidth;

    public NoteView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initNotePaint();
    }

    private void initMeasurements() {
        mNoteX = canvasWidth / 2;
        mNoteY = canvasHeight / 2;
        mNoteRadius = 30;
    }

    private void initNotePaint() {
        mNotePaint = new Paint();
        mNotePaint.setColor(Color.parseColor("black"));
        mNotePaint.setAntiAlias(true);
    }

    /**
     * Converts from dp to px
     *
     * @param dp (float) measure of density-independent pixel
     * @return (float) measure of dp in pixels for the operating device
     */
    private float convertDpToPx(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp, getResources().getDisplayMetrics());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        canvasWidth = w;
        canvasHeight = h;
        initMeasurements();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mNoteX, mNoteY, mNoteRadius, mNotePaint);
    }
}
