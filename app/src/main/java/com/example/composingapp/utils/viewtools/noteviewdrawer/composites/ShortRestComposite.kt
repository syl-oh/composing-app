package com.example.composingapp.utils.viewtools.noteviewdrawer.composites

import android.graphics.Canvas
import android.graphics.Paint
import com.example.composingapp.utils.interfaces.ComponentDrawer
import com.example.composingapp.utils.interfaces.CompositeDrawer
import com.example.composingapp.utils.interfaces.LeafDrawer

class ShortRestComposite(
        val noteComposite: NoteComposite,
        val paint: Paint
) : CompositeDrawer {


    override fun draw(canvas: Canvas?) {
        TODO("Not yet implemented")
    }

    override fun add(drawerComponent: ComponentDrawer?) {
        TODO("Not yet implemented")
    }

    override fun remove(drawerComponent: ComponentDrawer?) {
        TODO("Not yet implemented")
    }


    class ShortRestLeaf : LeafDrawer {
        override fun draw(canvas: Canvas?) {
            TODO("Not yet implemented")
        }
    }
}