package com.example.composingapp.music;

import android.app.Application;

public class Music extends Application {

    public enum Staff {
        TREBLE_CLEF,
        BASS_CLEF;
    }

    public enum NoteLength {
        WHOLE_NOTE,
        HALF_NOTE,
        QUARTER_NOTE,
        EIGHTH_NOTE,
        SIXTEENTH_NOTE
    }

    public enum PitchClass {
        A_FLAT,
        A_NATURAL,
        A_SHARP,
        B_FLAT,
        B_NATURAL,
        B_SHARP,
        C_FLAT,
        C_NATURAL,
        C_SHARP,
        D_FLAT,
        D_NATURAL,
        D_SHARP,
        E_FLAT,
        E_NATURAL,
        E_SHARP,
        F_FLAT,
        F_NATURAL,
        F_SHARP,
        G_FLAT,
        G_NATURAL,
        G_SHARP;
    }
}
