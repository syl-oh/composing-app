package musicconstants;

public class Tone {
    private PitchClass pitchClass;
    private int octave;

    public Tone(PitchClass pitchClass, int octave) {
        this.pitchClass = pitchClass;
        this.octave = octave;
    }

    public PitchClass getPitchClass() {
        return pitchClass;
    }

    public Tone setPitchClass(PitchClass pitchClass) {
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
