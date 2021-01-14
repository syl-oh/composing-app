package com.example.composingapp.utils.music;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableTable;

public final class ToneTable {
    private static final ImmutableTable<Music.PitchClass, Integer, Tone> toneTable = buildToneTable();


    private ToneTable() { }

    /**
     * Builds an ImmutableTable with keys Note and NoteLength to produce a Note value
     * @return ImmutableTable with keys Note and NoteLength to produce a Note value
     */
    private static ImmutableTable<Music.PitchClass, Integer, Tone> buildToneTable() {
        ImmutableTable.Builder<Music.PitchClass, Integer, Tone> builder = new ImmutableTable.Builder<>();
        for (Music.PitchClass pitchClass : Music.PitchClass.getValues()) {
            if (pitchClass != Music.PitchClass.REST) {
                for (int octave = 0; octave < 9; octave++) {
                    builder.put(pitchClass, octave, new Tone(pitchClass, octave));
                }
            }
        }
        // Manually add the Rest tone, since it is special
        builder.put(Music.PitchClass.REST, -1, new Tone(Music.PitchClass.REST, -1));
        return builder.build();
    }

    public static Tone get(Music.PitchClass pitchClass, int octave) {
        return toneTable.get(pitchClass, octave);
    }

    public static ImmutableCollection<Tone> values() {
        return toneTable.values();
    }
}
