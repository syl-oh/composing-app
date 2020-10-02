package com.example.composingapp.music;

public class Note extends Tone {
    private Music.NoteLength noteLength;

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
