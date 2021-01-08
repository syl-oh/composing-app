package com.example.composingapp.utils.music;

import com.google.common.collect.ImmutableTable;

public class NoteTable {
    static ImmutableTable<Tone, Music.NoteLength, Note> noteTable = buildNoteTable();

    private NoteTable() { }

    private static ImmutableTable<Tone, Music.NoteLength, Note> buildNoteTable() {
        ImmutableTable.Builder<Tone, Music.NoteLength, Note> builder = new ImmutableTable.Builder<>();
        for (Tone tone : ToneTable.values()) {
            for (Music.NoteLength noteLength : Music.NoteLength.values()) {
                builder.put(tone, noteLength, new Note(tone.getPitchClass(), tone.getOctave(), noteLength));
            }
        }
        return builder.build();
    }

    public static Note get(Music.PitchClass pitchClass, int octave, Music.NoteLength noteLength) {
        return noteTable.get(ToneTable.get(pitchClass, octave), noteLength);
    }

    public static Note get(Tone tone, Music.NoteLength noteLength) {
        return noteTable.get(tone, noteLength);
    }

    public static Note get(Music.NoteLength noteLength) {
        return noteTable.get(ToneTable.get(Music.PitchClass.REST, -1), noteLength);
    }
}
