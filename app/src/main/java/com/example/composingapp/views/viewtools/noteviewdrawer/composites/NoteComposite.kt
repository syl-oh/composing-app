package com.example.composingapp.views.viewtools.noteviewdrawer.composites

import android.graphics.Canvas
import android.graphics.Paint
import com.example.composingapp.utils.interfaces.componentdrawer.ComponentDrawer
import com.example.composingapp.utils.interfaces.componentdrawer.CompositeDrawer
import com.example.composingapp.utils.music.Music
import com.example.composingapp.views.viewtools.noteviewdrawer.leaves.FilledBaseLeaf
import com.example.composingapp.views.viewtools.noteviewdrawer.leaves.HollowBaseLeaf
import com.example.composingapp.views.viewtools.noteviewdrawer.leaves.StemLeaf
import com.example.composingapp.views.viewtools.positiondict.NotePositionDict
import com.example.composingapp.utils.music.Music.PitchClass.Accidental
import com.example.composingapp.views.viewtools.accidentals.FlatLeaf
import com.example.composingapp.views.viewtools.accidentals.SharpLeaf

class NoteComposite(
        private val notePositionDict: NotePositionDict,
        private val paint: Paint
) : CompositeDrawer {
    private val drawers = mutableListOf<ComponentDrawer>()

    init {
        // Add the base
        add(FilledBaseLeaf(notePositionDict, paint))

        // Add a hollow base, if needed
        with(notePositionDict.note) {
            if (this.noteLength == Music.NoteLength.WHOLE_NOTE ||
                    this.noteLength == Music.NoteLength.HALF_NOTE) {
                add(HollowBaseLeaf(notePositionDict, paint))
            }
            if (this.noteLength == Music.NoteLength.HALF_NOTE ||
                    this.noteLength == Music.NoteLength.QUARTER_NOTE) {
                add(StemLeaf(notePositionDict, paint))
            }

            // Add any accidentals
            with(pitchClass.accidental) {
                if (this == Accidental.SHARP) add(SharpLeaf(notePositionDict, paint))
                else if (this == Accidental.FLAT) add(FlatLeaf(notePositionDict, paint))
            }
        }


        // Add the ledger lines
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