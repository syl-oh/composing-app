package com.example.composingapp.views;

import android.graphics.Color;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.composingapp.R;
import com.example.composingapp.utils.music.BarObserver;
import com.example.composingapp.utils.music.ScoreObservable;
import com.example.composingapp.viewmodels.ScoreViewModel;
import com.example.composingapp.views.viewtools.positiondict.ScorePositionDict;

import java.util.ArrayList;

import static com.example.composingapp.views.viewtools.ViewConstants.BARS_PER_LINE;


public class ScoreLineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ADD_BUTTON_FOOTER = 664;
    private static final String TAG = "ScoreLineAdapter";
    private final ScoreViewModel mScoreViewModel;
    private ScorePositionDict positionDict;
    private ClefView clefView;
    private ScoreObservable mScoreObservable; // Copy of scoreObservable
    private ArrayList<BarObserver> mBarObservers;


    public ScoreLineAdapter(ScoreViewModel scoreViewModel) {
        mScoreViewModel = scoreViewModel;
        mScoreObservable = scoreViewModel.getScoreObservableMutableLiveData().getValue();
        assert mScoreObservable != null;
        mBarObservers = mScoreObservable.getBarObserverList();
    }

    public void setScoreObservable(ScoreObservable scoreObservable) {
        this.mScoreObservable = scoreObservable;
        mBarObservers = scoreObservable.getBarObserverList();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (positionDict == null) {
            positionDict = new ScorePositionDict(parent.getHeight() - parent.getPaddingTop()
                    - parent.getPaddingBottom(), mScoreObservable.getClef());
        }

        if (viewType == ADD_BUTTON_FOOTER) {
            Log.d(TAG, "onCreateViewHolder: ");
            int sideLength = (int) (positionDict.getSingleSpaceHeight() * 3);
            LinearLayout.LayoutParams addButtonParams = new LinearLayout.LayoutParams(sideLength, sideLength);
            ImageButton imageButton = new ImageButton(parent.getContext());
            imageButton.setId(View.generateViewId());
            imageButton.setImageResource(R.drawable.ic_baseline_add_circle_outline_24);
            imageButton.setBackgroundColor(Color.TRANSPARENT);
            imageButton.setLayoutParams(addButtonParams);
            imageButton.setTranslationY(positionDict.getFifthLineY() + positionDict.getSingleSpaceHeight() / 2);

            imageButton.setOnClickListener(v -> {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                new BarObserver(mScoreObservable);
                notifyDataSetChanged();
            });

            return new AddButtonViewHolder(imageButton);
        }

        BarViewGroup barViewGroup = new BarViewGroup(parent.getContext(), mScoreViewModel, positionDict);
        barViewGroup.setId(View.generateViewId());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                (parent.getWidth() / BARS_PER_LINE) - parent.getPaddingLeft() - parent.getPaddingRight(),
                parent.getHeight() - parent.getPaddingTop() - parent.getPaddingBottom());
        barViewGroup.setLayoutParams(layoutParams);

        return new BarViewGroupHolder(barViewGroup);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BarViewGroupHolder) {
            BarViewGroupHolder vh = (BarViewGroupHolder) holder;
            BarObserver currentBarObserver = mBarObservers.get(position);
            vh.barViewGroup.setBarObserver(currentBarObserver);
            if (position == 0) {
                vh.barViewGroup.addView(createClefView(vh.barViewGroup), 0);
            }
        }
    }


    @Override
    public int getItemCount() {
        if (mBarObservers.isEmpty()) {
            return 0;
        } else {
            return mBarObservers.size() + 1; // Add one to include the AddButtonViewHolder
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position == mBarObservers.size()) ? ADD_BUTTON_FOOTER : super.getItemViewType(position);
    }

    /**
     * Creates a ClefView to add to parent
     *
     * @param parent the parent of the ClefView
     * @return ClefView to add
     */
    private ClefView createClefView(View parent) {
        clefView = new ClefView(parent.getContext());
        int singleSpace = positionDict.getSingleSpaceHeight().intValue();
        LinearLayout.LayoutParams clefViewParams =
                new LinearLayout.LayoutParams(singleSpace * 3, singleSpace * 8);
        clefView.setLayoutParams(clefViewParams);
        clefView.setTranslationY(positionDict.getFifthLineY() -
                2 * positionDict.getSingleSpaceHeight());
        clefView.setClef(mScoreObservable.getClef());
        return clefView;
    }

    static class BarViewGroupHolder extends RecyclerView.ViewHolder {
        private BarViewGroup barViewGroup;

        public BarViewGroupHolder(@NonNull View itemView) {
            super(itemView);
            barViewGroup = (BarViewGroup) itemView;
        }

        public BarViewGroup getBarViewGroup() {
            return barViewGroup;
        }
    }

    static class AddButtonViewHolder extends RecyclerView.ViewHolder {
        public AddButtonViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

