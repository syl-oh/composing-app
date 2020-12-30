package com.example.composingapp.views.viewtools.barviewgroupdrawer.leaves.beams

import android.graphics.Paint
import com.example.composingapp.utils.Line
import com.example.composingapp.views.NoteView
import com.example.composingapp.views.viewtools.noteviewdrawer.leaves.StemLeaf
import com.example.composingapp.views.viewtools.positiondict.NotePositionDict
import kotlin.math.abs

object BeamHelper {
    fun findStemDirection(notePositionDicts: List<NotePositionDict>): StemLeaf.StemDirection {
        // If half of the notes point down, the entire group will point down
        if (notePositionDicts.map { it.noteY }.filter { it > notePositionDicts[0].thirdLineY }.size
                < notePositionDicts.size / 2) {
            return StemLeaf.StemDirection.POINTS_DOWN
        } else return StemLeaf.StemDirection.POINTS_UP
    }

    fun getStemX(
            noteView: NoteView,
            paint: Paint,
            stemDirection: StemLeaf.StemDirection
    ): Float {
        return with(noteView) {
            if (stemDirection == StemLeaf.StemDirection.POINTS_UP) {
                (this.x + this.notePositionDict.noteX + this.notePositionDict.noteHorizontalRadius
                        - paint.strokeWidth)
            } else {
                (this.x + this.notePositionDict.noteX - this.notePositionDict.noteHorizontalRadius
                        + paint.strokeWidth)
            }
        }
    }

    fun heightToBeam(noteView: NoteView, beamLine: Line, paint: Paint, stemDirection: StemLeaf.StemDirection): Float {
        with(noteView) {
            val noteY = this.notePositionDict.noteY
            val stemX = getStemX(this, paint, stemDirection)
            return abs(beamLine.yAt(stemX) - noteY)
        }
    }
}