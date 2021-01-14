package com.example.composingapp.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.composingapp.utils.interfaces.ui.Clickable;
import com.example.composingapp.utils.music.Note;
import com.example.composingapp.views.noteviewdrawer.NoteViewDrawer;
import com.example.composingapp.views.touchhandlers.NoteViewMoveHandler;
import com.example.composingapp.views.touchhandlers.ToggleClickedHandler;
import com.example.composingapp.views.viewtools.positiondict.NotePositionDict;
import com.example.composingapp.views.viewtools.positiondict.ScorePositionDict;

import org.jetbrains.annotations.NotNull;

public class NoteView extends View implements Clickable {
    //    private static final String TAG = "NoteView";
    private NotePositionDict mNotePositionDict;
    private final Note mNote;
    private NoteViewDrawer mNoteViewDrawer;
    private final BarViewGroup mBarViewGroup;
    private boolean mIsClicked = false;
    private final ScorePositionDict mScorePositionDict;
    /**
     * Constructor for programmatically creating a NoteView
     *
     * @param context Context of the view
     * @param note    Note object
     */
    public NoteView(Context context, BarViewGroup barViewGroup, ScorePositionDict positionDict, @NonNull Note note) {
        super(context);
        mNote = note;
        mScorePositionDict = positionDict;
        mBarViewGroup = barViewGroup;

        // Set the touch listener for events
        setOnTouchListener((v, event) -> {
            performClick();
            ToggleClickedHandler.INSTANCE.handleTouch(v, event);
            NoteViewMoveHandler.INSTANCE.handleTouch(v, event);
            return true;
        });
        this.setBackgroundColor(Color.TRANSPARENT);
    }

    public BarViewGroup getBarViewGroup() {
        return mBarViewGroup;
    }

    public NoteViewDrawer getNoteViewDrawer() {
        return mNoteViewDrawer;
    }

    public NotePositionDict getNotePositionDict() {
        return mNotePositionDict;
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mNotePositionDict = new NotePositionDict(mScorePositionDict, mNote, (float) w, (float) h);
        mNoteViewDrawer = new NoteViewDrawer(mNotePositionDict);
//        Log.d(TAG, "onSizeChanged: x:" + getX() +" y:" + getY() + " w:" + w + " h:" + h);
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
    }

    @NotNull
    @Override
    public RectF getTouchAreaRectF() {
        return mNotePositionDict.getTouchAreaRectF();
    }
}
