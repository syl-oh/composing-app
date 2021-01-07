package com.example.composingapp.utils.music;

import java.util.HashMap;
import java.util.Objects;

public class MidiNoteDict {
    private static MidiNoteDict INSTANCE;
    private final int startingMidiNum = 21;   // the first key on an 88-key piano
    private final int endingMidiNum = 108;    // the last key on an 88-key piano
    private HashMap<Integer, Tone> midiNumToToneMap;
    private HashMap<Tone, Integer> toneToMidiNumMap;

    /**
     * Constructs MidiNoteDict. Immediately initializes the dictionary
     */
    private MidiNoteDict() {
        buildMidiNoteDict();
    }

    public static MidiNoteDict getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MidiNoteDict();
        }
        return INSTANCE;
    }

    /**
     * Builds the midi note dictionary
     */
    private void buildMidiNoteDict() {
        // The dictionary is built only using sharps and naturals, ignoring enharmonic equivalents
        // Create an array ofMusic.PitchClass constants to iterate over so we can build midiNoteDict
        // The 1st element in pitchClasses must match the pitch class of the startingMidiNum
        final Music.PitchClass[] pitchClasses = {
                Music.PitchClass.A_NATURAL, Music.PitchClass.A_SHARP, Music.PitchClass.B_NATURAL, Music.PitchClass.C_NATURAL,
                Music.PitchClass.C_SHARP, Music.PitchClass.D_NATURAL, Music.PitchClass.D_SHARP, Music.PitchClass.E_NATURAL,
                Music.PitchClass.F_NATURAL, Music.PitchClass.F_SHARP, Music.PitchClass.G_NATURAL, Music.PitchClass.G_SHARP};

        midiNumToToneMap = new HashMap<>();  // initialize the dictionary
        toneToMidiNumMap = new HashMap<>();

        // Prepare for the loop:
        int currentPitchClassIdx = 0; // Index variable points to the current pitch class in pitchClasses
        Music.PitchClass currentPitchClass;   // variable to store the current pitch class
        int currentOctave = 0; // Variable to hold the current octave in which we are in

        for (int midiNum = startingMidiNum; midiNum <= endingMidiNum; midiNum++) {
            currentPitchClass = pitchClasses[currentPitchClassIdx]; // get the current pitch class
            // Anytime we reach the pitch class C natural, we have moved up an octave
            if (currentPitchClass == Music.PitchClass.C_NATURAL) {
                currentOctave++;
            }

            // Use the midi number as the key, and a tone object containing both the pitch class and octave
            Tone toneToAdd = new Tone(currentPitchClass, currentOctave);
            midiNumToToneMap.put(midiNum, toneToAdd);
            toneToMidiNumMap.put(toneToAdd, midiNum);

            // Move to the next pitch class in the pitchClasses array
            currentPitchClassIdx++;
            // if we have moved the index out of the array, reset it back to 0
            if (currentPitchClassIdx > pitchClasses.length - 1) {
                currentPitchClassIdx = 0;
            }
        }
    }

    /**
     * Produces the midi number of specified Tone
     *
     * @param tone Tone request
     * @return int representing the midi number of the given tone
     */
    public int getMidiNum(Tone tone) {
        Music.PitchClass tonePitchClass = tone.getPitchClass();

        // Hashmap only contains values for Sharps and Naturals, so we need to convert any Flats
        if (tonePitchClass.getAccidental() == Music.PitchClass.Accidental.FLAT) {
            for (Music.PitchClass pitchClass : Music.PitchClass.values()) {
                if (pitchClass.getLetter() == tonePitchClass.getLetter() &&
                        pitchClass.getAccidental() == Music.PitchClass.Accidental.NATURAL) {
                    tone = new Tone(pitchClass, tone.getOctave());
                }
            }
        }
        return toneToMidiNumMap.get(tone);
    }

    /**
     * Produces the tone at a specified midi number
     *
     * @param midiNum The midi number requested
     * @return Tone object in the dictionary with the key midiNum
     */
    public Tone getTone(int midiNum) {
        return Objects.requireNonNull(midiNumToToneMap.get(midiNum));
    }

    /**
     * Produces the pitch class of the tone at a specified midi number
     *
     * @param midiNum The midi number requested
     * @return PitchClass of the tone in the dictionary with the key midiNum
     */
    public Music.PitchClass getPitchClass(int midiNum) {
        return getTone(midiNum).getPitchClass();
    }

    /**
     * Produces the octave of the tone at a specified midi number
     *
     * @param midiNum The midi number requested
     * @return int between 1 and 7, representing the octave of the tone in the dictionary with the
     * key midiNum
     */
    public int getOctave(int midiNum) {
        return getTone(midiNum).getOctave();
    }


    /**
     * Produces the first midi num of midiNoteDict
     *
     * @return int representing the starting midi number
     */
    public int getStartingMidiNum() {
        return startingMidiNum;
    }

    /**
     * Produces the last midi num of midiNoteDict
     *
     * @return int representing the ending midi number
     */
    public int getEndingMidiNum() {
        return endingMidiNum;
    }
}
