package com.example.exampleapplication.custom_views

import android.content.Context
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.SweepGradient
import android.os.Build
import android.util.AttributeSet
import android.view.View
import kotlin.math.min

class CircularDial @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var maxRadius = 0f
    private var discRadius = 0f
    private var shadowRadius = 0f
    private var blurRadius = 0f

    private lateinit var discPaint: Paint
    private lateinit var shadowPaint: Paint

    init {
        discPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            isDither = true
            color = DISC_COLOR
            style = Paint.Style.FILL_AND_STROKE
        }

        shadowPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL_AND_STROKE
            color = SHADOW_COLOR
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.withTranslation(width / 2f, height / 2f) {
            drawCircle(0f, 0f, shadowRadius, shadowPaint)
            drawCircle(0f, 0f, discRadius, discPaint)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val w = MeasureSpec.getSize(widthMeasureSpec)
        val h = MeasureSpec.getSize(heightMeasureSpec)
        maxRadius = getRadius(w, h)
        discRadius = maxRadius * DISC_RADIUS_RATIO
        shadowRadius = discRadius
        blurRadius = shadowRadius / 2f
        shadowPaint.maskFilter = BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.NORMAL)
    }

    private fun getRadius(w: Int, h: Int) = (min(w, h) / 2f) - paddingBottom

    fun Canvas.withTranslation(
        x: Float = 0.0f,
        y: Float = 0.0f,
        block: Canvas.() -> Unit
    ) {
        val checkpoint = save()
        translate(x, y)
        try {
            block()
        } finally {
            restoreToCount(checkpoint)
        }
    }

    companion object {
        const val DISC_RADIUS_RATIO = 0.4f
        const val DISC_COLOR = 0xFF212427.toInt()
        const val SHADOW_COLOR = 0xFF0c0d11.toInt()
    }
}