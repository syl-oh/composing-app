package com.example.composingapp.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.core.view.ViewCompat;

import com.example.composingapp.utils.music.BarObserver;
import com.example.composingapp.utils.music.Note;
import com.example.composingapp.viewmodels.ScoreViewModel;
import com.example.composingapp.views.barviewgroupdrawer.BarViewGroupDrawer;
import com.example.composingapp.views.commands.ChangeNoteCommand;
import com.example.composingapp.views.viewtools.LayoutWeightMap;
import com.example.composingapp.views.viewtools.positiondict.BarPositionDict;
import com.example.composingapp.views.viewtools.positiondict.ScorePositionDict;

import java.util.ArrayList;

public class BarViewGroup extends LinearLayout {
    private final ArrayList<NoteView> mNoteViewList = new ArrayList<>();
    private final ScorePositionDict mPositionDict;
    private final ScoreViewModel mScoreViewModel;
    private BarPositionDict mBarPositionDict;
    private BarViewGroupDrawer mBarViewGroupDrawer;
    private BarObserver mBarObserver;
    public BarViewGroup(Context context, ScoreViewModel scoreViewModel, ScorePositionDict positionDict) {
        super(context);
        mScoreViewModel = scoreViewModel;
        mPositionDict = positionDict;

        // Initialize layout parameters
        setWillNotDraw(false); // Enable drawing of the ViewGroup
        setOrientation(LinearLayout.HORIZONTAL);
        setBackgroundColor(Color.TRANSPARENT);
    }

    public BarViewGroupDrawer getBarViewGroupDrawer() {
        return mBarViewGroupDrawer;
    }

    public void setBarViewGroupDrawer(BarViewGroupDrawer mBarViewGroupDrawer) {
        this.mBarViewGroupDrawer = mBarViewGroupDrawer;
    }

    public ArrayList<NoteView> getNoteViewList() {
        return mNoteViewList;
    }


    public BarPositionDict getBarPositionDict() {
        return mBarPositionDict;
    }

    /**
     * Updates NoteView children based on Notes in BarObserver
     */
    private void updateChildrenFromBarObserver() {
        // Clear the views and reset the NoteView list
        mNoteViewList.clear();
        removeAllViews();

        // Create the new children and add them to the NoteView list
        ArrayList<Note> noteArrayList = mBarObserver.getNoteArrayList();
        for (Note note : noteArrayList) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    0,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.weight = LayoutWeightMap.weightOf(note);
            NoteView noteView = new NoteView(getContext(), this, mPositionDict, note);
            noteView.setId(ViewCompat.generateViewId());
            noteView.setLayoutParams(layoutParams);
            this.addView(noteView);
            mNoteViewList.add(noteView);
        }
        forceLayout();
    }

    /**
     * Updates the ScoreViewModel on details of a NoteView child from this BarViewGroup. Called when
     * a NoteView is dragged up or down
     *
     * @param noteView NoteView child of this BarViewGroup to update
     */
    public void updateScoreViewModel(NoteView noteView) {
        // Find the index of the NoteView
        int noteViewIndex = mNoteViewList.indexOf(noteView);
        new ChangeNoteCommand(
                mScoreViewModel, mBarObserver, noteViewIndex, noteView.getNotePositionDict().getNote()
        ).execute();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // Update the y position dictionaries
        if (mBarObserver != null) {
            mBarPositionDict = new BarPositionDict(w, h, mPositionDict);
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
    public void invalidate() {
        super.invalidate();
        mBarViewGroupDrawer.setNoteViews(mNoteViewList);
        for (NoteView noteView : mNoteViewList) {
            noteView.invalidate();
        }
    }

    public BarObserver getBarObserver() {
        return mBarObserver;
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
}
