package com.example.composingapp.utils.music;

import com.google.common.collect.ImmutableTable;

public class ToneTable {
    static ImmutableTable<Music.PitchClass, Integer, Tone> toneTable = buildToneTable();

    private ToneTable() { }

    private static ImmutableTable<Music.PitchClass, Integer, Tone> buildToneTable() {
        ImmutableTable.Builder<Music.PitchClass, Integer, Tone> builder = new ImmutableTable.Builder<>();
        int octave = 0;
        for (Music.PitchClass pitchClass : Music.PitchClass.values()) {
            if (pitchClass == Music.PitchClass.C_NATURAL) {
                octave++;
            }
            builder.put(pitchClass, octave, new Tone(pitchClass, octave));
        }
        return builder.build();
    }

    static Tone get(Music.PitchClass pitchClass, int octave) {
        return toneTable.get(pitchClass, octave);
    }
}
