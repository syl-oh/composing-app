package com.example.composingapp.views.viewtools.barviewgroupdrawer.leaves.beams

import android.graphics.Paint
import com.example.composingapp.utils.Line
import com.example.composingapp.views.NoteView
import com.example.composingapp.views.viewtools.noteviewdrawer.leaves.StemLeaf
import com.example.composingapp.views.viewtools.positiondict.NotePositionDict
import kotlin.math.abs

object BeamHelper {
    /**
     *  Produces the StemDirection of a group of NoteViews that need to be beamed together
     *
     *  @param notePositionDicts List of NotePositionDicts containing coordinate information for NoteViews
     *
     *  @return StemDirection according to musical conventions
     */
    fun findStemDirection(notePositionDicts: List<NotePositionDict>): StemLeaf.StemDirection {
        // If half of the notes point down, the entire group will point down
        if (notePositionDicts.map { it.noteY }.filter { it > notePositionDicts[0].thirdLineY }.size
                < notePositionDicts.size / 2) {
            return StemLeaf.StemDirection.POINTS_DOWN
        } else return StemLeaf.StemDirection.POINTS_UP
    }


    /**
     * Produces the x coordinate of the NoteView's stem
     *
     * @param noteView NoteView whose stem's x-coordinate to calculate
     * @param paint Paint used to account for stroke width
     * @param stemDirection StemDirection used for finding the x coordinate of the NoteView's stem
     *
     * @return Float representing the x-coordinate of the NoteView's stem
     */
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

    /**
     *  Produces the distance between the NoteView's coordinates and its beam's Line
     *
     *  @param noteView NoteView to find the distance from
     *  @param beamLine Line to find the distance to
     *  @param paint Paint used to account for stroke width
     *  @param stemDirection StemDirection used for finding the x coordinate of the NoteView's stem
     *
     *  @return Float representing the distance from the NoteView to its beam (Line)
     */
    fun heightToBeam(
            noteView: NoteView,
            beamLine: Line,
            paint: Paint,
            stemDirection: StemLeaf.StemDirection
    ): Float {
        with(noteView) {
            return abs(beamLine.yAt(getStemX(this, paint, stemDirection))
                    - this.notePositionDict.noteY)
        }
    }
}