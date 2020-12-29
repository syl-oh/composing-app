package com.example.composingapp.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.widget.LinearLayout;

import androidx.core.view.ViewCompat;

import com.example.composingapp.utils.music.BarObserver;
import com.example.composingapp.utils.music.Note;
import com.example.composingapp.utils.music.Tone;
import com.example.composingapp.views.viewtools.barviewgroupdrawer.BarViewGroupDrawer;
import com.example.composingapp.views.viewtools.noteviewdrawer.NoteViewDrawer;
import com.example.composingapp.views.viewtools.positiondict.BarPositionDict;
import com.example.composingapp.views.viewtools.positiondict.NotePositionDict;
import com.example.composingapp.views.viewtools.positiondict.PositionDict;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static com.example.composingapp.views.viewtools.ViewConstants.BARLINE_SIZE;
import static com.example.composingapp.views.viewtools.ViewConstants.BARS_PER_LINE;
import static com.example.composingapp.views.viewtools.ViewConstants.TOTAL_LINES;

public class BarViewGroup extends LinearLayout  {
    private static final String TAG = "BarViewGroup";
    private BarPositionDict barPositionDict;
    private LinearLayout.LayoutParams mBarViewGroupParams;
    private BarViewGroupDrawer barViewGroupDrawer;
    private BarObserver mBarObserver;
    private ArrayList<NoteView> mNoteViewList;

    public BarViewGroup(Context context) {
        super(context);
        init();
    }

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
        mBarViewGroupParams = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT / BARS_PER_LINE,
                LayoutParams.WRAP_CONTENT);
        mBarViewGroupParams.weight = 1;

        // Initialize bar properties
        mNoteViewList = new ArrayList<>();
    }

    /**
     * Updates NoteView children based on Notes in BarObserver
     */
    private void updateChildrenFromBarObserver() {
        ArrayList<Note> noteArrayList = mBarObserver.getNoteArrayList();
        for (Note note : noteArrayList) {
            NoteView noteView = new NoteView(getContext(), note, mBarObserver.getClef());
            noteView.setId(ViewCompat.generateViewId());
            noteView.setLayoutParams(mBarViewGroupParams);
            this.addView(noteView);
            mNoteViewList.add(noteView);
            Log.d(TAG, "updateChildrenFromBarObserver: ");
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // Update the y position dictionaries
        if (mBarObserver != null) {
            barPositionDict = new BarPositionDict(w, h, mBarObserver.getClef());
            barViewGroupDrawer = new BarViewGroupDrawer(barPositionDict);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        barViewGroupDrawer.setPositionDictsFromNoteViews(mNoteViewList);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (barViewGroupDrawer != null) {
            barViewGroupDrawer.draw(canvas);
        }
    }
}
