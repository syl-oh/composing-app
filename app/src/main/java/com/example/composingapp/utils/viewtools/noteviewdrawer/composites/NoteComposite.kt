package com.example.composingapp.utils.viewtools.noteviewdrawer.composites

import android.graphics.Canvas
import android.graphics.Paint
import android.util.Log
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
        add(LedgerLineComposite(notePositionDict, paint))
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


    companion object {
        private const val TAG = "NoteComposite"
    }
}