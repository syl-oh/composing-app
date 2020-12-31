package com.example.composingapp.views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.composingapp.utils.music.Music;
import com.example.composingapp.utils.music.Note;
import com.example.composingapp.utils.music.Tone;
import com.example.composingapp.views.viewtools.noteviewdrawer.NoteViewDrawer;
import com.example.composingapp.views.viewtools.positiondict.NotePositionDict;

import org.jetbrains.annotations.NotNull;

import static java.lang.Math.abs;

public class NoteView extends View implements OnGestureListener, View.OnDragListener {
    private static final String TAG = "NoteView";
    private NotePositionDict notePositionDict;
    private Music.Clef mClef;
    private Note mNote;
    private GestureDetector mGestureDetector;
    private NoteViewDrawer mNoteViewDrawer;
    private BarViewGroup mBarViewGroup;

    /**
     * Constructor for programmatically creating a NoteView
     *
     * @param context Context of the view
     * @param note    Note object
     */
    public NoteView(Context context, BarViewGroup barViewGroup, @NonNull Note note, @NonNull Music.Clef clef) {
        super(context);
        init(note, clef, barViewGroup);
    }

    public NoteViewDrawer getNoteViewDrawer() {
        return mNoteViewDrawer;
    }

    public NotePositionDict getNotePositionDict() {
        return notePositionDict;
    }

    /**
     * Initializes all objects used for drawing
     */
    private void init(Note note, Music.Clef clef, BarViewGroup barViewGroup) {
        if (note != null) {
            mNote = note;
        } else {
            Log.e(TAG, "init: FATAL: Recieved null note for NoteView with ID "
                    + this.getId());
        }

        if (clef != null) {
            mClef = clef;
        } else {
            Log.e(TAG, "init: FATAL: Recieved null clef for NoteView with ID "
                    + this.getId());
        }
        mGestureDetector = new GestureDetector(getContext(), this);
        mBarViewGroup = barViewGroup;
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        notePositionDict = new NotePositionDict(mNote, mClef, (float) w, (float) h);
        mNoteViewDrawer = new NoteViewDrawer(notePositionDict);
        Log.d(TAG, "onSizeChanged: noteViewDrawer created");
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mNoteViewDrawer.draw(canvas);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return true;
    }


    @Override
    public boolean onDrag(View v, @NotNull DragEvent event) {
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
//                Log.d(TAG, "onDrag: drag started.");
                return true;

            case DragEvent.ACTION_DRAG_ENTERED:
//                Log.d(TAG, "onDrag: drag entered.");
                return true;

            case DragEvent.ACTION_DRAG_LOCATION:
                Float semiSpace = notePositionDict.getSingleSpaceHeight() / 2; // Semispace distance
                Float noteY = notePositionDict.getNoteY();
                float dy = noteY - event.getY();                          // Change in y position

                // Move up to the note a semispace above if the note has been dragged that far
                if (abs(dy) >= semiSpace) {
                    // Find the new tone
                    Float newToneY = dy > 0 ? noteY - semiSpace : noteY + semiSpace;
                    Tone nextTone = notePositionDict.getYToToneMap().get(newToneY);
//                    Log.d(TAG, "onDrag: nextTone: " + nextTone.getPitchClass() + " octave " +
//                            nextTone.getOctave());

                    // Update the note and the NoteView, then redraw
                    mNote = new Note(
                            nextTone.getPitchClass(),
                            nextTone.getOctave(),
                            mNote.getNoteLength());
                    notePositionDict.setNote(mNote);
                    mNoteViewDrawer.resetWith(notePositionDict);
                    mBarViewGroup.invalidate();
//                    Log.d(TAG, "onDrag: mNoteY " + newToneY);
                }
                return true;

            case DragEvent.ACTION_DRAG_EXITED:
//                Log.d(TAG, "onDrag: exited.");
                return true;

            case DragEvent.ACTION_DROP:
//                Log.d(TAG, "onDrag: dropped.");
                return true;

            case DragEvent.ACTION_DRAG_ENDED:
//                Log.d(TAG, "onDrag: ended.");
                return true;

            // An unknown action type was received.
            default:
                Log.e(TAG, "Unknown action type received by OnStartDragListener.");
                break;
        }
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        if (mNote.getPitchClass() != Music.PitchClass.REST) {
            NoDragShadowBuilder builder = new NoDragShadowBuilder(this); // Shadowless drag
            this.startDragAndDrop(null, builder, null, 0);
            builder.getView().setOnDragListener(this);
        }
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    /**
     * Class to enable drag and drop that does not create a shadow
     */
    private static class NoDragShadowBuilder extends View.DragShadowBuilder {
        public NoDragShadowBuilder(View view) {
            super(view);
        }

        @Override
        public void onDrawShadow(Canvas canvas) {
        }
    }
}
