package com.example.composingapp.utils.music;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.example.composingapp.utils.music.Music.PitchClass.A_NATURAL;
import static com.example.composingapp.utils.music.Music.PitchClass.C_NATURAL;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("When midiNoteDict is created ")
class MidiNoteDictTest {
    MidiNoteDict dict;

    private static Stream<Arguments> provideOctavesForGetOctave() {
        return Stream.of(
                Arguments.of(24, 0),  // C_NATURAL's midi num, octave
                Arguments.of(36, 1),
                Arguments.of(48, 2),
                Arguments.of(60, 3),
                Arguments.of(72, 4),
                Arguments.of(84, 5),
                Arguments.of(96, 6),
                Arguments.of(108, 7)
        );
    }

    @BeforeEach
    void init() {
        dict = new MidiNoteDict();
    }

    @DisplayName(" the pitch classes should all be correct")
    @Test
    void getPitchClass() {
        assertAll(
                () -> assertEquals(dict.getPitchClass(21), A_NATURAL),
                () -> assertEquals(dict.getPitchClass(60), C_NATURAL),
                () -> assertEquals(dict.getPitchClass(108), C_NATURAL)
        );
    }

    @DisplayName(" the octaves should all be correct")
    @ParameterizedTest
    @MethodSource("provideOctavesForGetOctave")
    void getOctave(int midiNumOfCNatural, int cNaturalOctave) {
        assertEquals(cNaturalOctave, dict.getOctave(midiNumOfCNatural));
    }
}