package com.example.composingapp.views;

public final class ViewConstants {
    private static final String TAG = "ViewConstants";
    static int TOTAL_LINES = 17;
    static int TOTAL_SPACES = TOTAL_LINES - 1;
    static int STEM_WIDTH = 3;
    static float NOTE_W_TO_H_RATIO = (float) (4.0 / 3.0);
    static int BASS_CLEF_MIDI_START = 23;
    static int TREBLE_CLEF_MIDI_START = 43;

    /**
     * This class should not be instantiated
     */
    private ViewConstants() {
    }
}
