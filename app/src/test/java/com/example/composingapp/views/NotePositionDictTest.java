package com.example.composingapp.views;

import com.example.composingapp.music.Music;
import com.example.composingapp.music.Note;
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
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
@DisplayName("When notePositionDict is created ")
class NotePositionDictTest {
    NotePositionDict notePositionDict;

    float barHeight;
    Music.Clef clef;

    @BeforeEach
    void init() {
        barHeight = 100;
        clef = Music.Clef.TREBLE_CLEF;
        notePositionDict = new NotePositionDict(barHeight, clef);
    }

    @Nested
    @DisplayName("toneToYMap")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class InitNoteYPositions {
        HashMap<Tone, Float> toneToYMap;

        @BeforeEach
        void prepareInitNoteYPositions() {
            toneToYMap = notePositionDict.getToneToYMap();
        }


        @DisplayName("should have accidental tones in the same position as unaccented ones")
        @ParameterizedTest
        @MethodSource("provideAccidentalTones")
        void testAccidentalTones(Tone unaccTone, Tone accTone) {
            assertEquals(toneToYMap.get(unaccTone), toneToYMap.get(accTone));
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
                    () -> assertEquals(0, Collections.min(toneToYMap.values())),
                    () -> assertEquals(barHeight, Collections.max(toneToYMap.values()))
            );
        }

        @DisplayName("should produce y-values for notes, since they are also tones")
        @Test
        void testNotesAsInput() {
            // C4 - Middle C is included in every staff
            Note middleCNote = new Note(Music.PitchClass.C_NATURAL, 4,
                    Music.NoteLength.QUARTER_NOTE);
            assertDoesNotThrow(() ->toneToYMap.get(middleCNote));
        }
    }

    @Nested
    @DisplayName("toneToBarlineMap")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class InitBarLineYPositions {

        @BeforeEach
        void prepareInitNoteYPositions() {
        }

        @DisplayName("should have barlines on the right tones for each clef")
        @ParameterizedTest
        @MethodSource("provideBarLineTones")
        void testCorrectTonesForClef(Music.Clef clef, Tone[] clefTones) {
            notePositionDict = new NotePositionDict(barHeight, clef);
            HashMap<Tone, Float> toneToBarlineYMap = notePositionDict.getToneToBarlineYMap();
            for (Tone tone : clefTones) {
                assertAll(
                        () -> assertTrue(toneToBarlineYMap.containsKey(tone))
                );
            }
        }


        /**
         * Provides arguments for testCorrectTonesForClef
         *
         * @return stream of arguments: (Music.Staff, Tone[])
         */
        Stream<Arguments> provideBarLineTones() {
            Tone[] trebleTones = {new Tone(Music.PitchClass.E_NATURAL, 4),
                    new Tone(Music.PitchClass.G_NATURAL, 4),
                    new Tone(Music.PitchClass.B_NATURAL, 4),
                    new Tone(Music.PitchClass.D_NATURAL, 5),
                    new Tone(Music.PitchClass.F_NATURAL, 5)};

            Tone[] bassTones = {new Tone(Music.PitchClass.G_NATURAL, 2),
                    new Tone(Music.PitchClass.B_NATURAL, 2),
                    new Tone(Music.PitchClass.D_NATURAL, 3),
                    new Tone(Music.PitchClass.F_NATURAL, 3),
                    new Tone(Music.PitchClass.A_NATURAL, 3)};
            return Stream.of(
                    Arguments.of(Music.Clef.TREBLE_CLEF, trebleTones),
                    Arguments.of(Music.Clef.BASS_CLEF, bassTones));
        }
    }
}
