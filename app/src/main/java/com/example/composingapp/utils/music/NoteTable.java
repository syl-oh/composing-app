package com.example.composingapp.utils.music;

import com.google.common.collect.ImmutableTable;

public class NoteTable {
    static ImmutableTable<Tone, Music.NoteLength, Note> noteTable = buildNoteTable();

    private NoteTable() { }

    private static ImmutableTable<Tone, Music.NoteLength, Note> buildNoteTable() {
        ImmutableTable.Builder<Tone, Music.NoteLength, Note> builder = new ImmutableTable.Builder<>();
        for (Tone tone : ToneTable.toneTable.values()) {
            for (Music.NoteLength noteLength : Music.NoteLength.values()) {
                builder.put(tone, noteLength, new Note(tone.getPitchClass(), tone.getOctave(), noteLength));
            }
        }
        return builder.build();
    }

    static Note get(Music.PitchClass pitchClass, int octave, Music.NoteLength noteLength) {
        return noteTable.get(ToneTable.get(pitchClass, octave), noteLength);
    }

    static Note get(Tone tone, Music.NoteLength noteLength) {
        return noteTable.get(tone, noteLength);
    }
}
