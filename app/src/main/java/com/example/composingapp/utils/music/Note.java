package com.example.composingapp.utils.music;

public class Note extends Tone {
    private final Music.NoteLength noteLength;

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

    /**
     * Constructor for Rest
     *
     * @param noteLength From the Enum in Music.class
     */
    public Note(Music.NoteLength noteLength) {
        super(Music.PitchClass.REST, -1);
        this.noteLength = noteLength;
    }

    public Music.NoteLength getNoteLength() {
        return noteLength;
    }

}
