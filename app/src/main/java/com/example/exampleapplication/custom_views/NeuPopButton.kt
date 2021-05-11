package com.example.exampleapplication.custom_views

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.widget.TextView
import androidx.core.animation.addListener
import androidx.core.animation.doOnEnd
import com.example.exampleapplication.dp
import kotlin.math.hypot
import kotlin.math.tan

class NeuPopButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr) {

    private lateinit var shadowPaint: Paint
    private lateinit var newShadowPaint: Paint
    private lateinit var bottomShadowPath: Path
    private lateinit var rightShadowPath: Path
    private lateinit var topShadowPath: Path
    private lateinit var leftShadowPath: Path

    //main card
    private lateinit var cardPath: Path
    private lateinit var mainCardPaint: Paint
    private lateinit var mainCardRect: RectF

    private var hasPressed = false

    private var dx: Float = 0f
    private var dy: Float = 0f

    private lateinit var buttonAnimator: ValueAnimator

    init {
        mainCardPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            isDither = true
            color = Color.WHITE
            style = Paint.Style.FILL_AND_STROKE
        }

        shadowPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL_AND_STROKE
            color = Color.RED
        }

        newShadowPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL_AND_STROKE
            color = Color.BLACK
        }

        buttonAnimator = ValueAnimator.ofFloat(0f, 16f.dp)
        buttonAnimator.duration = 200L
        buttonAnimator.addUpdateListener {
            dx = it.animatedValue as Float
            dy = it.animatedValue as Float
            invalidate()
        }

        val reverseAnimator = ValueAnimator.ofFloat(16f.dp, 0f)
        reverseAnimator.duration = 200L
        reverseAnimator.addUpdateListener {
            dx = it.animatedValue as Float
            dy = it.animatedValue as Float
            invalidate()
        }

        // buttonAnimator.addListener(object : Animator.AnimatorListener {
        //     override fun onAnimationStart(animation: Animator?) {
        //     }
        //
        //     override fun onAnimationEnd(animation: Animator?) {
        //         reverseAnimator.start()
        //     }
        //
        //     override fun onAnimationCancel(animation: Animator?) {
        //     }
        //
        //     override fun onAnimationRepeat(animation: Animator?) {
        //     }
        // })

        // val animX = ObjectAnimator.ofFloat(this, "translationX", 0, 200);
        // val animY = ObjectAnimator.ofFloat(this, "translationY", 0, 200);
    }

    override fun onDraw(canvas: Canvas) {
        drawContainer(canvas)
        super.onDraw(canvas)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                buttonAnimator.start()
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                buttonAnimator.reverse()
            }
        }
        // invalidate()
        return true
    }

    private fun drawContainer(canvas: Canvas) {
        canvas.withTranslation(16f.dp, 16f.dp) {
            this.drawPath(topShadowPath, newShadowPaint.apply { color = leftShadow })
        }
        canvas.withTranslation(16f.dp, 16f.dp) {
            this.drawPath(leftShadowPath, newShadowPaint.apply { color = topShadow })
        }
        canvas.withTranslation(dx, dy + mainCardRect.height().toFloat()) {
            this.drawPath(bottomShadowPath, shadowPaint.apply {
                color = 0xff2b0051.toInt()
            })
        }
        canvas.withTranslation(dx + mainCardRect.width(), dy) {
            this.drawPath(rightShadowPath, shadowPaint.apply {
                color = 0xff460085.toInt()
            })
        }
        canvas.withTranslation(dx, dy) {
            this.drawPath(cardPath, mainCardPaint.apply { color = 0xff5600a7.toInt() })
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val w = MeasureSpec.getSize(widthMeasureSpec) - 16f.dp.toInt()
        val h = MeasureSpec.getSize(heightMeasureSpec) - 16f.dp.toInt()
        mainCardRect = RectF(0f, 0f, w.toFloat(), h.toFloat())
        cardPath = Path().apply {
            addRect(mainCardRect, Path.Direction.CW)
        }

        bottomShadowPath = Path().apply {
            this.addHorizontalParallelogram(16f.dp.toInt(), w, 45.toDouble(), bottomLeftMoved = false)
        }
        rightShadowPath = Path().apply {
            this.addVerticalParallelogram(h, 16f.dp.toInt(), 45.toDouble(), topRightMoved = false)
        }

        topShadowPath = Path().apply {
            this.addTopParallelogram(16f.dp.toInt(), w, 45.toDouble())
        }

        leftShadowPath = Path().apply {
            this.addLeftParallelogram(h, 16f.dp.toInt(), 45.toDouble())
        }
    }

    companion object {
        val topShadow = 0xff107c66.toInt()
        val leftShadow = 0xff0b4d40.toInt()
    }

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

    fun Path.addHorizontalParallelogram(
        height: Int,
        width: Int,
        angle: Double,
        cornerRad: Float = 0f,
        bottomLeftMoved: Boolean = true
    ) {

        val movePath = (height * tan(Math.toRadians(angle))).toFloat()

        val topLeft = if (bottomLeftMoved) PointF(movePath, 0f) else PointF(0f, 0f)
        val topRight = PointF(width.toFloat(), 0f)
        val bottomRight = if (bottomLeftMoved) PointF(width.toFloat() - movePath, height.toFloat()) else PointF(width + movePath, height.toFloat())
        val bottomLeft = if (bottomLeftMoved) PointF(0f, height.toFloat()) else PointF(movePath, height.toFloat())

        val points = listOf(topLeft, topRight, bottomRight, bottomLeft, topLeft)

        for (i in 0 until points.size - 1) {
            val lineStart = getLineStart(points[i], points[i + 1], cornerRad)
            val lineEnd = getLineEnd(points[i], points[i + 1], cornerRad)

            if (i == 0) {
                moveTo(lineStart.x, lineStart.y)
            } else {
                quadTo(points[i].x, points[i].y, lineStart.x, lineStart.y)
            }

            lineTo(lineEnd.x, lineEnd.y)
        }

        val topLineStart = getLineStart(topLeft, topRight, cornerRad)
        quadTo(topLeft.x, topLeft.y, topLineStart.x, topLineStart.y)
        close()
    }

    fun Path.addVerticalParallelogram(
        height: Int,
        width: Int,
        angle: Double,
        cornerRad: Float = 0f,
        topRightMoved: Boolean = true
    ) {

        val movePath = (width * tan(Math.toRadians(angle))).toFloat()

        val topLeft = if (topRightMoved) PointF(movePath, 0f) else PointF(0f, 0f)
        val topRight = if (topRightMoved) PointF(width.toFloat(), 0f) else PointF(width.toFloat(), movePath)
        val bottomRight = if (topRightMoved) PointF(width.toFloat() - movePath, height.toFloat()) else PointF(width.toFloat(), height + movePath)
        val bottomLeft = if (topRightMoved) PointF(0f, height.toFloat()) else PointF(0f, height.toFloat())

        val points = listOf(topLeft, topRight, bottomRight, bottomLeft, topLeft)

        for (i in 0 until points.size - 1) {
            val lineStart = getLineStart(points[i], points[i + 1], cornerRad)
            val lineEnd = getLineEnd(points[i], points[i + 1], cornerRad)

            if (i == 0) {
                moveTo(lineStart.x, lineStart.y)
            } else {
                quadTo(points[i].x, points[i].y, lineStart.x, lineStart.y)
            }

            lineTo(lineEnd.x, lineEnd.y)
        }

        val topLineStart = getLineStart(topLeft, topRight, cornerRad)
        quadTo(topLeft.x, topLeft.y, topLineStart.x, topLineStart.y)
        close()
    }

    fun Path.addTopParallelogram(
        height: Int,
        width: Int,
        angle: Double,
        cornerRad: Float = 0f
    ) {
        val movePath = (height * tan(Math.toRadians(angle))).toFloat()

        val topLeft = PointF(-movePath, -height.toFloat())
        val topRight = PointF(width - movePath, -height.toFloat())
        val bottomRight = PointF(width.toFloat(), 0f)
        val bottomLeft = PointF(0f, 0f)

        val points = listOf(topLeft, topRight, bottomRight, bottomLeft, topLeft)

        for (i in 0 until points.size - 1) {
            val lineStart = getLineStart(points[i], points[i + 1], cornerRad)
            val lineEnd = getLineEnd(points[i], points[i + 1], cornerRad)

            if (i == 0) {
                moveTo(lineStart.x, lineStart.y)
            } else {
                quadTo(points[i].x, points[i].y, lineStart.x, lineStart.y)
            }

            lineTo(lineEnd.x, lineEnd.y)
        }

        val topLineStart = getLineStart(topLeft, topRight, cornerRad)
        quadTo(topLeft.x, topLeft.y, topLineStart.x, topLineStart.y)
        close()
    }

    fun Path.addLeftParallelogram(
        height: Int,
        width: Int,
        angle: Double,
        cornerRad: Float = 0f
    ) {

        val movePath = (width * tan(Math.toRadians(angle))).toFloat()

        val topLeft = PointF(-width.toFloat(), -movePath)
        val topRight = PointF(0f, 0f)
        val bottomRight = PointF(0f, height.toFloat())
        val bottomLeft = PointF(-width.toFloat(), height - movePath)

        val points = listOf(topLeft, topRight, bottomRight, bottomLeft, topLeft)

        for (i in 0 until points.size - 1) {
            val lineStart = getLineStart(points[i], points[i + 1], cornerRad)
            val lineEnd = getLineEnd(points[i], points[i + 1], cornerRad)

            if (i == 0) {
                moveTo(lineStart.x, lineStart.y)
            } else {
                quadTo(points[i].x, points[i].y, lineStart.x, lineStart.y)
            }

            lineTo(lineEnd.x, lineEnd.y)
        }

        val topLineStart = getLineStart(topLeft, topRight, cornerRad)
        quadTo(topLeft.x, topLeft.y, topLineStart.x, topLineStart.y)
        close()
    }

    @Suppress("MagicNumber", "VariableNaming", "UnusedPrivateMember")
    private fun getDist(p1: PointF, p2: PointF): Float = hypot(p1.x - p2.x, p1.y - p2.y)

    @Suppress("MagicNumber", "VariableNaming", "UnusedPrivateMember")
    private fun getLineStart(p1: PointF, p2: PointF, cornerRad: Float): PointF {
        // above 0.5 means the corner rad is greater than half of the side. This means that two curves will
        // intersect. To prevent that, we cap the ratio at 0.5
        val radLengthRatio: Float = (cornerRad / getDist(p1, p2)).takeIf { it <= 0.5f } ?: 0.5f
        return PointF(
            (1.0f - radLengthRatio) * p1.x + radLengthRatio * p2.x,
            (1.0f - radLengthRatio) * p1.y + radLengthRatio * p2.y
        )
    }

    @Suppress("MagicNumber", "VariableNaming", "UnusedPrivateMember")
    private fun getLineEnd(p1: PointF, p2: PointF, cornerRad: Float): PointF {
        val radLengthRatio: Float = (cornerRad / getDist(p1, p2)).takeIf { it <= 0.5f } ?: 0.5f
        return PointF(
            radLengthRatio * p1.x + (1.0f - radLengthRatio) * p2.x,
            radLengthRatio * p1.y + (1.0f - radLengthRatio) * p2.y
        )
    }
}