package com.example.composingapp.views;

import com.example.composingapp.music.Music;
import com.example.composingapp.music.Tone;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("When notePositionDict is created ")
class NotePositionDictTest {
    NotePositionDict notePositionDict;
    HashMap<Tone, Float> dict;
    float barHeight;
    Music.Staff clef;

    @BeforeEach
    void init() {
        barHeight = 100;
        clef = Music.Staff.TREBLE_CLEF;
        notePositionDict = new NotePositionDict(barHeight, clef);
    }

    @Nested
    @DisplayName("initNoteYPositions() ")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class InitNoteYPositions {

        @BeforeEach
        void prepareInitNoteYPositions() {
            dict = notePositionDict.getNoteYPositions();
        }


        @DisplayName("should have accidental tones in the same position as unaccented ones")
        @ParameterizedTest
        @MethodSource("provideAccidentalTones")
        void testAccidentalTones(Tone unaccTone, Tone accTone) {
            assertEquals(dict.get(unaccTone), dict.get(accTone));
        }

        /**
         * Produces the arguments for testAccidentalTones()
         *
         * @return stream of arguments for testAccidentalTones()
         */
        Stream<Arguments> provideAccidentalTones() {
            Music.PitchClass[] unaccPitchClasses = {Music.PitchClass.A_NATURAL,
                    Music.PitchClass.C_NATURAL, Music.PitchClass.D_NATURAL,
                    Music.PitchClass.F_NATURAL, Music.PitchClass.G_NATURAL};
            Music.PitchClass[] accPitchClasses = {Music.PitchClass.A_SHARP,
                    Music.PitchClass.C_SHARP, Music.PitchClass.D_SHARP,
                    Music.PitchClass.F_SHARP, Music.PitchClass.G_SHARP};
            int octave = 4; // essential octave in any score (contains middle C)
            Arguments[] arguments = new Arguments[accPitchClasses.length];
            for (int i = 0; i < accPitchClasses.length; i++) {
                arguments[i] =
                        Arguments.of(new Tone(unaccPitchClasses[i], octave),
                                new Tone(accPitchClasses[i], octave));
            }
            return Arrays.stream(arguments);
        }


        @DisplayName("should be bounded within the bar height")
        @Test
        void testMinAndMaxOfNoteYPositions() {
            assertAll(
                    () -> assertEquals(0, Collections.min(dict.values())),
                    () -> assertEquals(barHeight, Collections.max(dict.values()))
            );
        }
    }


    @Nested
    @DisplayName("getStartingIndex() ")
    class getStartingIndex {

        @BeforeEach
        void prepareInitNoteYPositions() {

        }

        @DisplayName("should have the correct starting index")
        @Test
        void testGetStartingIndex() {
            assertAll(
                    () -> assertEquals(ViewConstants.TREBLE_CLEF_MIDI_START,
                            notePositionDict.getStartingIndex(Music.Staff.TREBLE_CLEF)),
                    () -> assertEquals(ViewConstants.BASS_CLEF_MIDI_START,
                            notePositionDict.getStartingIndex(Music.Staff.BASS_CLEF))
            );
        }
    }


}
