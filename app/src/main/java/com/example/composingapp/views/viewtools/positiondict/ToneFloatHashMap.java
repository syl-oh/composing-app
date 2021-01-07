package com.example.composingapp.views.viewtools.positiondict;

import androidx.annotation.Nullable;

import com.example.composingapp.utils.music.Music;
import com.example.composingapp.utils.music.Tone;

import java.util.HashMap;
import java.util.Objects;

/**
 * Hashmap that bases its search for the key with an equivalent Letter
 */
public class ToneFloatHashMap extends HashMap<Tone, Float> {
    @Override
    public Float get(@Nullable Object key) {
        return super.get(
                new Tone(findNaturalPitchClass(((Tone) Objects.requireNonNull(key)).getPitchClass()), ((Tone) key).getOctave()));
    }

    private Music.PitchClass findNaturalPitchClass(Music.PitchClass pitchClass) {
        Music.PitchClass naturalPitchClass = pitchClass;
        for (Music.PitchClass currentPitchClass : Music.PitchClass.values()) {
            if (currentPitchClass.getLetter() == pitchClass.getLetter() &&
                    currentPitchClass.getAccidental() == Music.PitchClass.Accidental.NATURAL) {
                naturalPitchClass = currentPitchClass;
                break;
            }
        }
        return naturalPitchClass;
    }
}