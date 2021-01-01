package com.example.composingapp.views.viewtools.noteviewdrawer.composites;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import com.example.composingapp.utils.interfaces.ComponentDrawer;
import com.example.composingapp.utils.interfaces.CompositeDrawer;
import com.example.composingapp.utils.interfaces.LeafDrawer;
import com.example.composingapp.utils.music.Music;
import com.example.composingapp.utils.music.Note;
import com.example.composingapp.views.viewtools.positiondict.NotePositionDict;

import java.util.ArrayList;

import static com.example.composingapp.views.viewtools.ViewConstants.STEM_WIDTH;

public class RestComposite implements CompositeDrawer {
    private static final String TAG = "RestComposite";
    private final float REST_STROKE_WIDTH = 2 * STEM_WIDTH;
    private final NotePositionDict mNotePositionDict;
    private final Paint mRestPaint;
    private final Float mNoteX;
    private final Float mThirdLineY;
    private final Music.Clef mClef;
    ArrayList<ComponentDrawer> mDrawers;

    public RestComposite(NotePositionDict notePositionDict, Paint paint) {
        mNotePositionDict = notePositionDict;
        Note mNote = mNotePositionDict.getNote();
        mClef = mNotePositionDict.getClef();
        mNoteX = mNotePositionDict.getNoteX();
        mThirdLineY = mNotePositionDict.getThirdLineY();
        mRestPaint = paint;
        mDrawers = new ArrayList<>();

        // Add the required drawers
        Music.NoteLength noteLength = mNote.getNoteLength();
        if (noteLength == Music.NoteLength.QUARTER_NOTE) {
            add(new QuarterRestLeaf());
        } else if (noteLength == Music.NoteLength.WHOLE_NOTE || noteLength == Music.NoteLength.HALF_NOTE) {
            add(new LongRestLeaf(mNote.getNoteLength()));
        } else {
            add(new ShortRestComposite(notePositionDict, paint));
        }
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
    public void draw(Canvas canvas) {
        for (ComponentDrawer drawer : mDrawers) {
            drawer.draw(canvas);
        }
    }


    /**
     * Class for drawing either half or whole rests
     */
    private class LongRestLeaf implements LeafDrawer {
        private final float xDevianceFactor = (float) 0.08;
        private final RectF rect;
        private final Float bottomLeftX;
        private final Float bottomRightX;
        private final Float bottomY;
        private final Paint bottomPaint;

        public LongRestLeaf(Music.NoteLength noteLength) {
            // X position
            float dx = mNoteX * xDevianceFactor;
            float rectLeftX = mNoteX - dx;
            float rectRightX = mNoteX + dx;
            bottomLeftX = rectLeftX - dx;
            bottomRightX = rectRightX + dx;

            // Y Position of base
            bottomPaint = new Paint(mRestPaint);
            bottomPaint.setStrokeWidth(bottomPaint.getStrokeWidth() * 2);

            if (noteLength == Music.NoteLength.WHOLE_NOTE) {
                bottomY = mThirdLineY + (REST_STROKE_WIDTH / 2);
            } else {
                bottomY = mThirdLineY - (REST_STROKE_WIDTH / 2);
            }

            // Rect
            Float hatHeight = mNotePositionDict.getSingleSpaceHeight() / 2;

            if (noteLength == Music.NoteLength.WHOLE_NOTE) {
                rect = new RectF(rectLeftX, bottomY + hatHeight, rectRightX, bottomY);
            } else {
                rect = new RectF(rectLeftX, bottomY - hatHeight, rectRightX, bottomY);
            }
        }

        @Override
        public void draw(Canvas canvas) {
            canvas.drawLine(bottomLeftX, bottomY, bottomRightX, bottomY, bottomPaint);
            canvas.drawRect(rect, mRestPaint);
        }
    }

    /**
     * Class for drawing quarter rests
     */
    private class QuarterRestLeaf implements LeafDrawer {
        private final RectF curvedRect;
        float firstX, firstY, secondX, secondY, thirdY, fourthY;
        Paint restPaint;

        public QuarterRestLeaf() {
            restPaint = new Paint(mRestPaint);
            restPaint.setStrokeWidth(REST_STROKE_WIDTH);
            restPaint.setStyle(Paint.Style.STROKE);
            // x positions: deviance from the initial mNoteX
            float dx = mNotePositionDict.getSingleSpaceHeight() / 2;
            firstX = mNoteX - dx;
            secondX = mNoteX + dx;

            // y positions
            float halfSpace = mNotePositionDict.getSingleSpaceHeight() / 2;
            // Start in the middle of the top space
            try {
                firstY = mNotePositionDict.getToneToBarlineYMap().get(mClef.getBarlineTones()[4])
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

}
