package com.example.composingapp.activities;
import com.example.composingapp.utils.music.BarObserver;
import com.example.composingapp.utils.music.Music;
import com.example.composingapp.utils.music.Note;
import com.example.composingapp.utils.music.ScoreObservable;
import com.example.composingapp.viewmodels.ScoreViewModel;
import com.example.composingapp.views.ScoreLineAdapter;


import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.composingapp.R;

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
        initRecyclerView();
    }

    /**
     * Creates the RecyclerView and adds a listener to attach the RecyclerView to the ViewModel
     */
    private void initRecyclerView() {
        // Init the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final ScoreLineAdapter scoreLineAdapter = new ScoreLineAdapter(
                mScoreViewModel.getScoreObservableMutableLiveData().getValue());
        recyclerView.setAdapter(scoreLineAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);

        // Observer which updates the RecyclerView
        final Observer<ScoreObservable> scoreObserver = scoreLineAdapter::setScoreObservable;
        // Observe the LiveData for the score in the ScoreViewModel
        mScoreViewModel.getScoreObservableMutableLiveData().observe(this, scoreObserver);
    }
}