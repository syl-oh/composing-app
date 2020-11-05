package com.example.composingapp.views;

import com.example.composingapp.music.MidiNoteDict;
import com.example.composingapp.music.Music;
import com.example.composingapp.music.Tone;

import java.util.Arrays;
import java.util.HashMap;

import static com.example.composingapp.views.ViewConstants.BASS_CLEF_MIDI_START;
import static com.example.composingapp.views.ViewConstants.TREBLE_CLEF_MIDI_START;

public class NotePositionDict {
    private static final String TAG = "NotePositionDict";
    private HashMap<Tone, Float> noteYPositions;
    private MidiNoteDict midiNoteDict;

    public NotePositionDict(float barHeight, Music.Staff clef) {
        midiNoteDict = new MidiNoteDict();
        noteYPositions = initNoteYPositions(barHeight, clef);
    }

    /**
     * Getter method for noteYPositions
     *
     * @return hashmap with tones as keys and the required Y position as float values
     */
    public HashMap<Tone, Float> getNoteYPositions() {
        return noteYPositions;
    }

    /**
     * Setter method for noteYPositions
     *
     * @param barHeight Height in pixels of the barView
     * @param clef      Enum from Music.class
     */
    public void setNoteYPositions(float barHeight, Music.Staff clef) {
        this.noteYPositions = initNoteYPositions(barHeight, clef);
    }

    /**
     * Initializes the mNoteYPositions array, which contains all permitted Y-coordinates for a note
     *
     * @param barHeight Height in pixels of the barView
     * @param clef      Enum from Music.class
     * @return Hashmap with the designated y positions for each of the 88 notes on a piano
     */
    private HashMap<Tone, Float> initNoteYPositions(float barHeight, Music.Staff clef) {
        final Music.PitchClass[] accPitchClasses = {Music.PitchClass.A_SHARP,
                Music.PitchClass.C_SHARP, Music.PitchClass.D_SHARP,
                Music.PitchClass.F_SHARP, Music.PitchClass.G_SHARP};

        int totalPositions = ViewConstants.TOTAL_SPACES + ViewConstants.TOTAL_LINES;
        int midiIndex = getStartingIndex(clef);
        float currentY;
        Tone currentTone;
        noteYPositions = new HashMap<>();

        for (int i = 0; i <= totalPositions; i++) {
            currentTone = midiNoteDict.getTone(midiIndex);
            currentY = ((barHeight * i) / totalPositions); //- (2 * ViewConstants.STEM_WIDTH);
            if (Arrays.asList(accPitchClasses).contains(currentTone.getPitchClass())) {
                noteYPositions.put(currentTone, currentY);
                i -= 1;
            } else {
                noteYPositions.put(currentTone, currentY);
            }
            midiIndex += 1;
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