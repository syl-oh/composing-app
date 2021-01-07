package com.example.composingapp.views.barviewgroupdrawer.composites

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import androidx.core.graphics.withTranslation
import com.example.composingapp.utils.interfaces.componentdrawer.ComponentDrawer
import com.example.composingapp.utils.interfaces.componentdrawer.CompositeDrawer
import com.example.composingapp.utils.interfaces.componentdrawer.LeafDrawer
import com.example.composingapp.utils.music.Music
import com.example.composingapp.views.viewtools.ViewConstants.STEM_WIDTH
import com.example.composingapp.views.barviewgroupdrawer.leaves.StemLeaf
import com.example.composingapp.views.viewtools.positiondict.NotePositionDict
import kotlin.math.roundToInt

class FlagComposite(
        val notePositionDict: NotePositionDict,
        private val paint: Paint
) : CompositeDrawer {
    private val drawers = mutableListOf<ComponentDrawer>()

    init {
        paint.strokeWidth = STEM_WIDTH
        with(notePositionDict) {
            add(StemLeaf(this, paint))
            // Eighth notes require 1 flag, sixteenth notes need 2
            if (this.note.noteLength == Music.NoteLength.EIGHTH_NOTE) {
                add(FlagLeaf(this, paint))
            } else if (this.note.noteLength == Music.NoteLength.SIXTEENTH_NOTE) {
                add(FlagLeaf(this, paint))
                add(FlagLeaf(this, paint, dy = this.singleSpaceHeight / 2))
            }
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


    /**
     * Class for drawing a single flag to the screen
     */
    private class FlagLeaf(
            val notePositionDict: NotePositionDict,
            originalPaint: Paint,
            val x: Float = notePositionDict.noteX,
            val y: Float = notePositionDict.noteY,
            val dy: Float = 0f
    ) : LeafDrawer {
        private val flagPaint: Paint = Paint(originalPaint).apply { style = Paint.Style.STROKE }
        private val flagPointsDown = notePositionDict.noteY > notePositionDict.thirdLineY
        private val noteHorzRadius = notePositionDict.noteHorizontalRadius
        private val fourthDistance = notePositionDict.octaveHeight / 2
        private val dx: Float = (noteHorzRadius * 2).toFloat()
        private val arcRect: RectF = RectF(-dx, -fourthDistance, dx, fourthDistance)
        private val originalStrokeWidth = originalPaint.strokeWidth
        private val flagWidth: Int = (notePositionDict.singleSpaceHeight / 5).roundToInt()

        override fun draw(canvas: Canvas?) {
            canvas?.apply {
                if (flagPointsDown) {
                    withTranslation(x + noteHorzRadius - originalStrokeWidth,
                            y - fourthDistance + dy) {
                        for (i in 0..flagWidth) {
                            withTranslation(y = i.toFloat()) {
                                drawArc(arcRect, -90f, 70f, false, flagPaint)
                            }
                        }
                    }
                } else {
                    withTranslation(x - noteHorzRadius + originalStrokeWidth,
                            y + fourthDistance - dy) {
                        for (i in 0..flagWidth) {
                            withTranslation(y = -i.toFloat()) {

                                drawArc(arcRect, 90f, -70f, false, flagPaint)
                            }
                        }
                    }
                }
            }
        }
    }
}