package com.example.composingapp.utils.music;

import android.app.Application;

import java.util.ArrayList;

import static com.example.composingapp.utils.music.Music.PitchClass.Letter.A;
import static com.example.composingapp.utils.music.Music.PitchClass.Letter.B;
import static com.example.composingapp.utils.music.Music.PitchClass.Letter.C;
import static com.example.composingapp.utils.music.Music.PitchClass.Letter.D;
import static com.example.composingapp.utils.music.Music.PitchClass.Letter.E;
import static com.example.composingapp.utils.music.Music.PitchClass.Letter.F;
import static com.example.composingapp.utils.music.Music.PitchClass.Letter.G;
import static com.example.composingapp.utils.music.Music.PitchClass.Letter.NONE;

public final class Music extends Application {
    /**
     * Types of clefs used in music
     */
    public enum Clef {
        TREBLE_CLEF(43, new Tone[]
                {ToneTable.get(Music.PitchClass.E_NATURAL, 4),
                        ToneTable.get(Music.PitchClass.G_NATURAL, 4),
                        ToneTable.get(Music.PitchClass.B_NATURAL, 4),
                        ToneTable.get(Music.PitchClass.D_NATURAL, 5),
                        ToneTable.get(Music.PitchClass.F_NATURAL, 5)}),
        BASS_CLEF(23, new Tone[]
                {ToneTable.get(Music.PitchClass.G_NATURAL, 2),
                        ToneTable.get(Music.PitchClass.B_NATURAL, 2),
                        ToneTable.get(Music.PitchClass.D_NATURAL, 3),
                        ToneTable.get(Music.PitchClass.F_NATURAL, 3),
                        ToneTable.get(Music.PitchClass.A_NATURAL, 3)});

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
        WHOLE_NOTE(1, false),
        HALF_NOTE((double) 1 / 2, false),
        QUARTER_NOTE((double) 1 / 4, false),
        EIGHTH_NOTE((double) 1 / 8, true),
        SIXTEENTH_NOTE((double) 1 / 16, true);

        // Value relative to whole note. Actual value does not matter - only its value
        //    relative to WHOLE_NOTE's
        private double valueToWholeNote;
        private boolean needsFlag;

        NoteLength(double valueToWholeNote, boolean needsFlag) {
            this.valueToWholeNote = valueToWholeNote;
            this.needsFlag = needsFlag;
        }

        public boolean needsFlag() {
            return needsFlag;
        }

        public double getValueToWholeNote() {
            return valueToWholeNote;
        }
    }

    /**
     * Pitch class (letter-name) of a note in music
     */
    public enum PitchClass {
        A_FLAT(A, Accidental.FLAT),
        A_NATURAL(A, Accidental.NATURAL),
        A_SHARP(A, Accidental.SHARP),
        B_FLAT(B, Accidental.FLAT),
        B_NATURAL(B, Accidental.NATURAL),
        B_SHARP(B, Accidental.SHARP),
        C_FLAT(C, Accidental.FLAT),
        C_NATURAL(C, Accidental.NATURAL),
        C_SHARP(C, Accidental.SHARP),
        D_FLAT(D, Accidental.FLAT),
        D_NATURAL(D, Accidental.NATURAL),
        D_SHARP(D, Accidental.SHARP),
        E_FLAT(E, Accidental.FLAT),
        E_NATURAL(E, Accidental.NATURAL),
        E_SHARP(E, Accidental.SHARP),
        F_FLAT(F, Accidental.FLAT),
        F_NATURAL(F, Accidental.NATURAL),
        F_SHARP(F, Accidental.SHARP),
        G_FLAT(G, Accidental.FLAT),
        G_NATURAL(G, Accidental.NATURAL),
        G_SHARP(G, Accidental.SHARP),
        REST(NONE, Accidental.NONE);

        private Letter letter;
        private Accidental accidental;

        PitchClass(Letter letter, Accidental accidental) {
            this.letter = letter;
            this.accidental = accidental;
        }

        public Letter getLetter() {
            return letter;
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
            NONE,
            NATURAL,
            SHARP,
            FLAT;
        }

        public enum Letter {
            A, B, C, D, E, F, G, NONE;
        }
    }
}
