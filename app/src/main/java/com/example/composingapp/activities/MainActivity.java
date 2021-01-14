package com.example.composingapp.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.composingapp.R;
import com.example.composingapp.utils.interfaces.ui.Command;
import com.example.composingapp.utils.interfaces.ui.CommandReceiver;
import com.example.composingapp.utils.music.Music;
import com.example.composingapp.utils.music.NoteTable;
import com.example.composingapp.viewmodels.ScoreViewModel;
import com.example.composingapp.views.ScoreLineAdapter;
import com.example.composingapp.views.ScoreLineView;
import com.example.composingapp.views.UserCommandAdapter;
import com.example.composingapp.views.commands.AddAccidentalCommand;
import com.example.composingapp.views.commands.ChangeClefCommand;
import com.example.composingapp.views.commands.ChangeNoteCommand;
import com.example.composingapp.views.models.UserCommandModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CommandReceiver {
    private static final String TAG = "MainActivity";
    private ScoreViewModel mScoreViewModel;
    private ScoreLineView mScoreLineView;
    private ArrayDeque<Command> undoDeque = new ArrayDeque<>();
    private ArrayDeque<Command> redoDeque = new ArrayDeque<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Init the ViewModel
        mScoreViewModel = new ViewModelProvider(this).get(ScoreViewModel.class);

        // Create the RecyclerView for the BarViewGroups
        // Init the RecyclerView
        mScoreLineView = findViewById(R.id.scorelineview);
        final ScoreLineAdapter scoreLineAdapter = new ScoreLineAdapter(mScoreViewModel);
        mScoreLineView.setAdapter(scoreLineAdapter);
        LinearLayoutManager scoreLayoutManager = new LinearLayoutManager(this);
        scoreLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        scoreLayoutManager.canScrollHorizontally();
        mScoreLineView.setLayoutManager(scoreLayoutManager);
        // Observe the LiveData for the score in the ScoreViewModel
        mScoreViewModel.getScoreObservableMutableLiveData()
                .observe(this, scoreLineAdapter::setScoreObservable);

        // Create the RecyclerView for UserCommandButtons
        RecyclerView userCommandRecyclerView = findViewById(R.id.user_command_recycler);
        final UserCommandAdapter userCommandAdapter = new UserCommandAdapter(this,
                generateCommands());
        userCommandRecyclerView.setAdapter(userCommandAdapter);
        LinearLayoutManager commandLayoutManager = new LinearLayoutManager(this);
        commandLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        userCommandRecyclerView.setLayoutManager(commandLayoutManager);
    }

    /**
     * Produces an ArrayList of UserCommandModels for the user commands RecyclerView
     *
     * @return ArrayList of UserCommandModel objects in the order that they should appear on-screen
     */
    private ArrayList<UserCommandModel> generateCommands() {
        ArrayList<UserCommandModel> userCommandModels = new ArrayList<>();
        userCommandModels.add(new UserCommandModel(R.drawable.ic_treble_clef,
                new ChangeClefCommand(mScoreViewModel, Music.Clef.TREBLE_CLEF)));
        userCommandModels.add(new UserCommandModel(R.drawable.ic_bass_clef,
                new ChangeClefCommand(mScoreViewModel, Music.Clef.BASS_CLEF)));

        // Iterate through the drawables and notelength and add ChangeNoteCommands
        int[] noteImageIDS = {R.drawable.ic_whole_note,
                R.drawable.ic_half_note, R.drawable.ic_quarter_note, R.drawable.ic_eighth_note,
                R.drawable.ic_sixteenth_note};
        int[] restImageIds = {R.drawable.ic_whole_rest,
                R.drawable.ic_half_rest, R.drawable.ic_quarter_rest, R.drawable.ic_eighth_rest,
                R.drawable.ic_sixteenth_rest};

        Music.NoteLength[] noteLengths = Music.NoteLength.getValues();
        for (int i = 0; i < noteImageIDS.length; i++) {
            userCommandModels.add(new UserCommandModel(
                    noteImageIDS[i], makeChangeNoteCommand(noteLengths[i])));
        }
        for (int i = 0; i < restImageIds.length; i++) {
            userCommandModels.add(new UserCommandModel(
                    restImageIds[i], new ChangeNoteCommand(mScoreLineView, mScoreViewModel,
                    NoteTable.get(noteLengths[i]), true)));
        }

        return userCommandModels;
    }

    /**
     * Produces a generic ChangeNoteCommand for non-rest-changing requests. Used to user's commands
     *
     * @param noteLengthOfNote NoteLength of the non-rest new Note to replace the target in the score
     *                         data with
     * @return ChangeNoteCommand pre-loaded with a requested change to a Note in the ScoreViewModel's
     *         ScoreObservable live data
     */
    private ChangeNoteCommand makeChangeNoteCommand(Music.NoteLength noteLengthOfNote) {
        return new ChangeNoteCommand(mScoreLineView, mScoreViewModel,
                NoteTable.get(Music.PitchClass.C_NATURAL, 4, noteLengthOfNote), true);
    }


    /**
     * Executes a command, and adjusts undo/redo stack accordingly
     * @param command Command to execute
     * Side effects: Pushes command onto undoDeque
     */
    @Override
    public void actOn(@NotNull Command command) {
        command.execute();
        undoDeque.push(command);
    }

    /**
     * Undoes the last Command executed
     * @param view View with this function as an onClickListener
     * Side effects: Pops last element of undoDeque and pushes onto redoDeque
     *               Command's side effects
     */
    public void undo(View view) {
        if (undoDeque.peek() != null) {
            undoDeque.peek().undo();
            redoDeque.push(undoDeque.pop());
        }
    }

    /**
     * Re-executes the last Command undone
     * @param view View with this function as an onClickListener
     * Side effects: Pops last element of redoDeque and pushes onto undoDeque
     *               Command's side effects
     */
    public void redo(View view) {
        if (redoDeque.peek() != null) {
            redoDeque.peek().execute();
            undoDeque.push(redoDeque.pop());
        }
    }
}