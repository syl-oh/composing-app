package com.example.composingapp.utils.music;


public class Tone {
    private final Music.PitchClass pitchClass;
    private final int octave;

    /**
     * Constructs tone object
     *
     * @param pitchClass From the Enum in Music.class
     * @param octave     int between 0 and 7, or -1, representing the tone's octave or a rest,
     *                   respectively
     */
    Tone(Music.PitchClass pitchClass, int octave) {
        this.pitchClass = pitchClass;
        this.octave = octave;
    }

    /**
     * Getter method for the tone's pitch class
     *
     * @return PitchClass of the tone
     */
    public Music.PitchClass getPitchClass() {
        return pitchClass;
    }

    /**
     * Getter method for the tone's octave
     *
     * @return int between 1 and 7, or -1, representing the current octave of the tone, or a rest
     */
    public int getOctave() {
        return octave;
    }
}
