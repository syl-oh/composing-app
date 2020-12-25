package com.example.composingapp.utils.viewtools;

import android.util.Log;

import androidx.annotation.Nullable;

import com.example.composingapp.utils.music.MidiNoteDict;
import com.example.composingapp.utils.music.Music;
import com.example.composingapp.utils.music.Note;
import com.example.composingapp.utils.music.Tone;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;

public class PositionDict {
    private static final int NUM_BARLINES = 5;
    private static final String TAG = "NotePositionDict";
    private final int totalPositions = ViewConstants.TOTAL_SPACES + ViewConstants.TOTAL_LINES;
    private FloatToneHashMap yToToneMap;
    private HashMap<Tone, Float> toneToYMap;
    private FloatToneHashMap barlineYToToneMap;
    private HashMap<Tone, Float> toneToBarlineYMap;
    private MidiNoteDict midiNoteDict;
    private Float mBarHeight;
    private Music.Clef mClef;

    public PositionDict(float barHeight, Music.Clef clef) {
        mBarHeight = barHeight;
        mClef = clef;
        midiNoteDict = new MidiNoteDict();
        initMaps(barHeight, clef);
        initBarlineMaps(clef);
    }

    public Music.Clef getClef() {
        return mClef;
    }

    /**
     * Getter method for toneToBarlineYMap
     *
     * @return Hasmap with tones as keys and y positions of barlines as values
     */
    public HashMap<Tone, Float> getToneToBarlineYMap() {
        return toneToBarlineYMap;
    }

    /**
     * Getter method for toneToYMap
     *
     * @return Hashmap with tones as keys and y positions as values
     */
    public HashMap<Tone, Float> getToneToYMap() {
        return toneToYMap;
    }


    /**
     * Produces the space between two consecutive bar lines (the height of a single space)
     *
     * @return Height of a single space in Px
     */
    public Float getSingleSpaceHeight() {
        return 2 * mBarHeight / totalPositions;
    }

    /**
     * Produces the space between two of the same pitchclasses, an octave apart
     *
     * @return Height of an octave in Px
     */
    public Float getOctaveHeight() {
        return (getSingleSpaceHeight() * 4);
    }

    /**
     * Getter method for yToToneMap
     *
     * @return Hashmap with y positions as keys and natural tones as values
     */
    public HashMap<Float, Tone> getYToToneMap() {
        return yToToneMap;
    }

    /**
     * Getter method for barlineYToToneMap
     *
     * @return Hashmap with tones as keys and required Y position as float values
     */
    public HashMap<Float, Tone> getBarlineYToToneMap() {
        return barlineYToToneMap;
    }

    public Float getNoteYOf(Note note) {
        Float yPos = null;
        try {
            yPos = getToneToYMap().get(note);
        } catch (NullPointerException e) {
            Log.e(TAG, "onSizeChanged: NullPointerException, unable to retrieve y-position " +
                    "of pitchclass " + note.getPitchClass() + " and octave "
                    + note.getOctave());
        }
        return yPos;
    }

    /**
     * Initializes the yToToneMap and toneToYMap Hashmaps, which contains all permitted Y-coordinates
     * for a note
     *
     * @param barHeight Height in pixels of the barView
     * @param clef      Enum from Music.class
     */
    private void initMaps(float barHeight, @NotNull Music.Clef clef) {
        final Music.PitchClass[] accPitchClasses = {Music.PitchClass.A_SHARP,
                Music.PitchClass.C_SHARP, Music.PitchClass.D_SHARP,
                Music.PitchClass.F_SHARP, Music.PitchClass.G_SHARP};

        // Prepare for loop
        int midiIndex = clef.getMidiStartingIndex();
        float currentY;
        Tone currentTone = null;
        yToToneMap = new FloatToneHashMap();
        toneToYMap = new HashMap<>();

        for (int i = 0; i <= totalPositions; i++) {
            try {
                currentTone = midiNoteDict.getTone(midiIndex);
            } catch (NullPointerException e) {
                Log.e(TAG, "initMaps: NullPointerException, could not retrieve tone at " +
                        "midi index " + midiIndex + " from midiNoteDict");
            }

            if (Arrays.asList(accPitchClasses).contains(currentTone.getPitchClass())) {
                i -= 1;
            }
            currentY = barHeight - ((barHeight * i) / totalPositions); // build from bottom to top
//            Log.d(TAG, "initMaps: for pitchclass " + currentTone.getPitchClass() + " with " +
//                    "octave " + currentTone.getOctave() + " the y-pos is " + currentY);
            if (currentTone.getPitchClass().getAccidental() == Music.PitchClass.Accidental.NATURAL) {
                yToToneMap.put(currentY, currentTone);
            }

            toneToYMap.put(currentTone, currentY);
            midiIndex += 1;
            // TODO: 23/12/2020: Prevent midiIndex from exceeding 108
        }
    }

    /**
     * Initializes the barlineYToToneMap and toneToBarlineYMaps, cont
     *
     * @param clef
     */
    private void initBarlineMaps(@NotNull Music.Clef clef) {
        barlineYToToneMap = new FloatToneHashMap();
        toneToBarlineYMap = new HashMap();
        for (Tone tone : clef.getBarlineTones()) {
            try {
                Float toneY = toneToYMap.get(tone);
                toneToBarlineYMap.put(tone, toneY);
                barlineYToToneMap.put(toneToBarlineYMap.get(tone), tone);
            } catch (NullPointerException e) {
                Log.e(TAG, "initBarlineMaps: NullPointerException, could not retrieve " +
                        "y position from toneToYMap for tone with pitchclass " + tone.getPitchClass()
                        + " and octave" + tone.getOctave());
            }
        }
    }

    /**
     * Hashmap that produces the value of the closest key
     */
    class FloatToneHashMap extends HashMap<Float, Tone> {
        @Nullable
        @Override
        public Tone get(@Nullable Object key) {
            return super.get(closestKey((Float) key));
        }


        /**
         * Produces the key closest to the givenKey in the hashmap
         *
         * @param givenKey The key provided
         * @return The closest key in the hashmap to the given key
         */
        private Float closestKey(Float givenKey) {
            double minDiff = Double.MAX_VALUE;
            Float closestKey = null;
            for (Float currentKey : this.keySet()) {
                double currentDiff = Math.abs(givenKey - currentKey);
                if (currentDiff < minDiff) {
                    closestKey = currentKey;
                    minDiff = currentDiff;
                }
            }
            return closestKey;
        }
    }
}
