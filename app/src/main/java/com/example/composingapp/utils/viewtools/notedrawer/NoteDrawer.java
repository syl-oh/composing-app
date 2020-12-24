package com.example.composingapp.utils.viewtools.notedrawer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.composingapp.utils.interfaces.DrawerComponent;
import com.example.composingapp.utils.interfaces.DrawerComposite;
import com.example.composingapp.utils.interfaces.DrawerLeaf;
import com.example.composingapp.utils.music.Note;
import com.example.composingapp.utils.viewtools.NotePositionDict;
import com.example.composingapp.utils.viewtools.ViewConstants;

import java.util.ArrayList;

public class NoteDrawer implements DrawerComposite {
    private static final String TAG = "NoteDrawer";
    private final NotePositionDict mPositionDict;
    private Paint mNotePaint;
    private Paint mStemPaint;
    private int mStemWidth;
    private float mNoteRadius;
    private Float mStemHeight;
    private float mNoteX, mNoteY;
    private ArrayList<DrawerComponent> drawers;
    private Note mNote;
    private float mBaseLeftX, mBaseRightX, mBaseTopY, mBaseBottomY;

    public NoteDrawer(Note note, float noteX, float noteY, NotePositionDict positionDict) {
        // Init fields
        mStemHeight = positionDict.getOctaveHeight();
        mNoteRadius = positionDict.getSingleSpaceHeight() / 2;
        mNoteX = noteX;
        mNoteY = noteY;
        mPositionDict = positionDict;
        mNote = note;
        initBaseNotePos();
        initPaint();

        // Init drawers
        drawers = new ArrayList<>();
        add(new StemLeaf());
        add(new FilledBaseLeaf());
        add(new HalfNoteHollowBaseLeaf());
    }

    /**
     * Updates the note for the NoteDrawer to draw
     * @param note  The new Note to draw
     */
    public void setNote(Note note) {
        this.mNote = note;
        mNoteY = mPositionDict.getNoteYOf(mNote);
        initBaseNotePos();
    }

    /**
     * Initializes the left-x, right-x, top-y, and bottom-y coordinates of the base of the note
     */
    private void initBaseNotePos() {
        mBaseLeftX = (mNoteX) - (ViewConstants.NOTE_W_TO_H_RATIO * mNoteRadius);
        mBaseRightX = (mNoteX) + (ViewConstants.NOTE_W_TO_H_RATIO * mNoteRadius);
        mBaseTopY = (mNoteY) + (mNoteRadius);
        mBaseBottomY = (mNoteY) - (mNoteRadius);
    }

    /**
     * Initializes the Paint used for drawing
     */
    private void initPaint() {
        mNotePaint = new Paint();
        mNotePaint.setColor(Color.parseColor("black"));
        mNotePaint.setAntiAlias(true);
        mStemPaint = new Paint();
        mStemPaint.setColor(Color.parseColor("black"));
        mStemWidth = ViewConstants.STEM_WIDTH;
        mStemPaint.setStrokeWidth(mStemWidth);
        mStemPaint.setColor(Color.parseColor("black"));
    }



    @Override
    public void draw(Canvas canvas) {
        drawers.forEach((drawer) -> drawer.draw(canvas));
    }

    @Override
    public void add(DrawerComponent drawerComponent) {
        drawers.add(drawerComponent);
    }

    @Override
    public void remove(DrawerComponent drawerComponent) {
        drawers.remove(drawerComponent);
    }

    @Override
    public ArrayList<DrawerComponent> getDrawerComponents() {
        return drawers;
    }


    class StemLeaf implements DrawerLeaf {
        @Override
        public void draw(Canvas canvas) {
            float midY = (mBaseTopY + mBaseBottomY) / 2;
            canvas.drawLine(mBaseRightX - (float) mStemWidth / 2,
                    midY,
                    mBaseRightX - (float) mStemWidth / 2,
                    midY - mStemHeight,
                    mStemPaint);
        }
    }

    class FilledBaseLeaf implements DrawerLeaf {
        @Override
        public void draw(Canvas canvas) {
            canvas.drawOval(mBaseLeftX, mBaseTopY, mBaseRightX, mBaseBottomY, mNotePaint);
        }
    }

    class HalfNoteHollowBaseLeaf implements DrawerLeaf {
        @Override
        public void draw(Canvas canvas) {
            Paint tempNotePaint = new Paint(mNotePaint); // Copy the top-level class paint
            tempNotePaint.setColor(Color.WHITE);
            canvas.drawOval(mBaseLeftX, mBaseTopY, mBaseRightX, mBaseBottomY, tempNotePaint);
        }
    }
}
