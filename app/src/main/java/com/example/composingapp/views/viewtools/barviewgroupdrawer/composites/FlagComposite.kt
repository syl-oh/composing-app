package com.example.composingapp.views.viewtools.barviewgroupdrawer.composites

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import androidx.core.graphics.withTranslation
import com.example.composingapp.utils.interfaces.ComponentDrawer
import com.example.composingapp.utils.interfaces.CompositeDrawer
import com.example.composingapp.utils.interfaces.LeafDrawer
import com.example.composingapp.utils.music.Music
import com.example.composingapp.views.viewtools.positiondict.NotePositionDict
import com.example.composingapp.views.viewtools.barviewgroupdrawer.leaves.StemLeaf

class FlagComposite(
        val notePositionDict: NotePositionDict,
        private val paint: Paint
) : CompositeDrawer {
    private val drawers = mutableListOf<ComponentDrawer>()

    init {
        with(notePositionDict) {
            add(StemLeaf(this, paint))
            if (this.note.noteLength == Music.NoteLength.EIGHTH_NOTE) {
                add(FlagLeaf(this, paint))
            } else if (this.note.noteLength == Music.NoteLength.SIXTEENTH_NOTE) {
                add(FlagLeaf(this, paint))
                add(FlagLeaf(this, paint, dy = this.singleSpaceHeight))
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


    class FlagLeaf(
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
        private val dx: Float = (noteHorzRadius * 1.5).toFloat()
        private val arcRect: RectF = RectF(-dx, -fourthDistance, dx, fourthDistance)
        private val originalStrokeWidth = originalPaint.strokeWidth

        override fun draw(canvas: Canvas?) {
            canvas?.apply {
                if (flagPointsDown) {
                    withTranslation(x + noteHorzRadius - originalStrokeWidth,
                            y - fourthDistance + dy) {
                        drawArc(arcRect, -90f, 90f, false, flagPaint)
                    }
                } else {
                    withTranslation(x - noteHorzRadius + originalStrokeWidth,
                            y + fourthDistance - dy) {
                        drawArc(arcRect, 90f, -90f, false, flagPaint)
                    }
                }
            }
        }
    }
}