package com.example.composingapp.views;

import com.example.composingapp.music.MidiNoteDict;
import com.example.composingapp.music.Music;
import com.example.composingapp.music.Tone;

import java.util.Arrays;
import java.util.HashMap;

public class NotePositionDict {
    private static final String TAG = "NotePositionDict";
    HashMap<Tone, Float> noteYPositions;

    public NotePositionDict(float barHeight, Music.Staff clef) {
        this.noteYPositions = initNoteYPositions(barHeight, clef);
    }

    /**
     * Initializes the mNoteYPositions array, which contains all permitted Y-coordinates for a note
     *
     * @param barHeight Height in pixels of the barView
     * @param clef      Enum from Music.class
     * @return Hashmap with the designated y positions for each of the 88 notes on a piano
     */
    public HashMap<Tone, Float> initNoteYPositions(float barHeight, Music.Staff clef) {
        final Music.PitchClass[] accPitchClasses = {Music.PitchClass.A_SHARP,
                Music.PitchClass.C_SHARP, Music.PitchClass.D_SHARP,
                Music.PitchClass.F_SHARP, Music.PitchClass.G_SHARP};
        int totalPositions = ViewConstants.TOTAL_SPACES + ViewConstants.TOTAL_LINES;
        MidiNoteDict midiNoteDict = new MidiNoteDict();
        int midiIndex = getStartingIndex(clef);
        float currentY;
        Tone currentTone;
        HashMap<Tone, Float> noteYPositions = new HashMap<>();

        for (int i = 0; i < totalPositions; i++) {
            midiIndex += 1;
            currentTone = midiNoteDict.getTone(midiIndex);
            currentY = ((barHeight * i) / totalPositions) - (2 * ViewConstants.STEM_WIDTH);
            if (Arrays.asList(accPitchClasses).contains(currentTone.getPitchClass())) {
                noteYPositions.put(currentTone, currentY);
                midiIndex += 1;
                i -= 1;
            } else {
                noteYPositions.put(currentTone, currentY);
            }
        }
        return noteYPositions;
    }

    /**
     * Produces the midi note number of the first note in the treble and bass clef
     *
     * @param clef the clef we're working with
     * @return the midi note number of the lowest permitted line of clef
     */
    int getStartingIndex(Music.Staff clef) {
        int midiStart = 0;
        int BASS_CLEF_MIDI_START = 23;
        int TREBLE_CLEF_MIDI_START = 43;
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
}
