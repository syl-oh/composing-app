package com.example.composingapp.music;

import androidx.annotation.Nullable;

import java.util.Objects;

public class Tone {
    private final Music.PitchClass pitchClass;
    private final int octave;

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
     * Getter method for the tone's octave
     *
     * @return int between 1 and 7, representing the current octave of the tone
     */
    public int getOctave() {
        return octave;
    }


    @Override
    public int hashCode() {
        return Objects.hash(pitchClass, octave);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Tone)) {
            return false;
        }
        Tone tone = (Tone) obj;
        return ((pitchClass == tone.getPitchClass())
                && (octave == tone.getOctave()));
    }
}
