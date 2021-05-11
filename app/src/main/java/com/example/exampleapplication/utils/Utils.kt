package com.example.exampleapplication.utils

import android.content.res.Resources
import android.graphics.Path
import android.graphics.PointF
import android.util.TypedValue
import kotlin.math.hypot
import kotlin.math.tan

@Suppress("MagicNumber", "VariableNaming", "UnusedPrivateMember")
fun Path.addParallelogram(
    height: Int,
    width: Int,
    angle: Double,
    cornerRad: Float = 0f,
    isHorizontallyParallel: Boolean = true,
    oneSidedParallelogram: Boolean
) {
    val theta =
        if (isHorizontallyParallel) tan(Math.toRadians(angle)) else tan(Math.toRadians(angle))
    val movePath = (if (isHorizontallyParallel) height * theta else width / theta).toFloat()

    val topLeft = if (isHorizontallyParallel) PointF(0f, 0f) else PointF(0f, 0f)
    val topRight = if (isHorizontallyParallel) PointF(
        if (oneSidedParallelogram) width.toFloat() else
            width.toFloat() - movePath, 0f
    ) else PointF(
        movePath,
        (movePath * tan(Math.toRadians(angle))).toFloat()
    )
    val bottomRight =
        if (isHorizontallyParallel)
            PointF(width.toFloat(), height.toFloat()) else PointF(
            width.toFloat(), height.toFloat()
        )
    val bottomLeft = if (isHorizontallyParallel) PointF(movePath, height.toFloat()) else PointF(
        0f,

        if (oneSidedParallelogram)
            height.toFloat() else height.toFloat() - movePath
    )

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
fun getDist(p1: PointF, p2: PointF): Float = hypot(p1.x - p2.x, p1.y - p2.y)

@Suppress("MagicNumber", "VariableNaming", "UnusedPrivateMember")
fun getLineStart(p1: PointF, p2: PointF, cornerRad: Float): PointF {
    // above 0.5 means the corner rad is greater than half of the side. This means that two curves will
    // intersect. To prevent that, we cap the ratio at 0.5
    val radLengthRatio: Float = (cornerRad / getDist(p1, p2)).takeIf { it <= 0.5f } ?: 0.5f
    return PointF(
        (1.0f - radLengthRatio) * p1.x + radLengthRatio * p2.x,
        (1.0f - radLengthRatio) * p1.y + radLengthRatio * p2.y
    )
}

@Suppress("MagicNumber", "VariableNaming", "UnusedPrivateMember")
fun getLineEnd(p1: PointF, p2: PointF, cornerRad: Float): PointF {
    val radLengthRatio: Float = (cornerRad / getDist(p1, p2)).takeIf { it <= 0.5f } ?: 0.5f
    return PointF(
        radLengthRatio * p1.x + (1.0f - radLengthRatio) * p2.x,
        radLengthRatio * p1.y + (1.0f - radLengthRatio) * p2.y
    )
}


val Int.dp: Float
    get() = getDpValue(this.toFloat())
val Float.dp: Float
    get() = getDpValue(this)

fun getDpValue(value: Float): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        value.toFloat(),
        Resources.getSystem().displayMetrics
    )
}