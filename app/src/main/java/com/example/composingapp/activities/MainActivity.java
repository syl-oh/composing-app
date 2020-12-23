package com.example.composingapp.activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.composingapp.R;
import com.example.composingapp.utils.music.ScoreObservable;
import com.example.composingapp.viewmodels.ScoreViewModel;
import com.example.composingapp.views.ScoreLineAdapter;

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
        initBars();

        // Create the RecyclerView for the ImageButtons that the user can use to build scores
        initMusicButtons();
    }

    private void initMusicButtons() {

    }

    /**
     * Creates the RecyclerView and adds a listener to attach the RecyclerView to the ViewModel
     */
    private void initBars() {
        // Init the RecyclerView
        RecyclerView scoreLineView = findViewById(R.id.scorelineview);
        final ScoreLineAdapter scoreLineAdapter = new ScoreLineAdapter(
                mScoreViewModel.getScoreObservableMutableLiveData().getValue());
        scoreLineView.setAdapter(scoreLineAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        scoreLineView.setLayoutManager(layoutManager);

        // Observer which updates the RecyclerView
        final Observer<ScoreObservable> scoreObserver = scoreLineAdapter::setScoreObservable;
        // Observe the LiveData for the score in the ScoreViewModel
        mScoreViewModel.getScoreObservableMutableLiveData().observe(this, scoreObserver);
    }
}