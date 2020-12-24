package com.example.composingapp.views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.util.TypedValue;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.composingapp.utils.music.Music;
import com.example.composingapp.utils.music.Note;
import com.example.composingapp.utils.music.Tone;
import com.example.composingapp.utils.viewtools.NoDragShadowBuilder;
import com.example.composingapp.utils.viewtools.PositionDict;
import com.example.composingapp.utils.viewtools.notedrawer.NoteDrawer;

import org.jetbrains.annotations.NotNull;

import static java.lang.Math.abs;

public class NoteView extends View implements OnGestureListener, View.OnDragListener {
    private static final String TAG = "NoteView";
    private Float mNoteX, mNoteY;
    private PositionDict positionDict;
    private int mHeight, mWidth;
    private Music.Clef mClef;
    private Note mNote;
    private GestureDetector mGestureDetector;
    private NoteDrawer mNoteDrawer;

    /**
     * Constructor for programmatically creating a NoteView
     *
     * @param context Context of the view
     * @param note    Note object
     */
    public NoteView(Context context, @NonNull Note note, @NonNull Music.Clef clef) {
        super(context);
        init(note, clef);
    }


    /**
     * Initializes all objects used for drawing
     */
    private void init(Note note, Music.Clef clef) {
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


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        mHeight = h;
        positionDict = new PositionDict(mHeight, mClef);
        mNoteX = (float) (mWidth / 2);
        mNoteY = positionDict.getNoteYOf(mNote);
//        Log.d(TAG, "onSizeChanged: mNoteY: " + mNoteY);

        mNoteDrawer = new NoteDrawer(mNote, mNoteX, mNoteY, positionDict);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mNoteDrawer.draw(canvas);
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
                Float semiSpace = positionDict.getSingleSpaceHeight() / 2; // Semispace distance
                float dy = mNoteY - event.getY();                          // Change in y position

                // Move up to the note a semispace above if the note has been dragged that far
                if (abs(dy) >= semiSpace) {
                    // Find the new tone
                    Float newToneY = dy > 0 ? mNoteY - semiSpace : mNoteY + semiSpace;
                    Tone nextTone = positionDict.getYToToneMap().get(newToneY);
                    Log.d(TAG, "onDrag: nextTone: " + nextTone.getPitchClass() + " octave " +
                            nextTone.getOctave());

                    // Update the note and the NoteView, then redraw
                    mNote = new Note(
                            nextTone.getPitchClass(),
                            nextTone.getOctave(),
                            mNote.getNoteLength());
                    mNoteY = positionDict.getNoteYOf(mNote);
                    mNoteDrawer.setNote(mNote);
//                    Log.d(TAG, "onDrag: mNoteY " + mNoteY);
                    invalidate();
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
        NoDragShadowBuilder builder =  new NoDragShadowBuilder(this); // Shadowless drag
        this.startDragAndDrop(null, builder, null, 0);
        builder.getView().setOnDragListener(this);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}
