package com.example.composingapp.utils.viewtools.notedrawer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.TypedValue;

import com.example.composingapp.utils.interfaces.Drawer;
import com.example.composingapp.utils.music.Note;
import com.example.composingapp.utils.viewtools.NotePositionDict;
import com.example.composingapp.utils.viewtools.ViewConstants;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class NoteDrawer implements Drawer{
    private float mNoteRadius;
    private Float mStemHeight;
    private float mNoteX;
    private float mNoteY;
    private ArrayList<Drawer> drawers;
    private Note mNote;

    public NoteDrawer(Note note, float noteX, float noteY, NotePositionDict positionDict) {
        mStemHeight = positionDict.getOctaveHeight();
        mNoteRadius = positionDict.getSingleSpaceHeight() / 2;
        mNoteX = noteX;
        mNoteY = noteY;
        mNote = note;

        drawers.add(filledNoteDrawer());
    }

    @Override
    public void draw(Canvas canvas) {
        for (Drawer drawer : drawers) {
            drawer.draw(canvas);
        }
    }

    public Drawer filledNoteDrawer() {
        Paint mNotePaint = new Paint();
        mNotePaint.setColor(Color.parseColor("black"));
        mNotePaint.setAntiAlias(true);
        Paint mStemPaint = new Paint();
        mStemPaint.setColor(Color.parseColor("black"));
        float mStemWidth = ViewConstants.STEM_WIDTH;
        mStemPaint.setStrokeWidth(mStemWidth);
        mStemPaint.setColor(Color.parseColor("black"));


        float leftX = (mNoteX) - (ViewConstants.NOTE_W_TO_H_RATIO * mNoteRadius);
        float rightX = (mNoteX) + (ViewConstants.NOTE_W_TO_H_RATIO * mNoteRadius);
        float topY = (mNoteY) + (mNoteRadius);
        float bottomY = (mNoteY) - (mNoteRadius);


        return (canvas) -> canvas.drawOval(leftX, topY, rightX, bottomY, mNotePaint);
    }
}
