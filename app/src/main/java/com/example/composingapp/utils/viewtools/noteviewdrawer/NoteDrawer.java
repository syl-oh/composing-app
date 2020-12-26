package com.example.composingapp.utils.viewtools.noteviewdrawer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.composingapp.utils.interfaces.ComponentDrawer;
import com.example.composingapp.utils.interfaces.CompositeDrawer;
import com.example.composingapp.utils.music.Music;
import com.example.composingapp.utils.music.Note;
import com.example.composingapp.utils.viewtools.NotePositionDict;
import com.example.composingapp.utils.viewtools.noteviewdrawer.composites.RestComposite;

import java.util.ArrayList;

import static com.example.composingapp.utils.viewtools.ViewConstants.STEM_WIDTH;

public class NoteDrawer implements CompositeDrawer {
    private static final String TAG = "NoteDrawer";
    private Music.Clef mClef;
    private Note mNote;
    private NotePositionDict mNotePositionDict;
    private Paint mNotePaint;
    private ArrayList<ComponentDrawer> mDrawers;

    public NoteDrawer(NotePositionDict notePositionDict) {
        // Init fields
        mNotePositionDict = notePositionDict;
        mClef = notePositionDict.getClef();
        mNote = mNotePositionDict.getNote();
        initPaint();

        // Init drawers
        mDrawers = new ArrayList<>();
        add(new RestComposite(mNotePositionDict, mNotePaint));
    }

    /**
     * Initializes the Paint used for drawing
     */
    private void initPaint() {
        mNotePaint = new Paint();
        mNotePaint.setColor(Color.parseColor("black"));
        mNotePaint.setAntiAlias(true);
        mNotePaint.setDither(true);
        mNotePaint.setStrokeWidth(STEM_WIDTH);
        mNotePaint.setStrokeJoin(Paint.Join.ROUND);
        mNotePaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    public void draw(Canvas canvas) {
        mDrawers.forEach((drawer) -> drawer.draw(canvas));
    }

    @Override
    public void add(ComponentDrawer componentDrawer) {
        mDrawers.add(componentDrawer);
    }

    @Override
    public void remove(ComponentDrawer componentDrawer) {
        mDrawers.remove(componentDrawer);
    }

    @Override
    public ArrayList<ComponentDrawer> getDrawerComponents() {
        return mDrawers;
    }


}
