package com.example.composingapp.views.viewtools

import com.example.composingapp.utils.music.Music
import com.example.composingapp.utils.music.Music.NoteLength.*
import com.example.composingapp.utils.music.Note
import kotlin.math.roundToInt

object LayoutWeightMap {
    private val layoutWeightMap = hashMapOf<Music.NoteLength, Int>(
            WHOLE_NOTE to 6,
            HALF_NOTE to 5,
            QUARTER_NOTE to 4,
            EIGHTH_NOTE to 3,
            SIXTEENTH_NOTE to 2,
    )
    private const val accidentalBonusWeight = 0f
    private const val TAG = "LayoutWeightMap"

    /**
     *  @return the assigned layout weight of a given note
     */
    @JvmStatic
    fun widthOf(note: Note, barNoteList: List<Note>, barWidth: Float): Int {
        return ((barWidth * (layoutWeightMap[note.noteLength] ?: 1) /
                barNoteList.fold(0,) { acc, note -> acc + (layoutWeightMap[note.noteLength] ?: 1) })
                .toInt())
    }
}