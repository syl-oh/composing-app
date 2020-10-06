package com.example.composingapp.views;

import com.example.composingapp.music.MidiNoteDict;
import com.example.composingapp.music.Music;
import com.example.composingapp.music.Tone;

import java.util.HashMap;

public class ViewConstants {
     private static final int BASS_CLEF_MIDI_START = 620;
     private static final int TREBLE_CLEF_MIDI_START = 620;
     static int TOTAL_LINES = 17;
     static int TOTAL_SPACES = TOTAL_LINES - 1;
     static int STEM_WIDTH = 3;
     static float NOTE_W_TO_H_RATIO = (float) (4.0 / 3.0);
     static HashMap<Tone, Float> NOTE_Y_POSITIONS;

     /**
      * Initializes the mNoteYPositions array, which contains all permitted Y-coordinates for a note
      */
     static public void initNoteYPositions(float barHeight, Music.Staff clef) {
          int totalPositions = ViewConstants.TOTAL_SPACES + ViewConstants.TOTAL_LINES;
          MidiNoteDict midiNoteDict = new MidiNoteDict();
          int midiIndex = getStartingIndex(clef);
          NOTE_Y_POSITIONS = new HashMap<>();
          for (int i = 0; i < totalPositions; i++) {
               NOTE_Y_POSITIONS.put(midiNoteDict.getTone(midiIndex),
                       ((barHeight * i) / totalPositions) - (2 * ViewConstants.STEM_WIDTH));
          }
     }

     static int getStartingIndex(Music.Staff clef) {
          switch (clef) {
               case TREBLE_CLEF:
                    return TREBLE_CLEF_MIDI_START;
               case BASS_CLEF:
                    return BASS_CLEF_MIDI_START;
               break;
          }
     }
}
