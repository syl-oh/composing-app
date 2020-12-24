package com.example.composingapp.utils.viewtools;

public final class ViewConstants {
    public static int TOTAL_LINES = 19; // Keep this < 20 to avoid Midi Numbers > 108
    public static int TOTAL_SPACES = TOTAL_LINES - 1;
    public static int STEM_WIDTH = 3;
    public static float NOTE_W_TO_H_RATIO = (float) (3.5 / 3);
    public static int BARLINE_SIZE = 2;
    public static int BARS_PER_LINE = 3;
    public static float FILLED_NOTE_ANGLE = 330;
    public static float HALF_NOTE_ANGLE_INSIDE = FILLED_NOTE_ANGLE;
    public static float WHOLE_NOTE_BASE_ANGLE = 0;
    public static float WHOLE_NOTE_INNER_ANGLE = 75;
    public static float QUARTER_REST_NOTE_X_DEVIANCE_FACTOR = (float) 0.05;
    public static float REST_STROKE_WIDTH = 2*STEM_WIDTH;

    /**
     * This class should not be instantiated
     */
    private ViewConstants() {}
}
