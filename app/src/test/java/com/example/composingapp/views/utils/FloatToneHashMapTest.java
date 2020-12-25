package com.example.composingapp.views.utils;

import com.example.composingapp.utils.music.Music;
import com.example.composingapp.utils.music.Tone;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("When FloatToneHashMap is created ")
class FloatToneHashMapTest {
    FloatToneHashMap floatToneHashMap;
    @BeforeEach
    void setUp() {
        floatToneHashMap = new FloatToneHashMap();
    }


    @Nested
    @DisplayName("get")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class get {
        @Test
        @DisplayName("should retrieve the tone whose key is closest to the given float key")
        void testGet() {
            Tone c4Tone = new Tone(Music.PitchClass.C_NATURAL, 4);
            Tone d4Tone = new Tone(Music.PitchClass.D_NATURAL, 4);
            Tone e4Tone = new Tone(Music.PitchClass.E_NATURAL, 4);
            Float c4ToneKey = 0f;
            Float d4ToneKey = 50f;
            Float e4ToneKey = 100f;
            floatToneHashMap.put(c4ToneKey, c4Tone);
            floatToneHashMap.put(d4ToneKey, d4Tone);
            floatToneHashMap.put(e4ToneKey, e4Tone);

            assertAll(
                    () -> assertEquals(floatToneHashMap.get(-1f), c4Tone),
                    () -> assertEquals(floatToneHashMap.get(0f), c4Tone),
                    () -> assertEquals(floatToneHashMap.get(1f), c4Tone),
                    () -> assertEquals(floatToneHashMap.get(24f), c4Tone),
                    () -> assertEquals(floatToneHashMap.get(25f), c4Tone),

                    () -> assertEquals(floatToneHashMap.get(26f), d4Tone),
                    () -> assertEquals(floatToneHashMap.get(49f), d4Tone),
                    () -> assertEquals(floatToneHashMap.get(50f), d4Tone),
                    () -> assertEquals(floatToneHashMap.get(51f), d4Tone),
                    () -> assertEquals(floatToneHashMap.get(74f), d4Tone),
                    () -> assertEquals(floatToneHashMap.get(75f), d4Tone),

                    () -> assertEquals(floatToneHashMap.get(76f), e4Tone),
                    () -> assertEquals(floatToneHashMap.get(99f), e4Tone),
                    () -> assertEquals(floatToneHashMap.get(100f), e4Tone),
                    () -> assertEquals(floatToneHashMap.get(101f), e4Tone)
            );

        }
    }
}