package com.example.composingapp.utils.viewtools.noteviewdrawer.composites

import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.graphics.withTranslation
import com.example.composingapp.utils.interfaces.ComponentDrawer
import com.example.composingapp.utils.interfaces.CompositeDrawer
import com.example.composingapp.utils.interfaces.LeafDrawer
import com.example.composingapp.utils.music.Music
import com.example.composingapp.utils.viewtools.NotePositionDict
import com.example.composingapp.utils.viewtools.noteviewdrawer.leaves.StemLeaf
import com.example.composingapp.utils.viewtools.noteviewdrawer.leaves.bases.FilledBaseLeaf
import com.example.composingapp.utils.viewtools.noteviewdrawer.leaves.bases.HollowBaseLeaf

class NoteComposite(
        private val notePositionDict: NotePositionDict,
        private val paint: Paint
) : CompositeDrawer {
    private val drawers = mutableListOf<ComponentDrawer>()

    init {
        add(FilledBaseLeaf(notePositionDict, paint))
        if (notePositionDict.note.noteLength == Music.NoteLength.WHOLE_NOTE ||
                notePositionDict.note.noteLength == Music.NoteLength.HALF_NOTE) {
            add(HollowBaseLeaf(notePositionDict, paint))
        }
        if (notePositionDict.note.noteLength != Music.NoteLength.WHOLE_NOTE) {
            add(StemLeaf(notePositionDict, paint))
        }

        val maxBarlineY: Float = notePositionDict.toneToBarlineYMap.maxByOrNull { it.value }!!.value
        val minBarlineY: Float = notePositionDict.toneToBarlineYMap.minByOrNull { it.value }!!.value
        val noteY: Float = notePositionDict.noteY

        if (noteY < minBarlineY || noteY > maxBarlineY) {
            TODO("Draw ledger lines at the correct spots and at the right time")
            add(LedgerLineLeaf(notePositionDict, paint))
        }
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

    class LedgerLineLeaf(
            val notePositionDict: NotePositionDict,
            val paint: Paint
    ) : LeafDrawer {
        val ledgerLineHalfWidth: Float = 3 * notePositionDict.noteHorizontalRadius / 2
        val noteX: Float = notePositionDict.noteX
        val noteY: Float = notePositionDict.noteY

        override fun draw(canvas: Canvas?) {
            canvas?.withTranslation(noteX, noteY) {
                canvas.drawLine(-ledgerLineHalfWidth, 0f, ledgerLineHalfWidth, 0f, paint)
            }
        }
    }
}