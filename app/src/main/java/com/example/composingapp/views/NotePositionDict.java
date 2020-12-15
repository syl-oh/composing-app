package com.example.composingapp.views;

import com.example.composingapp.music.MidiNoteDict;
import com.example.composingapp.music.Music;
import com.example.composingapp.music.Tone;

import java.util.Arrays;
import java.util.HashMap;

import static com.example.composingapp.views.ViewConstants.BASS_CLEF_MIDI_START;
import static com.example.composingapp.views.ViewConstants.TREBLE_CLEF_MIDI_START;

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
     * Produces the midi note number of the first note in the treble and bass clef
     *
     * @param clef The clef we're working with
     * @return The midi note number of the lowest permitted line of clef
     */
    int getStartingIndex(Music.Staff clef) {
        int midiStart = 0;

        switch (clef) {
            case TREBLE_CLEF:
                midiStart = TREBLE_CLEF_MIDI_START; // Note: G2
                break;
            case BASS_CLEF:
                midiStart = BASS_CLEF_MIDI_START;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + clef);
        }
        return midiStart;
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
     * Getter method for toneToBarlineMap
     *
     * @return Hashmap with tones as keys and required Y position as float values
     */
    public HashMap<Tone, Float> getBarlineYToToneMap() {
        return toneToBarlineYMap;
    }


    /**
     * Initializes the yToToneMap and toneToYMap Hashmaps, which contains all permitted Y-coordinates
     * for a note
     *
     * @param barHeight Height in pixels of the barView
     * @param clef      Enum from Music.class
     * @return Hashmap with the designated y positions for each of the 88 notes on a piano
     */
    private HashMap<Float, Tone> initMaps(float barHeight, Music.Staff clef) {
        final Music.PitchClass[] accPitchClasses = {Music.PitchClass.A_SHARP,
                Music.PitchClass.C_SHARP, Music.PitchClass.D_SHARP,
                Music.PitchClass.F_SHARP, Music.PitchClass.G_SHARP};

        // Prepare for loop
        int totalPositions = ViewConstants.TOTAL_SPACES + ViewConstants.TOTAL_LINES;
        int midiIndex = getStartingIndex(clef);
        float currentY;
        Tone currentTone;
        yToToneMap = new HashMap<>();
        toneToYMap = new HashMap<>();

        for (int i = 0; i <= totalPositions; i++) {
            currentTone = midiNoteDict.getTone(midiIndex);
            if (Arrays.asList(accPitchClasses).contains(currentTone.getPitchClass())) {
                i -= 1;
            }
            currentY = ((barHeight * i) / totalPositions); //- (2 * ViewConstants.STEM_WIDTH);
            yToToneMap.put(currentY, currentTone);
            toneToYMap.put(currentTone, currentY);
            midiIndex += 1;
        }
        return yToToneMap;
    }

    /**
     * Initializes the barlineYToToneMap and toneToBarlineYMaps, cont
     *
     * @param clef
     */
    private void initBarlineMaps(Music.Staff clef) {
        barlineYToToneMap = new HashMap<>();
        toneToBarlineYMap = new HashMap<>();
        for (Tone tone : barLineTones(clef)) {
            toneToBarlineYMap.put(tone, toneToYMap.get(tone));
            barlineYToToneMap.put(toneToBarlineYMap.get(tone), tone);
        }
    }

    private Tone[] barLineTones(Music.Staff clef) {
        Tone[] tones;
        switch (clef) {
            case TREBLE_CLEF:
                tones = new Tone[]
                        {new Tone(Music.PitchClass.E_NATURAL, 4),
                                new Tone(Music.PitchClass.G_NATURAL, 4),
                                new Tone(Music.PitchClass.B_NATURAL, 4),
                                new Tone(Music.PitchClass.D_NATURAL, 5),
                                new Tone(Music.PitchClass.F_NATURAL, 5)};
                break;
            case BASS_CLEF:
                tones = new Tone[]
                        {new Tone(Music.PitchClass.G_NATURAL, 2),
                                new Tone(Music.PitchClass.B_NATURAL, 2),
                                new Tone(Music.PitchClass.D_NATURAL, 3),
                                new Tone(Music.PitchClass.F_NATURAL, 3),
                                new Tone(Music.PitchClass.A_NATURAL, 3)};
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + clef);
        }
        return tones;
    }


}
