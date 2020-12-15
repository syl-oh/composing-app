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

import com.example.composingapp.music.Music;
import com.example.composingapp.music.Tone;

import java.util.HashMap;

public class NoteView extends View {
    private static final String TAG = "NoteView";
    private Paint mNotePaint, mStemPaint;
    private float mNoteRadius, mStemWidth, mStemHeight;
    private Float mNoteX, mNoteY;
    private NotePositionDict positionDict;
    private int mHeight, mWidth;
    private Music.Staff mClef;


    public NoteView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * Constructor for programmatically creating a NoteView
     *
     * @param context   Context of the view
     * @param attrs     Attributes of the view
     * @param yPosition Y-position of the note
     */
    public NoteView(Context context, @Nullable AttributeSet attrs, Float yPosition) {
        super(context, attrs);
        init();
        mNoteY = yPosition;
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
        mStemWidth = convertDpToPx(ViewConstants.STEM_WIDTH);
        mStemPaint.setStrokeWidth(mStemWidth);
        mStemPaint.setColor(Color.parseColor("black"));

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        mHeight = h;
        positionDict = new NotePositionDict(mHeight, mClef);
        mNoteX = (float) (mWidth / 2);
        if (mNoteY == null) {
            HashMap<Tone, Float> toneToYMap = positionDict.getToneToYMap();
            Tone middleTone = mClef.getBarlineTones()[2];
            try {
                mNoteY = toneToYMap.get(middleTone);
            } catch (NullPointerException e) {
                Log.e(TAG, "onSizeChanged: NullPointerException, unable to retrieve y-position " +
                        "of pitchclass " + middleTone.getPitchClass() + " and octave "
                        + middleTone.getOctave()) ;
            }
        }
        mStemHeight = positionDict.getOctaveHeight();
        mNoteRadius = positionDict.getSingleSpaceHeight() / 2;
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
