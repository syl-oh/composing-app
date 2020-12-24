package com.example.composingapp.utils.viewtools.notedrawer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.example.composingapp.utils.interfaces.ComponentDrawer;
import com.example.composingapp.utils.interfaces.CompositeDrawer;
import com.example.composingapp.utils.interfaces.LeafDrawer;
import com.example.composingapp.utils.music.Music;
import com.example.composingapp.utils.music.Note;
import com.example.composingapp.utils.music.Tone;
import com.example.composingapp.utils.viewtools.NotePositionDict;
import com.example.composingapp.utils.viewtools.ViewConstants;

import java.util.ArrayList;

import static com.example.composingapp.utils.viewtools.ViewConstants.FILLED_NOTE_ANGLE;
import static com.example.composingapp.utils.viewtools.ViewConstants.HALF_NOTE_ANGLE_INSIDE;
import static com.example.composingapp.utils.viewtools.ViewConstants.WHOLE_NOTE_BASE_ANGLE;
import static com.example.composingapp.utils.viewtools.ViewConstants.WHOLE_NOTE_INNER_ANGLE;

public class NoteDrawer implements CompositeDrawer {
    private static final String TAG = "NoteDrawer";
    private final NotePositionDict mPositionDict;
    private Paint mNotePaint;
    private Paint mStemPaint;
    private int mStemWidth;
    private float mNoteRadius;
    private Float mStemHeight;
    private float mNoteX, mNoteY;
    private ArrayList<ComponentDrawer> drawers;
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
        add(new FilledBaseLeaf(WHOLE_NOTE_BASE_ANGLE));
        add(new HollowBaseLeaf(WHOLE_NOTE_INNER_ANGLE));
    }

    /**
     * Updates the note for the NoteDrawer to draw
     *
     * @param note The new Note to draw
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
    public void add(ComponentDrawer drawerComponent) {
        drawers.add(drawerComponent);
    }

    @Override
    public void remove(ComponentDrawer drawerComponent) {
        drawers.remove(drawerComponent);
    }

    @Override
    public ArrayList<ComponentDrawer> getDrawerComponents() {
        return drawers;
    }


    class StemLeaf implements LeafDrawer {
        float mThirdLineYPos;

        public StemLeaf() {
            // Retrieve the y position of the 3rd line on the bar to determine which way to draw
            //     the stem
            Music.Clef clef = mPositionDict.getClef();
            Tone thirdLineTone = clef.getBarlineTones()[2];
            try {
                mThirdLineYPos = mPositionDict.getToneToBarlineYMap().get(thirdLineTone);
            } catch (NullPointerException e) {
                Log.e(TAG, "StemLeaf: NullPointerException, could not retrieve thirdLineTone" +
                        "from toneToBarlineYMap");
            }
        }

        @Override
        public void draw(Canvas canvas) {
            float startY = (mBaseTopY + mBaseBottomY) / 2;
            float xPos, endY;

            // If the note is BELOW the third line
            if (mNoteY > mThirdLineYPos) {
                // Draw the stem on its right, pointing upwards
                xPos = mBaseRightX - (float) mStemWidth / 2;
                endY = startY - mStemHeight;
            } else {
                xPos = mBaseLeftX + (float) mStemWidth / 2;
                endY = startY + mStemHeight;
            }
            canvas.drawLine(xPos, startY, xPos, endY, mStemPaint);
        }
    }

    class FilledBaseLeaf implements LeafDrawer {
        private float mAngle;

        public FilledBaseLeaf(float angle) {
            this.mAngle = angle;
        }

        @Override
        public void draw(Canvas canvas) {
            canvas.save();
            canvas.rotate(mAngle, mNoteX, mNoteY);
            canvas.drawOval(mBaseLeftX, mBaseTopY, mBaseRightX, mBaseBottomY, mNotePaint);
            canvas.restore();
        }
    }

    class HollowBaseLeaf implements LeafDrawer {
        private float mAngle;
        private Paint tempNotePaint;

        public HollowBaseLeaf(float angle) {
            this.mAngle = angle;
            tempNotePaint = new Paint(mNotePaint); // Copy the top-level class paint
            tempNotePaint.setColor(Color.WHITE);
        }

        @Override
        public void draw(Canvas canvas) {
            // Save the canvas before rotating
            canvas.save();
            canvas.rotate(mAngle, mNoteX, mNoteY);
            float baseUpY = mNoteY + mNoteRadius / 2;
            float baseDownY = mNoteY - mNoteRadius / 2;

            // If mAngle > 180, baseUp relatively becomes the bottom!
            if (mAngle > 180) {
                canvas.drawOval(mBaseLeftX, baseDownY, mBaseRightX, baseUpY, tempNotePaint);
            } else {
                canvas.drawOval(mBaseLeftX, baseUpY, mBaseRightX, baseDownY, tempNotePaint);
            }

            // Restore the canvas for further drawing
            canvas.restore();
        }
    }
}
