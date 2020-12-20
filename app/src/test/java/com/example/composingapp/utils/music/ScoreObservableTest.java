package com.example.composingapp.utils.music;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("When a ScoreObservable is created")
class ScoreObservableTest {
    ScoreObservable scoreObservable;
    Music.Clef initScoreClef = Music.Clef.TREBLE_CLEF;
    Music.NoteLength initScoreBeatUnit = Music.NoteLength.QUARTER_NOTE;
    int initScoreBeatsPerBar = 4;

    @BeforeEach
    void init() {
        scoreObservable = new ScoreObservable(initScoreClef, initScoreBeatUnit,
                initScoreBeatsPerBar);
    }

    @Nested
    @DisplayName("updateObservers")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class ToneToYMap {
        BarObserver barObserver;

        @BeforeEach
        void initScoreAndBar() {
            barObserver = new BarObserver(scoreObservable);
        }

        @Test
        @DisplayName("updates the clefs of its BarObservers when its clef is changed")
        void updateObservers() {
            Music.Clef newClef = Music.Clef.BASS_CLEF;
            scoreObservable.setClef(newClef);
            scoreObservable.updateObservers();

            assertAll(
                    () -> assertEquals(barObserver.getClef(), newClef),
                    () -> assertEquals(barObserver.getBeatsPerBar(), initScoreBeatsPerBar),
                    () -> assertEquals(barObserver.getBeatUnit(), initScoreBeatUnit
                    ));
        }
    }
}