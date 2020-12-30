package com.example.composingapp.views.viewtools;

public final class ViewConstants {
    public static final int TOTAL_LINES = 19; // Keep this < 20 to avoid Midi Numbers > 108
    public static final int TOTAL_SPACES = TOTAL_LINES - 1;
    public static final float STEM_WIDTH = 3;
    public static final float NOTE_W_TO_H_RATIO = (float) (3.5 / 3);
    public static final int BARLINE_SIZE = 2;
    public static final int BARS_PER_LINE = 3;


    /**
     * This class should not be instantiated
     */
    private ViewConstants() {}
}
