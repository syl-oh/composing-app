package com.example.composingapp.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.example.composingapp.music.Music;
import com.example.composingapp.music.Note;
import com.example.composingapp.music.Tone;

import java.util.HashMap;

public class NoteView extends View {
    private static final String TAG = "NoteView";
    private Paint mNotePaint, mStemPaint;
    private float mNoteRadius, mStemWidth, mStemHeight;
    private Float mNoteX, mNoteY;
    private NotePositionDict positionDict;
    private int mHeight, mWidth;
    private Music.Clef mClef;
    private Note mNote;

    /**
     * Constructor for programmatically creating a NoteView
     *
     * @param context   Context of the view
     * @param note Note object
     */
    public NoteView(Context context, Note note, Music.Clef clef) {
        super(context);
        init(note, clef);

    }


    /**
     * Initializes all objects used for drawing
     */
    private void init(Note note, Music.Clef clef) {
        mNote = note;
        mClef = clef;
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
            try {
                mNoteY = toneToYMap.get(mNote);
            } catch (NullPointerException e) {
                Log.e(TAG, "onSizeChanged: NullPointerException, unable to retrieve y-position " +
                        "of pitchclass " + mNote.getPitchClass() + " and octave "
                        + mNote.getOctave());
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
