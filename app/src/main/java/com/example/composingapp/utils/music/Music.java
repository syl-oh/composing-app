package com.example.composingapp.utils.music;

import android.app.Application;

import java.util.ArrayList;

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
        WHOLE_NOTE(1),
        HALF_NOTE(1 / 2),
        QUARTER_NOTE(1 / 4),
        EIGHTH_NOTE(1 / 8),
        SIXTEENTH_NOTE(1 / 16);

        // Value relative to whole note. Actual value does not matter - only its value
        //    relative to WHOLE_NOTE's
        private double valueToWholeNote;

        NoteLength(double valueToWholeNote) {
            this.valueToWholeNote = valueToWholeNote;
        }

        public double getValueToWholeNote() {
            return valueToWholeNote;
        }
    }

    /**
     * Pitch class (letter-name) of a note in music
     */
    public enum PitchClass {
        A_FLAT(Accidental.FLAT),
        A_NATURAL(Accidental.NATURAL),
        A_SHARP(Accidental.SHARP),
        B_FLAT(Accidental.FLAT),
        B_NATURAL(Accidental.NATURAL),
        B_SHARP(Accidental.SHARP),
        C_FLAT(Accidental.FLAT),
        C_NATURAL(Accidental.NATURAL),
        C_SHARP(Accidental.SHARP),
        D_FLAT(Accidental.FLAT),
        D_NATURAL(Accidental.NATURAL),
        D_SHARP(Accidental.SHARP),
        E_FLAT(Accidental.FLAT),
        E_NATURAL(Accidental.NATURAL),
        E_SHARP(Accidental.SHARP),
        F_FLAT(Accidental.FLAT),
        F_NATURAL(Accidental.NATURAL),
        F_SHARP(Accidental.SHARP),
        G_FLAT(Accidental.FLAT),
        G_NATURAL(Accidental.NATURAL),
        G_SHARP(Accidental.SHARP);

        private Accidental accidental;

        PitchClass(Accidental accidental) {
            this.accidental = accidental;
        }

        public Accidental getAccidental() {
            return accidental;
        }

        public PitchClass[] getAccentedPitchClasses() {
            ArrayList<PitchClass> accentedPitchClasses = new ArrayList<>();
            for (PitchClass pitchClass : PitchClass.values()) {
                if (pitchClass.accidental != Accidental.NATURAL) {
                    accentedPitchClasses.add(pitchClass);
                }
            }
            return (PitchClass[]) accentedPitchClasses.toArray();
        }

        public enum Accidental {
            NATURAL,
            SHARP,
            FLAT;
        }
    }

}
