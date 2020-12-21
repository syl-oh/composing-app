package com.example.composingapp.utils.music;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

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
    @DisplayName("addNote()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class AddNote {
        Note initWholeRest = new Note(Music.PitchClass.REST, -1, Music.NoteLength.WHOLE_NOTE);
        @BeforeEach
        void initFullBarForAdding() {
            // Fill the bar
            barObserver.addNote(initWholeRest);
        }

        @Test
        @DisplayName("does not add a note to a full bar")
        void testAddToEndWithAddNote() {
            Note note = new Note(Music.PitchClass.C_NATURAL, 4, Music.NoteLength.QUARTER_NOTE);
            barObserver.addNote(note);
            assertTrue(!barObserver.getNoteArrayList().contains(note));
        }
    }

    @Nested
    @DisplayName("addNoteAt()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class AddNoteAt {
        Note initWholeRest = new Note(Music.PitchClass.REST, -1, Music.NoteLength.WHOLE_NOTE);
        @BeforeEach
        void initFullBarForAdding() {
            // Fill the bar
            barObserver.addNote(initWholeRest);
        }

        @Test
        @DisplayName("does not add a note to a full bar at the last index")
        void testAddToLastIndexOfAddNoteAt() {
            int lastIndex = barObserver.getNoteArrayList().size();
            Note note = new Note(Music.PitchClass.C_NATURAL, 4, Music.NoteLength.QUARTER_NOTE);
            barObserver.addNoteAt(lastIndex, note);
            assertTrue(!barObserver.getNoteArrayList().contains(note));
        }

        @Test
        @DisplayName("resizes the bar so it does not contain extra notes")
        void testResizeAfterAddNoteAt() {
            int secondLastIndex = barObserver.getNoteArrayList().size() - 1;
            Note note = new Note(Music.PitchClass.C_NATURAL, 4, Music.NoteLength.QUARTER_NOTE);
            barObserver.addNoteAt(secondLastIndex, note);

            assertAll(
                    () -> assertTrue(barObserver.getNoteArrayList().contains(note)),
                    () -> assertFalse(barObserver.getNoteArrayList().contains(initWholeRest))
            );

        }
    }

    @Nested
    @DisplayName("removeNote()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class RemoveNote {
        Note initWholeRest = new Note(Music.PitchClass.REST, -1, Music.NoteLength.WHOLE_NOTE);

        @BeforeEach
        void initFullBarForRemoving() {
            // Fill the bar
            barObserver.addNote(initWholeRest);
        }

        @DisplayName("removes a given note from the BarObserver")
        @Test
        void testRemoveNote() {
            barObserver.removeNote(initWholeRest);
            assertFalse(barObserver.getNoteArrayList().contains(initWholeRest));
        }
    }
}