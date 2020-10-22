package com.example.composingapp.music;

public class Tone {
    private Music.PitchClass pitchClass;
    private int octave;

    /**
     * Constructs tone object
     *
     * @param pitchClass From the Enum in Music.class
     * @param octave     int between 1 and 7, representing the tone's octave
     */
    public Tone(Music.PitchClass pitchClass, int octave) {
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
     * Setter method for the tone's pitch class
     *
     * @param pitchClass PitchClass to set
     */
    public void setPitchClass(Music.PitchClass pitchClass) {
        this.pitchClass = pitchClass;
    }

    /**
     * Getter method for the tone's octave
     *
     * @return int between 1 and 7, representing the current octave of the tone
     */
    public int getOctave() {
        return octave;
    }

    /**
     * Setter method for the tone's octave
     *
     * @param octave int between 1 and 7, representing the octave to set
     */
    public void setOctave(int octave) {
        this.octave = octave;
    }
}
