package com.example.composingapp.utils.viewtools.noteviewdrawer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import com.example.composingapp.utils.interfaces.ComponentDrawer;
import com.example.composingapp.utils.interfaces.CompositeDrawer;
import com.example.composingapp.utils.interfaces.LeafDrawer;
import com.example.composingapp.utils.music.Music;
import com.example.composingapp.utils.music.Note;
import com.example.composingapp.utils.music.Tone;
import com.example.composingapp.utils.viewtools.PositionDict;
import com.example.composingapp.utils.viewtools.ViewConstants;

import java.util.ArrayList;

import static com.example.composingapp.utils.viewtools.ViewConstants.STEM_WIDTH;

public class NoteDrawer implements CompositeDrawer {
    private static final String TAG = "NoteDrawer";
    private final float FILLED_NOTE_ANGLE = 330;
    private final float HALF_NOTE_ANGLE_INSIDE = FILLED_NOTE_ANGLE;
    private final float WHOLE_NOTE_BASE_ANGLE = 0;
    private final float WHOLE_NOTE_INNER_ANGLE = 75;
    private final float QUARTER_REST_X_DEVIANCE_FACTOR = (float) 0.05;
    private final float LONG_REST_X_DEVIANCE_FACTOR = (float) 0.08;
    private final float REST_STROKE_WIDTH = 2 * STEM_WIDTH;
    private final PositionDict mPositionDict;
    private final Music.Clef mClef;
    private Float mThirdLineY = null;
    private Paint mNotePaint;
    private Float mNoteRadius;
    private Float mStemHeight;
    private Float mNoteX, mNoteY;
    private ArrayList<ComponentDrawer> mDrawers;
    private Note mNote;
    private float mBaseLeftX, mBaseRightX, mBaseTopY, mBaseBottomY;

    public NoteDrawer(Note note, float noteX, float noteY, PositionDict positionDict) {
        // Init fields
        mStemHeight = positionDict.getOctaveHeight();
        mNoteRadius = positionDict.getSingleSpaceHeight() / 2;
        mNoteX = noteX;
        mNoteY = noteY;
        mPositionDict = positionDict;
        mNote = note;
        mClef = mPositionDict.getClef();
        initBaseNotePos();
        initPaint();

        // Retrieve the y position of the 3rd line on the bar to determine which way to draw
        //     the stem
        Tone thirdLineTone = mClef.getBarlineTones()[2];
        try {
            mThirdLineY = mPositionDict.getToneToBarlineYMap().get(thirdLineTone);
        } catch (NullPointerException e) {
            Log.e(TAG, "StemLeaf: NullPointerException, could not retrieve thirdLineTone" +
                    "from toneToBarlineYMap");
        }

        // Init drawers
        mDrawers = new ArrayList<>();
        add(new StemLeaf());
        add(new FilledBaseLeaf(FILLED_NOTE_ANGLE));
        add(new HollowBaseLeaf(HALF_NOTE_ANGLE_INSIDE));
        add(new LongRestLeaf(Music.NoteLength.WHOLE_NOTE));
        add(new SharpLeaf(mNoteY, mNoteX - mPositionDict.getSingleSpaceHeight(), mPositionDict, mNotePaint));
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
        mNotePaint.setDither(true);
        mNotePaint.setStrokeWidth(STEM_WIDTH);
        mNotePaint.setStrokeJoin(Paint.Join.ROUND);
        mNotePaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    public void draw(Canvas canvas) {
        mDrawers.forEach((drawer) -> drawer.draw(canvas));
    }

    @Override
    public void add(ComponentDrawer componentDrawer) {
        mDrawers.add(componentDrawer);
    }

    @Override
    public void remove(ComponentDrawer componentDrawer) {
        mDrawers.remove(componentDrawer);
    }

    @Override
    public ArrayList<ComponentDrawer> getDrawerComponents() {
        return mDrawers;
    }

    class LongRestLeaf implements LeafDrawer {
        private final RectF rect;
        private Float bottomLeftX, bottomRightX, bottomY;
        private Paint bottomPaint;

        public LongRestLeaf(Music.NoteLength noteLength) {
            // X position
            Float dx = mNoteX * LONG_REST_X_DEVIANCE_FACTOR;
            Float rectLeftX = mNoteX - dx;
            Float rectRightX = mNoteX + dx;
            bottomLeftX = rectLeftX - dx;
            bottomRightX = rectRightX + dx;

            // Y Position of base
            bottomPaint = new Paint(mNotePaint);
            bottomPaint.setStrokeWidth(bottomPaint.getStrokeWidth() * 2);

            if (noteLength == Music.NoteLength.WHOLE_NOTE) {
                bottomY = mThirdLineY + (REST_STROKE_WIDTH / 2);
            } else {
                bottomY = mThirdLineY - (REST_STROKE_WIDTH / 2);
            }

            // Rect
            Float hatHeight = mPositionDict.getSingleSpaceHeight() / 2;

            if (noteLength == Music.NoteLength.WHOLE_NOTE) {
                rect = new RectF(rectLeftX, bottomY + hatHeight, rectRightX, bottomY);
            } else {
                rect = new RectF(rectLeftX, bottomY - hatHeight, rectRightX, bottomY);
            }
        }

        @Override
        public void draw(Canvas canvas) {
            canvas.drawLine(bottomLeftX, bottomY, bottomRightX, bottomY, bottomPaint);
            canvas.drawRect(rect, mNotePaint);
        }
    }

    class QuarterRestLeaf implements LeafDrawer {
        private final RectF curvedRect;
        float firstX, firstY, secondX, secondY, thirdX, thirdY, fourthY;
        Paint restPaint;

        public QuarterRestLeaf() {
            restPaint = new Paint(mNotePaint);
            restPaint.setStrokeWidth(REST_STROKE_WIDTH);
            restPaint.setStyle(Paint.Style.STROKE);
            // x positions: deviance from the initial mNoteX
            float dx = QUARTER_REST_X_DEVIANCE_FACTOR * mNoteX;
            firstX = mNoteX - dx;
            secondX = mNoteX + dx;

            // y positions
            float halfSpace = mPositionDict.getSingleSpaceHeight() / 2;
            // Start in the middle of the top space
            try {
                firstY = mPositionDict.getToneToBarlineYMap().get(mClef.getBarlineTones()[4])
                        + halfSpace;
            } catch (NullPointerException e) {
                Log.e(TAG, "QuarterRestLeaf: NullPointerException: could not retrieve Y position" +
                        "of the middle of the top space in the bar");
            }
            // Move down a space
            secondY = firstY + 2 * halfSpace;
            // Move down half a space
            thirdY = secondY + halfSpace;
            // Move down half a space
            fourthY = thirdY + halfSpace;
            // For the curved stroke, create the rect for the oval
            curvedRect = new RectF(firstX, fourthY,
                    secondX + 2 * dx,
                    fourthY + 2 * halfSpace);
        }

        @Override
        public void draw(Canvas canvas) {
            // First Stroke
            canvas.drawLine(firstX, firstY, secondX, secondY, restPaint);
            // Second Stroke
            canvas.drawLine(secondX, secondY, firstX, thirdY, restPaint);
            // Third Stroke
            canvas.drawLine(firstX, thirdY, secondX, fourthY, restPaint);
            // Fourth Stroke (Curved Stroke)
            canvas.drawArc(curvedRect, 90, 180, false, restPaint);
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

    class StemLeaf implements LeafDrawer {
        public StemLeaf() {

        }

        @Override
        public void draw(Canvas canvas) {
            float startY = (mBaseTopY + mBaseBottomY) / 2;
            float xPos, endY;

            // If the note is BELOW the third line
            if (mNoteY > mThirdLineY) {
                // Draw the stem on its right, pointing upwards
                xPos = mBaseRightX - (float) STEM_WIDTH / 2;
                endY = startY - mStemHeight;
            } else {
                xPos = mBaseLeftX + (float) STEM_WIDTH / 2;
                endY = startY + mStemHeight;
            }
            canvas.drawLine(xPos, startY, xPos, endY, mNotePaint);
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

}
