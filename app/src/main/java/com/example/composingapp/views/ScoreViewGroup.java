package com.example.composingapp.views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.core.view.ViewCompat;

import com.example.composingapp.music.Music;
import com.example.composingapp.music.Note;

public class ScoreViewGroup extends LinearLayout {
    private static final String TAG = "StaffViewGroup";
    private Music.NoteLength mBeatUnit;
    private LinearLayout.LayoutParams mScoreLineViewGroupParams;
    private int mBeatsPerBar;
    private Music.Clef mClef;

    public ScoreViewGroup(Context context) {
        super(context);
        init();
    }

    public ScoreViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    void init() {
        mScoreLineViewGroupParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mScoreLineViewGroupParams.weight = 1;
        mClef = Music.Clef.TREBLE_CLEF;

        // Temporary code: testing instance of ScoreLineView
        BarViewGroup barViewGroup = new BarViewGroup(getContext(), mClef);
        barViewGroup.setId(ViewCompat.generateViewId());
        barViewGroup.setLayoutParams(mScoreLineViewGroupParams);
        Note note = new Note(Music.PitchClass.C_NATURAL, 4, Music.NoteLength.QUARTER_NOTE);
        barViewGroup.addNoteToChildren(note);

        this.addView(barViewGroup);
    }
}