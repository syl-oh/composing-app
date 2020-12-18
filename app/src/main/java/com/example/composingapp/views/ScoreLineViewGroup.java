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

import java.util.ArrayList;

public class ScoreLineViewGroup extends LinearLayout {
    private static final String TAG = "StaffViewGroup";
    private Music.NoteLength mBeatUnit;
    private LinearLayout.LayoutParams mStaffViewGroupParams;
    private int mBeatsPerBar;

    public ScoreLineViewGroup(Context context) {
        super(context);
        init();
    }

    public ScoreLineViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    void init() {
        mStaffViewGroupParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mStaffViewGroupParams.weight = 1;
        BarViewGroup barViewGroup = new BarViewGroup(getContext());
        BarViewGroup barViewGroup1 = new BarViewGroup(getContext());
        barViewGroup.setId(ViewCompat.generateViewId());
        barViewGroup1.setId(ViewCompat.generateViewId());
        barViewGroup.setLayoutParams(mStaffViewGroupParams);
        barViewGroup1.setLayoutParams(mStaffViewGroupParams);

        Log.d(TAG, "init: barViewGroup ID is " + barViewGroup.getId());
        Log.d(TAG, "init: barViewGroup1 ID is " + barViewGroup1.getId());

//        this.addView(barViewGroup);
//        this.addView(barViewGroup1);
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        Log.d(TAG, "drawChild: " + getChildCount());
        return super.drawChild(canvas, child, drawingTime);
    }
}