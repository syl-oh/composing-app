package com.example.composingapp.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.example.composingapp.utils.music.Music;
import com.example.composingapp.utils.music.Note;

import static com.example.composingapp.utils.viewtools.ViewConstants.BARS_PER_LINE;

public class ScoreLineView extends LinearLayout {
    private static final String TAG = "ScoreViewGroup";
    private Music.NoteLength mBeatUnit;
    private int mBeatsPerBar;
    private Music.Clef mClef;
    private LinearLayout.LayoutParams mScoreLineViewLayout;

    public ScoreLineView(Context context) {
        super(context);
        init();
    }

    public ScoreLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    void init() {
        setOrientation(LinearLayout.HORIZONTAL);
        mScoreLineViewLayout = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT / BARS_PER_LINE,
                LayoutParams.WRAP_CONTENT);
        mScoreLineViewLayout.weight = 1;
        mClef = Music.Clef.TREBLE_CLEF;

        BarViewGroup barViewGroup = new BarViewGroup(getContext(), mClef);
        barViewGroup.setId(generateViewId());
        barViewGroup.setLayoutParams(mScoreLineViewLayout);

        Note note = new Note(Music.PitchClass.C_NATURAL, 4, Music.NoteLength.QUARTER_NOTE);
        barViewGroup.addNoteToChildren(note);
        this.addView(barViewGroup);
    }
}