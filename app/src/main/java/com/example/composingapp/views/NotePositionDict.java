package com.example.composingapp.views;

import android.util.Log;

import com.example.composingapp.music.MidiNoteDict;
import com.example.composingapp.music.Music;
import com.example.composingapp.music.Tone;

import java.util.Arrays;
import java.util.HashMap;

public class NotePositionDict {
    private static final int NUM_BARLINES = 5;
    private static final String TAG = "NotePositionDict";
    private HashMap<Float, Tone> yToToneMap;
    private HashMap<Tone, Float> toneToYMap;
    private HashMap<Float, Tone> barlineYToToneMap;
    private HashMap<Tone, Float> toneToBarlineYMap;
    private MidiNoteDict midiNoteDict;

    public NotePositionDict(float barHeight, Music.Staff clef) {
        midiNoteDict = new MidiNoteDict();
        initMaps(barHeight, clef);
        initBarlineMaps(clef);
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
     * Getter method for noteYPositions
     *
     * @return Hashmap with y positions as keys and tones as values
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


    /**
     * Initializes the yToToneMap and toneToYMap Hashmaps, which contains all permitted Y-coordinates
     * for a note
     *
     * @param barHeight Height in pixels of the barView
     * @param clef      Enum from Music.class
     */
    private void initMaps(float barHeight, Music.Staff clef) {
        final Music.PitchClass[] accPitchClasses = {Music.PitchClass.A_SHARP,
                Music.PitchClass.C_SHARP, Music.PitchClass.D_SHARP,
                Music.PitchClass.F_SHARP, Music.PitchClass.G_SHARP};

        // Prepare for loop
        int totalPositions = ViewConstants.TOTAL_SPACES + ViewConstants.TOTAL_LINES;
        int midiIndex = clef.getMidiStartingIndex();
        float currentY = 0;
        Tone currentTone = null;
        yToToneMap = new HashMap<>();
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
            currentY = ((barHeight * i) / totalPositions); //- (2 * ViewConstants.STEM_WIDTH);
//            Log.d(TAG, "initMaps: for pitchclass "+ currentTone.getPitchClass() + " with " +
//                    "octave " + currentTone.getOctave() + " the y-pos is " + currentY);
            yToToneMap.put(currentY, currentTone);
            toneToYMap.put(currentTone, currentY);
            midiIndex += 1;
        }
    }

    /**
     * Initializes the barlineYToToneMap and toneToBarlineYMaps, cont
     *
     * @param clef
     */
    private void initBarlineMaps(Music.Staff clef) {
        barlineYToToneMap = new HashMap<>();
        toneToBarlineYMap = new HashMap<>();
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
}
