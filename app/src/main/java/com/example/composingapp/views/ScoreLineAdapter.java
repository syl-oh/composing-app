package com.example.composingapp.views;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.composingapp.utils.music.BarObserver;
import com.example.composingapp.utils.music.ScoreObservable;
import com.example.composingapp.views.viewtools.positiondict.PositionDict;

import java.util.ArrayList;

import static com.example.composingapp.views.viewtools.ViewConstants.BARS_PER_LINE;


public class ScoreLineAdapter extends RecyclerView.Adapter<ScoreLineAdapter.BarViewGroupHolder> {
    private static final String TAG = "ScoreLineAdapter";
    private PositionDict positionDict;
    private ClefView clefView;
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
        if (positionDict == null) {
            positionDict = new PositionDict(parent.getHeight() - parent.getPaddingTop()
                    - parent.getPaddingBottom(), mScoreObservable.getClef());
        }
        if (clefView == null) {
            clefView = new ClefView(parent.getContext());
        }

        BarViewGroup barViewGroup = new BarViewGroup(parent.getContext());
        barViewGroup.setId(View.generateViewId());

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                (parent.getWidth() / BARS_PER_LINE) - parent.getPaddingLeft() - parent.getPaddingRight(),
                ViewGroup.LayoutParams.MATCH_PARENT);

//        Log.d(TAG, "onCreateViewHolder: " +
//                ((parent.getWidth() / BARS_PER_LINE) - parent.getPaddingLeft() - parent.getPaddingRight()));
        barViewGroup.setLayoutParams(layoutParams);
        return new BarViewGroupHolder(barViewGroup);
    }

    @Override
    public void onBindViewHolder(@NonNull BarViewGroupHolder holder, int position) {
        BarObserver currentBarObserver = mBarObservers.get(position);
        holder.barViewGroup.setBarObserver(currentBarObserver);

        if (position == 0) {
            int singleSpace = positionDict.getSingleSpaceHeight().intValue();
            LinearLayout.LayoutParams clefViewParams =
                    new LinearLayout.LayoutParams(singleSpace * 3, singleSpace * 8);
            clefView.setLayoutParams(clefViewParams);
            clefView.setTranslationY(positionDict.getFifthLineY() -
                     2*positionDict.getSingleSpaceHeight() );
            holder.barViewGroup.addView(clefView, 0);
        }
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

