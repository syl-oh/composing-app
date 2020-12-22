package com.example.composingapp.activities;
import com.example.composingapp.utils.music.BarObserver;
import com.example.composingapp.utils.music.Music;
import com.example.composingapp.utils.music.Note;
import com.example.composingapp.utils.music.ScoreObservable;
import com.example.composingapp.views.ScoreLineAdapter;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.composingapp.R;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ScoreObservable mScoreObservable = new ScoreObservable(Music.Clef.TREBLE_CLEF,
                Music.NoteLength.QUARTER_NOTE, 4);
        BarObserver barObserver1 = new BarObserver(mScoreObservable);
        BarObserver barObserver2 = new BarObserver(mScoreObservable);
        Note note = new Note(Music.PitchClass.C_NATURAL, 4, Music.NoteLength.QUARTER_NOTE);
        barObserver1.addNote(note);
        barObserver2.addNote(note);

        // Init the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final ScoreLineAdapter scoreLineAdapter = new ScoreLineAdapter(mScoreObservable);
        recyclerView.setAdapter(scoreLineAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
    }
}