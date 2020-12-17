package com.example.composingapp.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.core.view.ViewCompat;

import com.example.composingapp.music.Music;

import java.util.ArrayList;

public class StaffViewGroup extends LinearLayout {
    private Music.NoteLength mBeatUnit;
    private int mBeatsPerBar;
    private ArrayList<BarViewGroup> mBarViewGroupList;
    private Music.Clef mClef;
    private LayoutParams mStaffViewGroupParams;

    public StaffViewGroup(Context context) {
        super(context);
        init();
    }

    public StaffViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    void init() {
        setWillNotDraw(false); // Enable drawing of the ViewGroup
        setOrientation(LinearLayout.HORIZONTAL);
        mStaffViewGroupParams = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        mClef = Music.Clef.TREBLE_CLEF;
        mBarViewGroupList = new ArrayList<>();


        // TODO: 17/12/20: Programmatically add BarViewGroups
        BarViewGroup barViewGroup = new BarViewGroup(getContext(), mClef);
        addBarViewGroupToChildren(barViewGroup);

    }

    /**
     * Adds a BarViewGroup as a child to this BarViewGroup
     */
    private void addBarViewGroupToChildren(BarViewGroup barViewGroup) {
        barViewGroup.setId(ViewCompat.generateViewId());
        barViewGroup.setLayoutParams(mStaffViewGroupParams);
        this.addView(barViewGroup);
        mBarViewGroupList.add(barViewGroup);
    }
}