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
    private static final String TAG = "ScoreViewGroup";
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
        setOrientation(HORIZONTAL);
        mScoreLineViewGroupParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mScoreLineViewGroupParams.weight = 1;
        mClef = Music.Clef.TREBLE_CLEF;
        // Temporary code: testing instance of ScoreLineView
        BarViewGroup barViewGroup = new BarViewGroup(getContext(), mClef);
        BarViewGroup barViewGroup2 = new BarViewGroup(getContext(), mClef);
        barViewGroup.setId(ViewCompat.generateViewId());
        barViewGroup2.setId(ViewCompat.generateViewId());
        barViewGroup.setLayoutParams(mScoreLineViewGroupParams);
        barViewGroup2.setLayoutParams(mScoreLineViewGroupParams);
        Note note = new Note(Music.PitchClass.C_NATURAL, 4, Music.NoteLength.QUARTER_NOTE);
        barViewGroup.addNoteToChildren(note);
        barViewGroup2.addNoteToChildren(note);
        this.addView(barViewGroup);
        this.addView(barViewGroup2);
    }
}