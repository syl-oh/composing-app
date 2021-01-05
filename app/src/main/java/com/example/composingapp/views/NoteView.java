package com.example.composingapp.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.composingapp.utils.interfaces.Clickable;
import com.example.composingapp.utils.interfaces.TouchHandler;
import com.example.composingapp.utils.music.Music;
import com.example.composingapp.utils.music.Note;
import com.example.composingapp.views.touchhandlers.MoveHandler;
import com.example.composingapp.views.touchhandlers.ToggleClickedHandler;
import com.example.composingapp.views.viewtools.noteviewdrawer.NoteViewDrawer;
import com.example.composingapp.views.viewtools.positiondict.NotePositionDict;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class NoteView extends View implements Clickable {
    private static final String TAG = "NoteView";
    private NotePositionDict notePositionDict;
    private Music.Clef mClef;
    private Note mNote;
    private NoteViewDrawer mNoteViewDrawer;
    private BarViewGroup mBarViewGroup;
    private boolean mIsClicked = false;
    private ArrayList<TouchHandler> touchHandlers = new ArrayList<>();

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

    public BarViewGroup getBarViewGroup() {
        return mBarViewGroup;
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
//        mGestureDetector = new GestureDetector(getContext(), this);
        mBarViewGroup = barViewGroup;

        // Add the drag handler
        touchHandlers.add(ToggleClickedHandler.INSTANCE);
        touchHandlers.add(MoveHandler.INSTANCE);

        // Set the touch listener for events
        setOnTouchListener((v, event) -> {
            touchHandlers.forEach(handler -> handler.handleTouch(v, event));
            performClick();
            return true;
        });
        this.setBackgroundColor(Color.TRANSPARENT);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        notePositionDict = new NotePositionDict(mNote, mClef, (float) w, (float) h);
        mNoteViewDrawer = new NoteViewDrawer(notePositionDict);
//        Log.d(TAG, "onSizeChanged: x:" + getX() +" y:" + getY() + " w:" + w + " h:" + h);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mNoteViewDrawer.draw(canvas);
//        canvas.drawRect(notePositionDict.getTouchAreaRectF(), mNoteViewDrawer.getPaint());
    }

    @Override
    public void updateColour(int colour) {
        mNoteViewDrawer.getPaint().setColor(colour);
        invalidate();
    }

    @Override
    public boolean isClicked() {
        return mIsClicked;
    }

    @Override
    public void setClicked(boolean isClicked) {
        mIsClicked = isClicked;
        if (mIsClicked) {
            mBarViewGroup.setLastClickedNoteView(this);
        }
    }

    @NotNull
    @Override
    public RectF getTouchAreaRectF() {
        return notePositionDict.getTouchAreaRectF();
    }
}
