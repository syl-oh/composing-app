package com.example.composingapp.utils.music;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("When a BarObserver is created")
class BarObserverTest {
    BarObserver barObserver;
    ScoreObservable scoreObservable;
    Music.Clef initScoreClef = Music.Clef.TREBLE_CLEF;
    Music.NoteLength initScoreBeatUnit = Music.NoteLength.QUARTER_NOTE;
    int initScoreBeatsPerBar = 4;


    @BeforeEach
    void init() {
        scoreObservable = new ScoreObservable(initScoreClef, initScoreBeatUnit,
                initScoreBeatsPerBar);
        barObserver = new BarObserver(scoreObservable);

    }

    @Nested
    @DisplayName("updateNoteAt()")
    class UpdateNoteAt {
        int targetIndex;

        @BeforeEach
        void initUpdateNoteAt() {
            barObserver.addNote(NoteTable.get(Music.PitchClass.C_NATURAL, 4, Music.NoteLength.QUARTER_NOTE));
            barObserver.addNote(NoteTable.get(Music.PitchClass.C_NATURAL, 4, Music.NoteLength.QUARTER_NOTE));
            barObserver.addNote(NoteTable.get(Music.PitchClass.C_NATURAL, 4, Music.NoteLength.HALF_NOTE));
            targetIndex = 1;
        }

        @Test
        @DisplayName("replaces the note and produces extra rests to fill in extra bar space" +
                " when the replacement is shorter than the target note")
        void testShorterReplacement() {
            Note replacement = NoteTable.get(Music.NoteLength.SIXTEENTH_NOTE);
            barObserver.replaceNoteAt(targetIndex, replacement);
            assertAll(
                    () -> assertEquals(NoteTable.get(Music.NoteLength.EIGHTH_NOTE),
                            barObserver.getNoteArrayList().get(targetIndex + 1)),
                    () -> assertEquals(NoteTable.get(Music.NoteLength.SIXTEENTH_NOTE),
                            barObserver.getNoteArrayList().get(targetIndex + 2))
            );
        }

        @Test
        @DisplayName("replaces the note directly if the replacement is equal in length to the target" +
                " note")
        void testEqualReplacement() {
            Note replacement = NoteTable.get(barObserver.getNoteArrayList().get(targetIndex).getNoteLength());
            int originalSize = barObserver.getNoteArrayList().size();
            barObserver.replaceNoteAt(targetIndex, replacement);
            assertAll(
                    () -> assertEquals(originalSize, barObserver.getNoteArrayList().size()),
                    () -> assertEquals(barObserver.getNoteArrayList().get(targetIndex), replacement)

            );
        }

        @Test
        @DisplayName("does not replace the note if the replacement is longer in length than the target " +
                "note and there are not enough proceeding notes to replace too")
        void testLongerReplacementButNotEnoughProceeding() {
            Note replacement = NoteTable.get(Music.NoteLength.WHOLE_NOTE);
            ArrayList<Note> originalNotes = (ArrayList<Note>) barObserver.getNoteArrayList().clone();
            barObserver.replaceNoteAt(targetIndex, replacement);
            for (Note note : barObserver.getNoteArrayList()) {
                assertTrue(originalNotes.contains(note));
            }
        }

        @Test
        @DisplayName("replaces the note if the replacement is longer in length than the target " +
                "and there are enough proceeding notes to replace too")
        void testLongerReplacementWithEnoughProceedingAndProducesNoRests() {
            Note replacement = NoteTable.get(Music.NoteLength.HALF_NOTE);
            barObserver.replaceNoteAt(targetIndex, replacement);
            assertEquals(replacement, barObserver.getNoteArrayList().get(targetIndex));
        }

        @Test
        @DisplayName("replaces the note if the replacement is longer in length than the target " +
                "and there are enough proceeding notes to replace too, and produces extra rests to " +
                "fill in any gaps")
        void testLongerReplacementWithEnoughProceedingAndProducesRests() {
            Note replacement = NoteTable.get(Music.NoteLength.HALF_NOTE);
            barObserver.replaceNoteAt(targetIndex, replacement);
            assertAll(
                    () -> assertEquals(replacement, barObserver.getNoteArrayList().get(targetIndex)),
                    () -> assertEquals(NoteTable.get(Music.NoteLength.QUARTER_NOTE),
                            barObserver.getNoteArrayList().get(targetIndex++))
            );
        }
    }
}