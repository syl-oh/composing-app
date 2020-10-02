package com.example.composingapp.music;

public class Tone {
    private Music.PitchClass pitchClass;
    private int octave;

    public Tone(Music.PitchClass pitchClass, int octave) {
        this.pitchClass = pitchClass;
        this.octave = octave;
    }

    public Music.PitchClass getPitchClass() {
        return pitchClass;
    }

    public Tone setPitchClass(Music.PitchClass pitchClass) {
        this.pitchClass = pitchClass;
        return this;
    }

    public int getOctave() {
        return octave;
    }

    public Tone setOctave(int octave) {
        this.octave = octave;
        return this;
    }
}
