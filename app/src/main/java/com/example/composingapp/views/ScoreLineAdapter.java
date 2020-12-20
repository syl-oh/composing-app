package com.example.composingapp.views;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class ScoreLineAdapter extends RecyclerView.Adapter<ScoreLineAdapter.ScoreLineViewHolder> {
    private Context mClef;

    public ScoreLineAdapter(Context clef) {
        this.mClef = clef;
    }

    @NonNull
    @Override
    public ScoreLineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreLineViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    class ScoreLineViewHolder extends RecyclerView.ViewHolder {

        public ScoreLineViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

