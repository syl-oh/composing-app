package com.example.composingapp.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.composingapp.R;
import com.example.composingapp.utils.music.Music;
import com.example.composingapp.utils.music.ScoreObservable;
import com.example.composingapp.viewmodels.ScoreViewModel;
import com.example.composingapp.views.ScoreLineAdapter;
import com.example.composingapp.views.ScoreLineView;

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
        final ScoreLineAdapter scoreLineAdapter = new ScoreLineAdapter(
                mScoreViewModel.getScoreObservableMutableLiveData().getValue());
        scoreLineView.setAdapter(scoreLineAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        layoutManager.canScrollHorizontally();
        scoreLineView.setLayoutManager(layoutManager);

        // Observe the LiveData for the score in the ScoreViewModel
        mScoreViewModel.getScoreObservableMutableLiveData()
                .observe(this, scoreLineAdapter::setScoreObservable);


        ImageButton bassClefButton = findViewById(R.id.bassClefButton);
        bassClefButton.setOnClickListener(v -> mScoreViewModel.setClef(Music.Clef.BASS_CLEF));
        ImageButton trebleClefButton = findViewById(R.id.trebleClefButton);
        trebleClefButton.setOnClickListener(v -> mScoreViewModel.setClef(Music.Clef.TREBLE_CLEF));
    }
}