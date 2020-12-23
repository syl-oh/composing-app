package com.example.composingapp.utils.music;

public class Note extends Tone {
    private Music.NoteLength noteLength;

    /**
     * Constructor for Note
     *
     * @param pitchClass From the Enum in Music.class
     * @param octave     int between 1 and 7 or -1, representing the Note's octave or a rest
     * @param noteLength From the Enum in Music.class
     */
    public Note(Music.PitchClass pitchClass, int octave, Music.NoteLength noteLength) {
        super(pitchClass, octave);
        this.noteLength = noteLength;
    }

    public Music.NoteLength getNoteLength() {
        return noteLength;
    }

    public Note setNoteLength(Music.NoteLength noteLength) {
        this.noteLength = noteLength;
        return this;
    }
}
