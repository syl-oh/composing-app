package com.example.composingapp.activities;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.composingapp.R;
import com.example.composingapp.utils.interfaces.ui.Clickable;
import com.example.composingapp.utils.music.BarObserver;
import com.example.composingapp.utils.music.Music;
import com.example.composingapp.utils.music.Note;
import com.example.composingapp.utils.music.NoteTable;
import com.example.composingapp.viewmodels.ScoreViewModel;
import com.example.composingapp.views.NoteView;
import com.example.composingapp.views.ScoreLineAdapter;
import com.example.composingapp.views.ScoreLineView;
import com.example.composingapp.views.commands.ChangeClefCommand;
import com.example.composingapp.views.commands.ChangeNoteCommand;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ScoreViewModel mScoreViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Init the ViewModel
        mScoreViewModel = new ViewModelProvider(this).get(ScoreViewModel.class);

        // Create the RecyclerView for the BarViewGroups
        // Init the RecyclerVie
        ScoreLineView scoreLineView = findViewById(R.id.scorelineview);
        final ScoreLineAdapter scoreLineAdapter = new ScoreLineAdapter(mScoreViewModel);
        scoreLineView.setAdapter(scoreLineAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        layoutManager.canScrollHorizontally();
        scoreLineView.setLayoutManager(layoutManager);

        // Observe the LiveData for the score in the ScoreViewModel
        mScoreViewModel.getScoreObservableMutableLiveData()
                .observe(this, scoreLineAdapter::setScoreObservable);


        ImageButton quarterNoteButton = findViewById(R.id.quarterNoteButton);
        quarterNoteButton.setOnClickListener(view -> {
            Clickable clickedChild = scoreLineView.findClickedChild();
            if (clickedChild instanceof NoteView) {
                Note oldNote = ((NoteView) clickedChild).getNotePositionDict().getNote();
                BarObserver barObserver = ((NoteView) clickedChild).getBarViewGroup().getBarObserver();
                int index = ((NoteView) clickedChild).getBarViewGroup().getNoteViewList().indexOf(clickedChild);
                Note replacement = NoteTable.get(oldNote.getPitchClass(), oldNote.getOctave(), Music.NoteLength.QUARTER_NOTE);
                ChangeNoteCommand command =
                        new ChangeNoteCommand(mScoreViewModel, barObserver, index, replacement);
                command.execute();
                command.notifyScoreViewModelObservers();
            }
        });

        ImageButton bassClefButton = findViewById(R.id.bassClefButton);
        ChangeClefCommand bassClefCommand = new ChangeClefCommand(mScoreViewModel, Music.Clef.BASS_CLEF);
        bassClefButton.setOnClickListener(v -> bassClefCommand.execute());

        ImageButton trebleClefButton = findViewById(R.id.trebleClefButton);
        ChangeClefCommand trebleClefCommand = new ChangeClefCommand(mScoreViewModel, Music.Clef.TREBLE_CLEF);
        trebleClefButton.setOnClickListener(v -> trebleClefCommand.execute());
    }
}