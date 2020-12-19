package com.example.composingapp.utils;

import androidx.annotation.Nullable;

import com.example.composingapp.music.Tone;

import java.util.HashMap;

public class FloatToneHashMap extends HashMap<Float, Tone> {
    @Nullable
    @Override
    public Tone get(@Nullable Object key) {
        return super.get(closestKey((Float) key));
    }


    /**
     * Produces the key closest to the givenKey in the hashmap
     *
     * @param givenKey The key provided
     * @return The closest key in the hashmap to the given key
     */
    private Float closestKey(Float givenKey) {
        double minDiff = Double.MAX_VALUE;
        Float closestKey = null;
        for (Float currentKey : this.keySet()) {
            double currentDiff = Math.abs(givenKey - currentKey);
            if (currentDiff < minDiff) {
                closestKey = currentKey;
                minDiff = currentDiff;
            }
        }
        return closestKey;
    }
}
