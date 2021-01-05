package com.example.composingapp.views

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

class ScoreLineView : RecyclerView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onInterceptTouchEvent(e: MotionEvent?): Boolean {
        Log.d(TAG, "onInterceptTouchEvent: ${e?.action == MotionEvent.ACTION_MOVE}")
        return super.onInterceptTouchEvent(e)
    }

    companion object {
        private const val TAG = "ScoreLineView"
    }
}