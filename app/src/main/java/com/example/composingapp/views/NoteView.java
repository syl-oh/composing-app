package com.example.composingapp.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.composingapp.music.Music;

public class NoteView extends View {
    private static final String TAG = "NoteView";
    private Paint mNotePaint, mStemPaint;
    private float mNoteX, mNoteY, mNoteRadius, mStemWidth, mStemHeight;
    private NotePositionDict positionDict;
    private int mHeight, mWidth;
    private Music.Staff mClef;


    public NoteView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * Initializes all objects used for drawing
     */
    private void init() {
        mClef = Music.Staff.TREBLE_CLEF;
        mNotePaint = new Paint();
        mNotePaint.setColor(Color.parseColor("black"));
        mNotePaint.setAntiAlias(true);
        mStemPaint = new Paint();
        mStemPaint.setColor(Color.parseColor("black"));
        mStemPaint.setStrokeWidth(mStemWidth);
        positionDict = new NotePositionDict(mHeight, mClef);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        mHeight = h;
        initDrawMeasurements();
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
        mStemHeight = (mHeight / 3);
        mStemWidth = convertDpToPx(ViewConstants.STEM_WIDTH);
        mNoteX = mWidth / 2;
        // mNoteY = mNoteYPositions[10];
        mNoteRadius = mHeight / 10;
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
