package com.example.composingapp.utils.music;

import android.util.Log;

import com.example.composingapp.utils.interfaces.observer.Observer;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class BarObserver implements Observer {
    private static final String TAG = "BarObserver";
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

    /**
     * Getter method for this BarObserver's list of Note objects
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
     * @return Int representing the total beats allowed in this bar
     */
    public int getBeatsPerBar() {
        return mBeatsPerBar;
    }

    /**
     * Getter method for this BarObserver's Clef
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
        mNoteArrayList = resizeNoteArrayListToFitBar(mNoteArrayList);
    }


    /**
     * Adds a given Note to this BarObserver to the end of the bar
     *
     * @param note The Note to add
     */
    public void addNote(Note note) {
        mNoteArrayList.add(note);
        // Assert the bar contains a valid amount of notes
        mNoteArrayList = resizeNoteArrayListToFitBar(mNoteArrayList);
    }

    /**
     * Adds a given Note to this BarObserver at a specific index
     *
     * @param index The index of where to add the note
     * @param note  The Note to add
     */
    public void addNoteAt(int index, Note note) {
        mNoteArrayList.add(index, note);
        // Assert the bar contains a valid amount of notes
        mNoteArrayList = resizeNoteArrayListToFitBar(mNoteArrayList);
    }

    /**
     * Removes a given Note from this BarObserver
     *
     * @param note The Note to remove
     */
    public void removeNote(Note note) {
        mNoteArrayList.remove(note);
    }

    /**
     * Resizes a Note ArrayList so that it does not contain more beats than permitted in this
     * BarObserver
     *
     * @param noteArrayList The ArrayList of Notes to resize
     * @return A resized ArrayList of Notes, excluding any notes at the end that would make the
     * BarObserver exceed its BeatsPerBar
     */
    private ArrayList<Note> resizeNoteArrayListToFitBar(ArrayList<Note> noteArrayList) {
        // Check if a whole note occupies the entire noteArrayList
        if (noteArrayList.size() == 1
                && noteArrayList.get(0).equals(
                new Note(Music.PitchClass.REST, -1, Music.NoteLength.WHOLE_NOTE))) {
            return noteArrayList; // If so, this is a valid bar that doesn't need resizing
        }

        // Otherwise, we check if the sum of the beat values of all the notes fit the bar
        Double beatSum = 0d;
        ArrayList<Note> resizedNoteArrayList = new ArrayList<>();
        Note currentNote = null;
        for (int i = 0; i < noteArrayList.size(); i++) {
            try {
                currentNote = noteArrayList.get(i);
            } catch (NullPointerException e) {
                Log.e(TAG, "resizeToBeatsPerBar: could not retrieve note at index " + i +
                        "from noteArrayList");
            }
            try {
                beatSum += mNoteLengthToBeatsMap.get(currentNote.getNoteLength());
            } catch (NullPointerException e) {
                Log.e(TAG, "resizeToBeatsPerBar: NullPointerException, could not retrieve " +
                        "NoteLength form NoteLengthToBeatsMap");
            }

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
