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
    private Paint mNotePaint, mStemPaint;
    private float mNoteX, mNoteY, mNoteRadius, mStemWidth, mStemHeight;
    private float[] mNoteYPositions;
    private int canvasHeight, canvasWidth;


    public NoteView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        canvasWidth = w;
        canvasHeight = h;
        initDrawMeasurements();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawNote(canvas);
    }

    /**
     * @param canvas
     */
    private void drawNote(Canvas canvas) {
        float leftX = (mNoteX) - (ViewConstants.NOTE_W_TO_H_RATIO * mNoteRadius);
        float rightX = (mNoteX) + (ViewConstants.NOTE_W_TO_H_RATIO * mNoteRadius);
        float topY = (mNoteY) + (mNoteRadius);
        float bottomY = (mNoteY) - (mNoteRadius);
        float midY = (topY + bottomY) / 2;

        canvas.drawOval(leftX, topY, rightX, bottomY, mNotePaint);
        canvas.drawLine(rightX - mStemWidth / 2,
                midY,
                rightX - mStemWidth / 2,
                midY - mStemHeight,
                mStemPaint);
    }

    /**
     * Initializes measurements for all drawings to be drawn in onDraw()
     */
    private void initDrawMeasurements() {
        mStemHeight = (canvasHeight / 3);
        mStemWidth = convertDpToPx(ViewConstants.STEM_WIDTH);
        initNoteYPositions();
        mNoteX = canvasWidth / 2;
        mNoteY = mNoteYPositions[10];
        mNoteRadius = canvasHeight / 10;
        initNotePaint();

    }

    /**
     * Initializes the mNoteYPositions array, which contains all permitted Y-coordinates for a note
     */
    private void initNoteYPositions() {
        int totalPositions = ViewConstants.TOTAL_LINES + ViewConstants.TOTAL_SPACES;
        mNoteYPositions = new float[totalPositions];
        for (int i = 1; i <= totalPositions; i++) {
            mNoteYPositions[i - 1] = (canvasHeight * i) / totalPositions - 2 * mStemWidth;
        }
        ;
    }

    /**
     * Initializes the Paint objects to be used in onDraw()
     */
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
