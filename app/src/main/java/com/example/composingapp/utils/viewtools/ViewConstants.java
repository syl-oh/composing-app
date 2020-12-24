package com.example.composingapp.utils.viewtools;

public final class ViewConstants {
    public static int TOTAL_LINES = 19; // Keep this < 20 to avoid Midi Numbers > 108
    public static int TOTAL_SPACES = TOTAL_LINES - 1;
    public static int STEM_WIDTH = 3;
    public static float NOTE_W_TO_H_RATIO = (float) (3.5 / 3);
    public static int BARLINE_SIZE = 6;
    public static int BARS_PER_LINE = 3;
    /**
     * This class should not be instantiated
     */
    private ViewConstants() {}
}
