package com.example.composingapp.views

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.example.composingapp.utils.interfaces.ui.Clickable
import com.example.composingapp.views.touchhandlers.ToggleClickedHandler.toggleClicked


class ScoreLineView : RecyclerView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    companion object {
//        private const val TAG = "ScoreLineView"
    }

    override fun onInterceptTouchEvent(e: MotionEvent?): Boolean {
        when (e?.action) {
            MotionEvent.ACTION_DOWN -> resetClickedChild()
        }
        return super.onInterceptTouchEvent(e)
    }

    private fun resetClickedChild() {
        findClickedChild()?.let {
            if (it is View) {
                toggleClicked(it)
            }
        }
    }

    fun findClickedChild(): Clickable? {
        for (i in 0..childCount) {
            val child = getChildAt(i)
            // When the child is a BarViewGroup
            if (child is BarViewGroup) {
                // Loop through BarViewGroup's children and find the Clickable that is clicked
                for (i in 0..child.childCount) {
                    val barViewGroupChild = child.getChildAt(i)
                    if (barViewGroupChild is Clickable && barViewGroupChild.isClicked) {
                        return barViewGroupChild
                    }
                }
            }
        }
        // Return null if no children were clicked
        return null
    }
}