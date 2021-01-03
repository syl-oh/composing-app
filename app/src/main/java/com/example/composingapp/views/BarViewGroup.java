package com.example.composingapp.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.core.view.ViewCompat;

import com.example.composingapp.utils.music.BarObserver;
import com.example.composingapp.utils.music.Note;
import com.example.composingapp.views.touchlisteners.ToggleColourListener;
import com.example.composingapp.views.viewtools.LayoutWeightMap;
import com.example.composingapp.views.viewtools.barviewgroupdrawer.BarViewGroupDrawer;
import com.example.composingapp.views.viewtools.positiondict.BarPositionDict;

import java.util.ArrayList;

public class BarViewGroup extends LinearLayout {
    private static final String TAG = "BarViewGroup";
    private final ArrayList<NoteView> mNoteViewList = new ArrayList<>();
    private BarPositionDict mBarPositionDict;
    private BarViewGroupDrawer mBarViewGroupDrawer;
    private BarObserver mBarObserver;

    public BarViewGroup(Context context) {
        super(context);
        init();
    }

    public BarPositionDict getBarPositionDict() {
        return mBarPositionDict;
    }

    /**
     * Assigns the barObserver for this BarViewGroup, then updates/ creates its NoteView children
     *
     * @param barObserver The BarObserver for this BarViewGroup
     */
    public void setBarObserver(BarObserver barObserver) {
        this.mBarObserver = barObserver;
        updateChildrenFromBarObserver();
    }

    /**
     * Initializes the parameters, paints, and bar properties
     */
    private void init() {
        // Initialize layout parameters
        setWillNotDraw(false); // Enable drawing of the ViewGroup
        setOrientation(LinearLayout.HORIZONTAL);
        setBackgroundColor(Color.TRANSPARENT);
    }

    /**
     * Updates NoteView children based on Notes in BarObserver
     */
    private void updateChildrenFromBarObserver() {
        ArrayList<Note> noteArrayList = mBarObserver.getNoteArrayList();
        for (Note note : noteArrayList) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    0,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.weight = LayoutWeightMap.weightOf(note);
            NoteView noteView = new NoteView(getContext(), this, note, mBarObserver.getClef());
            noteView.setId(ViewCompat.generateViewId());
            noteView.setLayoutParams(layoutParams);
            this.addView(noteView);
            mNoteViewList.add(noteView);
        }
        forceLayout();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // Update the y position dictionaries
        if (mBarObserver != null) {
            mBarPositionDict = new BarPositionDict(w, h, mBarObserver.getClef());
            mBarViewGroupDrawer = new BarViewGroupDrawer(mBarPositionDict);
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mBarViewGroupDrawer.setNoteViews(mNoteViewList);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mBarViewGroupDrawer.draw(canvas);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // Reset the clicked status of any clicked children
            for (NoteView noteView : mNoteViewList) {
//                noteView.setClicked(!noteView.isClicked());
                if (noteView.isClicked()) {
                    ToggleColourListener.INSTANCE.toggleColour(noteView);
                }
            }
        }
        return false;
    }

    @Override
    public void invalidate() {
        super.invalidate();
        mBarViewGroupDrawer.resetDrawers();
        for (NoteView noteView : mNoteViewList) {
            noteView.invalidate();
        }
    }
}
