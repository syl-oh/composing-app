package musicconstants;

public class Note extends Tone {
    private NoteLength noteLength;

    public Note(PitchClass pitchClass, int octave, NoteLength noteLength) {
        super(pitchClass, octave);
        this.noteLength = noteLength;
    }

    public NoteLength getNoteLength() {
        return noteLength;
    }

    public Note setNoteLength(NoteLength noteLength) {
        this.noteLength = noteLength;
        return this;
    }
}
