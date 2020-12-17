package com.example.composingapp.music;

import android.app.Application;

public final class Music extends Application {

    /**
     * Types of clefs used in music
     */
    public enum Clef {
        TREBLE_CLEF(43, new Tone[]
                {new Tone(Music.PitchClass.E_NATURAL, 4),
                        new Tone(Music.PitchClass.G_NATURAL, 4),
                        new Tone(Music.PitchClass.B_NATURAL, 4),
                        new Tone(Music.PitchClass.D_NATURAL, 5),
                        new Tone(Music.PitchClass.F_NATURAL, 5)}),
        BASS_CLEF(23, new Tone[]
                {new Tone(Music.PitchClass.G_NATURAL, 2),
                        new Tone(Music.PitchClass.B_NATURAL, 2),
                        new Tone(Music.PitchClass.D_NATURAL, 3),
                        new Tone(Music.PitchClass.F_NATURAL, 3),
                        new Tone(Music.PitchClass.A_NATURAL, 3)});

        private final Tone[] barlineTones;
        private final int midiStartingIndex;

        Clef(int midiStartingIndex, Tone[] barlineTones) {
            this.midiStartingIndex = midiStartingIndex;
            this.barlineTones = barlineTones;
        }

        public Tone[] getBarlineTones() {
            return barlineTones;
        }

        public int getMidiStartingIndex() {
            return midiStartingIndex;
        }
    }

    /**
     * Duration of a note in music
     */
    public enum NoteLength {
        WHOLE_NOTE,
        HALF_NOTE,
        QUARTER_NOTE,
        EIGHTH_NOTE,
        SIXTEENTH_NOTE
    }

    /**
     * Pitch class (letter-name) of a note in music
     */
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
        G_SHARP
    }
}
