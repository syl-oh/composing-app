package com.example.composingapp.views;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.composingapp.utils.music.BarObserver;
import com.example.composingapp.utils.music.ScoreObservable;

import java.util.ArrayList;

public class ScoreLineAdapter extends RecyclerView.Adapter<ScoreLineAdapter.BarViewGroupHolder> {
    private static final String TAG = "ScoreLineAdapter";
    private ScoreObservable mScoreObservable; // Copy of scoreObservable
    private ArrayList<BarObserver> mBarObservers;

    public ScoreLineAdapter(ScoreObservable scoreObservable) {
        mScoreObservable = scoreObservable;
        mBarObservers = mScoreObservable.getBarObserverList();
    }

    public void setScoreObservable(ScoreObservable scoreObservable) {
        this.mScoreObservable = scoreObservable;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BarViewGroupHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Log.d(TAG, "onCreateViewHolder: ");
        BarViewGroup barViewGroup = new BarViewGroup(parent.getContext());
        barViewGroup.setId(View.generateViewId());
        barViewGroup.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        return new BarViewGroupHolder(barViewGroup);
    }

    @Override
    public void onBindViewHolder(@NonNull BarViewGroupHolder holder, int position) {
        BarObserver currentBarObserver = mBarObservers.get(position);
        holder.barViewGroup.setBarObserver(currentBarObserver);
    }

    @Override
    public int getItemCount() {
        return mBarObservers.size();
    }


    class BarViewGroupHolder extends RecyclerView.ViewHolder {
        private BarViewGroup barViewGroup;

        public BarViewGroupHolder(@NonNull View itemView) {
            super(itemView);
//            Log.d(TAG, "ScoreLineViewHolder: ");
            barViewGroup = (BarViewGroup) itemView;
        }

        public BarViewGroup getBarViewGroup() {
            return barViewGroup;
        }
    }
}

