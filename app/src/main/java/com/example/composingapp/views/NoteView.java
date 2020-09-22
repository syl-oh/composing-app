package com.example.composingapp.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

public class NoteView extends View {
    private static final String TAG = "NoteView";
    private Paint mNotePaint;
    private Paint mStemPaint;
    private float mNoteX, mNoteY;
    private float[] mNoteYPositions;
    private float mNoteRadius;
    private int canvasHeight;
    private int canvasWidth;
    private float mStemWidth;
    private float mStemHeight;


    public NoteView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initNoteYPositions();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        canvasWidth = w;
        canvasHeight = h;
        Log.d(TAG, "onSizeChanged: canvasWidth" + canvasWidth);
        initDrawMeasurements();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawNote(canvas);
    }

    private void drawNote(Canvas canvas) {
        float leftX = (canvasWidth / 2) - mNoteRadius;
        float rightX = (canvasWidth / 2) + mNoteRadius;
        float topY = (canvasHeight / 2) + ((mNoteRadius * 3) / 4);
        float bottomY = (canvasHeight / 2) - ((mNoteRadius * 3) / 4);
        float midY = (topY + bottomY) / 2;


        canvas.drawOval(leftX, topY, rightX, bottomY, mNotePaint);
        canvas.drawLine(rightX - mStemWidth / 2,
                midY,
                rightX - mStemWidth / 2,
                midY - mStemHeight,
                mStemPaint);
    }

    private void initDrawMeasurements() {
        mNoteX = canvasWidth / 2;
        mNoteY = canvasHeight / 2;
        mNoteRadius = canvasWidth / 20;
        mStemHeight = (canvasHeight / 3);
        mStemWidth = 3;

        mNoteX = (int) convertDpToPx(mNoteX);
        mNoteY = (int) convertDpToPx(mNoteY);
        mNoteRadius = convertDpToPx(mNoteRadius);
        mStemWidth = convertDpToPx(mStemWidth);

        initNotePaint();
    }

    private void initNoteYPositions() {
        mNoteYPositions = new float[33];
        float highestY = mStemHeight;
        for (int i = 1; i <= 33; i++) {
            mNoteYPositions[i - 1] = highestY + (i / 33) * canvasHeight;
        }
    }

    private void initNotePaint() {
        mNotePaint = new Paint();
        mNotePaint.setColor(Color.parseColor("black"));
        mNotePaint.setAntiAlias(true);

        mStemPaint = new Paint();
        mStemPaint.setColor(Color.parseColor("black"));
        mStemPaint.setStrokeWidth(mStemWidth);
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
}
