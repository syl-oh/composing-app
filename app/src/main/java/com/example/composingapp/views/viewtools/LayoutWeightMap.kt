package com.example.composingapp.views.viewtools

import com.example.composingapp.utils.music.Music
import com.example.composingapp.utils.music.Music.NoteLength.*
import com.example.composingapp.utils.music.Note

object LayoutWeightMap {
    private val layoutWeightMap = hashMapOf<Music.NoteLength, Float>(
            WHOLE_NOTE to 6f,
            HALF_NOTE to 5f,
            QUARTER_NOTE to 4f,
            EIGHTH_NOTE to 3f,
            SIXTEENTH_NOTE to 2f,
    )

    private const val accidentalBonusWeight = 0.5f
    private const val TAG = "LayoutWeightMap"

    /**
     *  @return the assigned layout weight of a given note
     */
    @JvmStatic
    fun weightOf(note: Note): Float {
        val baseWeight = layoutWeightMap[note.noteLength] ?: 1F

        return if (note.pitchClass.accidental != Music.PitchClass.Accidental.NATURAL &&
                note.pitchClass.accidental != Music.PitchClass.Accidental.NONE) {
            (baseWeight + accidentalBonusWeight)
        } else baseWeight
    }

}