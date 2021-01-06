package com.example.composingapp.views

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.example.composingapp.utils.interfaces.ui.Clickable
import com.example.composingapp.views.touchhandlers.ToggleClickedHandler.toggleClicked


class ScoreLineView : RecyclerView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    companion object {
        private const val TAG = "ScoreLineView"
    }

    override fun onInterceptTouchEvent(e: MotionEvent?): Boolean {
        // Reset the clicked status of any clicked NoteViews in the RecyclerView
        for (i in 0..childCount) {
            val child = getChildAt(i)
            // When the child is a BarViewGroup
            if (child is BarViewGroup) {
                // Loop through BarViewGroup's children and reset Clickable views that are clicked
                for (i in 0..child.childCount) {
                    val barViewGroupChild = child.getChildAt(i)
                    if (barViewGroupChild is Clickable && barViewGroupChild.isClicked) {
                        toggleClicked(barViewGroupChild)
                        barViewGroupChild.isClicked = false
                    }
                }
            }
        }
        return super.onInterceptTouchEvent(e)
    }
}