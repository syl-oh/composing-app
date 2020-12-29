package com.example.composingapp.views.viewtools.noteviewdrawer.composites

import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.graphics.withTranslation
import com.example.composingapp.utils.interfaces.ComponentDrawer
import com.example.composingapp.utils.interfaces.CompositeDrawer
import com.example.composingapp.utils.interfaces.LeafDrawer
import com.example.composingapp.views.viewtools.positiondict.NotePositionDict
import kotlin.math.ceil
import kotlin.math.floor

class LedgerLineComposite(
        notePositionDict: NotePositionDict,
        paint: Paint
) : CompositeDrawer {
    private val drawers = mutableListOf<ComponentDrawer>()

    init {
        addNeededLedgerLineLeaves(notePositionDict, paint)
    }

    override fun draw(canvas: Canvas?) {
        drawers.map { it.draw(canvas) }
    }

    override fun add(drawerComponent: ComponentDrawer) {
        drawers.add(drawerComponent)
    }

    override fun remove(drawerComponent: ComponentDrawer) {
        drawers.remove(drawerComponent)
    }

    private fun addNeededLedgerLineLeaves(notePositionDict: NotePositionDict, paint: Paint) {
        val barlineYAt: (Int) -> Float? =
                { i -> notePositionDict.toneToBarlineYMap[notePositionDict.clef.barlineTones[i]]!!}
        val topBarlineY: Float? =  barlineYAt(4)
        val bottomBarlineY: Float? = barlineYAt(0)
        val singleSpaceHeight: Float = notePositionDict.singleSpaceHeight

        topBarlineY?.let {
            if (notePositionDict.noteY < it) {
                var currentY = it - singleSpaceHeight
                while (floor(notePositionDict.noteY) <= currentY) {
                    add(LedgerLineLeaf(notePositionDict, paint, y = currentY))
                    currentY -= singleSpaceHeight
                }
            }
        }

        bottomBarlineY?.let {
            if (notePositionDict.noteY > it) {
                var currentY = it + singleSpaceHeight
                while (ceil(notePositionDict.noteY) >= currentY) {
                    add(LedgerLineLeaf(notePositionDict, paint, y = currentY))
                    currentY += singleSpaceHeight
                }
            }
        }
    }

    class LedgerLineLeaf(
            val notePositionDict: NotePositionDict,
            val paint: Paint,
            val x: Float = notePositionDict.noteX,
            val y: Float = notePositionDict.noteY,
    ) : LeafDrawer {
        private val ledgerLineHalfWidth: Float = 3 * notePositionDict.noteHorizontalRadius / 2

        override fun draw(canvas: Canvas?) {
            canvas?.withTranslation(x, y) {
                canvas.drawLine(-ledgerLineHalfWidth, 0f, ledgerLineHalfWidth, 0f, paint)
            }
        }

        companion object {
            private const val TAG = "LedgerLineLeaf"
        }
    }

    companion object {
        private const val TAG = "LedgerLineComposite"
    }
}