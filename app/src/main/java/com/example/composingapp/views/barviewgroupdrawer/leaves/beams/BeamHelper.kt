package com.example.composingapp.views.barviewgroupdrawer.leaves.beams

import android.graphics.Paint
import com.example.composingapp.utils.Line
import com.example.composingapp.utils.music.Music
import com.example.composingapp.views.NoteView
import com.example.composingapp.views.barviewgroupdrawer.leaves.StemLeaf
import com.example.composingapp.views.viewtools.positiondict.NotePositionDict
import kotlin.math.abs

object BeamHelper {
//    private const val TAG = "BeamHelper"
    /**
     *   Produces a 2D list where each element is a group of notes that satisfy the given condition
     *
     *   @param condition (Music.NoteLength -> Boolean) The condition to collect by
     *
     *   @return 2D list containing groups of NoteViews (groups.size >= 1) who meet the condition
     */
    fun List<NoteView>.onlyGroupsWithNoteLengthCondition(condition: (Music.NoteLength) -> Boolean)
            : List<List<NoteView>> {
        return when {
            this.isEmpty() -> emptyList()
            else ->(this.groupByNoteLengthCondition(condition)
                    .filter { condition(it.first().notePositionDict.note.noteLength) })
        }
    }

    /**
     *  Produces a 2D list where each element is a group of notes where each subelement either satisfies
     *     or does not satisfy a condition.
     *
     *  @param accumulator List that collects the groups that meet the condition or not: used for tail recursion
     *  @param condition (Music.NoteLength -> Boolean) The condition to collect by
     *
     *  @return 2D list containing groups of NoteViews (groups.size >= 1) who meet the condition or do not
     */
    tailrec fun List<NoteView>.groupByNoteLengthCondition(
            condition: (Music.NoteLength) -> Boolean,
            accumulator: List<List<NoteView>> = emptyList(),
    ): List<List<NoteView>> {
        // Collect all proceeding notes that have the same condition as the first
        val firstGroup: List<NoteView> = this.takeWhile {
            condition(it.notePositionDict.note.noteLength) ==
                    condition(this.first().notePositionDict.note.noteLength)
        }
        // Once we hit an element that has a different condition, store the rest of the list
        val restOfGroup: List<NoteView> = this.takeLast(this.size - firstGroup.size)
        return when {
            restOfGroup.isEmpty() -> accumulator + listOf(firstGroup)
            else -> restOfGroup.groupByNoteLengthCondition(condition, accumulator + listOf(firstGroup))
        }
    }

    /**
     *  Produces the StemDirection of a group of NoteViews that need to be beamed together
     *
     *  @param notePositionDicts List of NotePositionDicts containing coordinate information for NoteViews
     *
     *  @return StemDirection: POINTS_DOWN if half of the group or more points down individually,
     *                         and POINTS_UP otherwise
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