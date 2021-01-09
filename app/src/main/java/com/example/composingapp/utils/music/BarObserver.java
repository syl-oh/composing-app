package com.example.composingapp.utils.music;

import com.example.composingapp.utils.interfaces.observer.Observer;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.example.composingapp.views.viewtools.ViewConstants.TOTAL_LINES;
import static com.example.composingapp.views.viewtools.ViewConstants.TOTAL_SPACES;

public class BarObserver implements Observer {
    private ArrayList<Note> mNoteArrayList;
    private ScoreObservable mScoreObservable;
    private Music.NoteLength mBeatUnit;
    private int mBeatsPerBar;
    private Music.Clef mClef;
    private HashMap<Music.NoteLength, Double> mNoteLengthToBeatsMap;

    /**
     * Constructor
     *
     * @param scoreObservable The score to which this BarObserver is observing: necessary for
     *                        Observer Pattern
     */
    public BarObserver(@NotNull ScoreObservable scoreObservable) {
        // Subscribe to the score and add itself
        this.mScoreObservable = scoreObservable;
        scoreObservable.addObserver(this);

        // Init field variables
        mNoteArrayList = new ArrayList<>();
        mBeatUnit = scoreObservable.getBeatUnit();
        mBeatsPerBar = scoreObservable.getBeatsPerBar();
        mClef = scoreObservable.getClef();
        initNoteLengthToBeatsMap();
    }

    private ArrayList<Note> fitNotesWithinStaff(ArrayList<Note> noteArrayList, Music.Clef clef) {
        final int octaveMidiDistance = 12;
        MidiNoteDict midiNoteDict = MidiNoteDict.getInstance();
        int midiNumOfBottomNote = clef.getMidiStartingIndex();
        int midiNumOfTopNote = midiNumOfBottomNote + TOTAL_SPACES + TOTAL_LINES - 1;

        for (int i = 0; i < noteArrayList.size(); i++) {
            Note currentNote = noteArrayList.get(i);
            if (currentNote.getPitchClass() != Music.PitchClass.REST) {
                int noteMidiNum = 0;
                try {
                    noteMidiNum =
                            midiNoteDict.getMidiNum(ToneTable.get(currentNote.getPitchClass(), currentNote.getOctave()));
                } catch (NullPointerException e) {
                }

                // Raise the octave of the currentNote if it is too low
                while (noteMidiNum < midiNumOfBottomNote) {
                    noteMidiNum += octaveMidiDistance; // Move up an octave distance
                }
                // Lower the octave of the currentNote if it is too high
                while (noteMidiNum > midiNumOfTopNote) {
                    noteMidiNum -= octaveMidiDistance;
                }
                Tone replacementTone = midiNoteDict.getTone(noteMidiNum);
                noteArrayList.remove(i);
                noteArrayList.add(i, NoteTable.get(replacementTone.getPitchClass(),
                        replacementTone.getOctave(), currentNote.getNoteLength()));
            }
        }
        return noteArrayList;
    }

    /**
     * Getter method for this BarObserver's list of Note objects
     *
     * @return ArrayList of Note objects
     */
    public ArrayList<Note> getNoteArrayList() {
        return mNoteArrayList;
    }

    /**
     * Getter method for this BarObserver's beat unit
     *
     * @return NoteLength representing the beat unit of this BarObserver
     */
    public Music.NoteLength getBeatUnit() {
        return mBeatUnit;
    }

    /**
     * Getter method for this BarObserver's BeatsPerBar
     *
     * @return Int representing the total beats allowed in this bar
     */
    public int getBeatsPerBar() {
        return mBeatsPerBar;
    }

    /**
     * Getter method for this BarObserver's Clef
     *
     * @return The clef of this BarObserver
     */
    public Music.Clef getClef() {
        return mClef;
    }

    /**
     * Updates the properties of this BarObserver and adjusts the list of Notes accordingly
     */
    @Override
    public void update() {
        mBeatUnit = mScoreObservable.getBeatUnit();
        mBeatsPerBar = mScoreObservable.getBeatsPerBar();
        mClef = mScoreObservable.getClef();
        // Clones the list and resize it
        mNoteArrayList = fitNotesWithinStaff(shrinkListToFitBar(mNoteArrayList), mClef);
    }


    /**
     * Adds a given Note to this BarObserver to the end of the bar. Should only be called on
     * initialization of a BarObserver
     *
     * @param note The Note to add
     */
    public void addNote(Note note) {
        mNoteArrayList.add(note);
    }

    /**
     * Replaces the note at a given index with a replacement, and fills any extra space with rest
     * Notes. If the replacement is longer than the note at the given index, the proceeding note
     * will also be replaced. If the proceeding note cannot be replaced, the transaction fails and
     * nothing happens.
     *
     * @param index       index of the note to replace
     * @param replacement Note to replace with
     */
    public void replaceNoteAt(int index, Note replacement) {
        // Verify that the index is within the Note ArrayList
        if (index <= mNoteArrayList.size() - 1) {
            Note targetNote = mNoteArrayList.get(index);
            double targetNoteBeatWeight = mNoteLengthToBeatsMap.get(targetNote.getNoteLength());
            double replacementNoteBeatWeight = mNoteLengthToBeatsMap.get(replacement.getNoteLength());

            // If the replacement is less than the target in beat weight
            if (replacementNoteBeatWeight < targetNoteBeatWeight) {
                // Replace the target with the replacement and move the index pointer one forward
                mNoteArrayList.remove(index);
                mNoteArrayList.add(index, replacement);
                index += 1;
                addRestsWithWeightSumAt(index, targetNoteBeatWeight - replacementNoteBeatWeight);

            } else if (replacementNoteBeatWeight == targetNoteBeatWeight) {
                mNoteArrayList.remove(index);
                mNoteArrayList.add(index, replacement);
            } else {
                double proceedingNotesBeatWeightSum = 0;
                for (int i = 0; i < mNoteArrayList.size(); i++) {
                    // Add the beat weights of Notes after the given index to get the sum
                    if (i > index) {
                        proceedingNotesBeatWeightSum +=
                                mNoteLengthToBeatsMap.get(mNoteArrayList.get(i).getNoteLength());
                    }

                    // Check if the there is enough weight to complete the replacement of the Note
                    if ((proceedingNotesBeatWeightSum + targetNoteBeatWeight) >= replacementNoteBeatWeight) {
                        double weightDifference =
                                (proceedingNotesBeatWeightSum + targetNoteBeatWeight) - replacementNoteBeatWeight;
                        // Remove all notes between the given index and i
                        for (int k = 0; k <= i - index; k++) {
                            mNoteArrayList.remove(index);
                        }
                        mNoteArrayList.add(index, replacement);
                        addRestsWithWeightSumAt(++index, weightDifference);
                        break;
                    }
                }
            }
        }
    }

    /**
     * Fills the remainingWeight beat sum with rest Notes
     *
     * @param index     index at which to add the rests
     * @param weightSum sum of beat weight of the rests to be added
     */
    private void addRestsWithWeightSumAt(int index, double weightSum) {
        // Fill the remaining weight with Rest Notes
        while (weightSum > 0) {
            Music.NoteLength maxNoteLength = getMaxNoteLengthThatFits(weightSum);
            // Add the Rest and move the index pointer
            mNoteArrayList.add(index, NoteTable.get(maxNoteLength));
            index++;
            weightSum -= mNoteLengthToBeatsMap.get(maxNoteLength);
        }
    }

    /**
     * Produces the largest NoteLength that fits within a given beatWeight
     *
     * @param beatWeight double value representing the beat weight to fit in
     * @return NoteLength with the largest beat weight that fits within the given beatWeight
     */
    private Music.NoteLength getMaxNoteLengthThatFits(double beatWeight) {
        // Set the default maximum to the lowest NoteLength
        Music.NoteLength maxThatFits = Music.NoteLength.SIXTEENTH_NOTE;

        for (Map.Entry entry : mNoteLengthToBeatsMap.entrySet()) {
            Music.NoteLength currentNoteLength = (Music.NoteLength) entry.getKey();
            double currentWeight = (double) entry.getValue();
            // If we have found a larger NoteLength that fits in the extra space
            if (currentWeight > mNoteLengthToBeatsMap.get(maxThatFits) && currentWeight <= beatWeight) {
                maxThatFits = currentNoteLength;
            }
        }
        return maxThatFits;
    }


    /**
     * Resizes a Note ArrayList so that it does not contain more beats than permitted in this
     * BarObserver
     *
     * @param noteArrayList The ArrayList of Notes to resize
     * @return A resized ArrayList of Notes, excluding any notes at the end that would make the
     * BarObserver exceed its BeatsPerBar
     */
    private ArrayList<Note> shrinkListToFitBar(ArrayList<Note> noteArrayList) {
        // Check if a whole note occupies the entire noteArrayList
        if (noteArrayList.size() == 1
                && noteArrayList.get(0).equals(
                NoteTable.get(Music.PitchClass.REST, -1, Music.NoteLength.WHOLE_NOTE))) {
            return noteArrayList; // If so, this is a valid bar that doesn't need resizing
        }

        // Otherwise, we check if the sum of the beat values of all the notes fit the bar
        double beatSum = 0d;
        ArrayList<Note> resizedNoteArrayList = new ArrayList<>();
        Note currentNote = null;
        for (int i = 0; i < noteArrayList.size(); i++) {
            currentNote = noteArrayList.get(i);
            beatSum += mNoteLengthToBeatsMap.get(Objects.requireNonNull(currentNote).getNoteLength());
            if (beatSum > mBeatsPerBar) {    // If we have hit the beat sum limit
                return resizedNoteArrayList;  // End the loop and return what we have resized
            } else {
                resizedNoteArrayList.add(currentNote); // Otherwise, add the note and keep resizing
            }
        }
        return resizedNoteArrayList;
    }


    /**
     * Initializes the HashMap for retrieving the beat value of any NoteLength in the context of
     * this BarObserver instance
     */
    private void initNoteLengthToBeatsMap() {
        mNoteLengthToBeatsMap = new HashMap<>();
        for (Music.NoteLength noteLength : Music.NoteLength.values()) {
            Double beatValue = (noteLength.getValueToWholeNote() / mBeatUnit.getValueToWholeNote());
            mNoteLengthToBeatsMap.put(noteLength, beatValue);
        }
    }
}
