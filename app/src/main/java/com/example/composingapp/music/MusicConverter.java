package com.example.composingapp.music;

public final class MusicConverter {
    private static final String TAG = "MusicConverter";

    /**
     * This class should not be instantiated.
     */
    private MusicConverter() {
    }
    /**
     * Calculates the MIDI number of a pitch and accesses the noteDict to find the note name.
     * To find the MIDI number, n, based on the frequency, f, use the formula n = 69 + log2(f / 440)
     * More information can be found at https://newt.phys.unsw.edu.au/jw/notes.html
     *
     * @param pitchInHz The pitch of the incoming audio
     * @param duration  How long the note lasts (s)
     * @return A note object containing both the note's tone and rhythmic value.
     */
    public static Tone getToneFromHz(double pitchInHz, double duration) {
        // Calculate the midiNum using n = 69 + log2(f / 440)
        // Use the change of base law to take the log with base 2
        double midiNumDouble = 69 + (Math.log(pitchInHz / 440) / Math.log(2));
        // round the midi number double to the closest int
        int midiNum = (int) Math.round(midiNumDouble);

        // Create the dictionary to lookup the PitchClass
        MidiNoteDict midiNoteDict = new MidiNoteDict();
        return midiNoteDict.getTone(midiNum);
    }
}
