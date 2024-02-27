package com.example.feature.canvas.impl.presentation.canvas

import android.graphics.Bitmap
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.calculateCentroid
import androidx.compose.foundation.gestures.calculateCentroidSize
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.gestures.calculateZoom
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.positionChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import kotlin.math.abs
import kotlin.math.sign

fun Offset.toScreen(offset: Offset, scale: Float): Offset = (this + offset) * scale
fun Offset.toWorld(offset: Offset, scale: Float): Offset = this / scale - offset
fun Offset.toIntOffset() = IntOffset(this.x.toInt(), this.y.toInt())
fun Size.toIntSize() = IntSize(this.width.toInt(), this.height.toInt())
fun Path.moveTo(offset: Offset) = this.moveTo(offset.x, offset.y)
fun Path.lineTo(offset: Offset) = this.lineTo(offset.x, offset.y)

fun Color.Companion.fromArgb(color: Int): Color {
    val a: Int = color shr 24 and 0xff
    val r: Int = color shr 16 and 0xff
    val g: Int = color shr 8 and 0xff
    val b: Int = color and 0xff
    return Color(r, g, b, a)
}

fun getSize(topLeft: Offset, bottomEnd: Offset): Size {
    val size = bottomEnd - topLeft
    return Size(size.x, size.y)
}

fun calculatePixelFromWorldPosition(worldXY: Offset): IntOffset =
    Offset(worldXY.x / defaultPixelSize, worldXY.y / defaultPixelSize).toIntOffset()

fun interpolate(start: IntOffset, end: IntOffset, color: Color, pixels: IntArray, width: Int, height: Int) {
    val sizeX = abs(end.x - start.x)
    val sizeY = abs(end.y - start.y)
    if (sizeX > sizeY) {
        val stepY = (end.y - start.y) / sizeX.toFloat()
        var i = start.y.toFloat()
        var j = start.x
        val dir = (end.x - start.x).sign
        do {
            pixels[i.toInt() * width + j] = color.toArgb()
            i += stepY
            j += dir
        } while (i.toInt() in 0 until height && j in 0 until width && j != end.x)
    } else {
        val stepX = (end.x - start.x) / sizeY.toFloat()
        var i = start.y
        var j = start.x.toFloat()
        val dir = (end.y - start.y).sign
        do {
            pixels[i * width + j.toInt()] = color.toArgb()
            i += dir
            j += stepX
        } while (i in 0 until height && j.toInt() in 0 until width && i != end.y)
    }
}


fun getBackgroundPattern(width: Int, height: Int, step: Int): Bitmap {
    val pixels = IntArray(width * height)
    for (i in 0 until height step step) {
        for (j in 0 until width step step) {
            val col = if ((i + j) / step % 2 == 0) Color.Gray.copy(0.1f) else Color.Gray.copy(0.4f)

            for (ii in i until Integer.min(i + step, height))
                for (jj in j until Integer.min(j + step, width))
                    pixels[ii + jj * width] = col.toArgb()
        }
    }
    return Bitmap.createBitmap(pixels, 0, width, width, height, Bitmap.Config.ARGB_8888)
}

fun Bitmap.setPixel(x: Int, y: Int, color: Color, prevPoint: IntOffset?): Bitmap {
    val bitmap = this.copy(Bitmap.Config.ARGB_8888, true)
    val pixels = IntArray(bitmap.width * bitmap.height)
    bitmap.getPixels(pixels, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
    val curr = IntOffset(x, y)
    if (prevPoint == null)
        pixels[y * bitmap.width + x] = color.toArgb()
    else {
        interpolate(prevPoint, curr, color, pixels, bitmap.width, bitmap.height)
    }
    bitmap.setPixels(pixels, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
    return bitmap
}

@Composable
fun Dp.dpToPx() = with(LocalDensity.current) { this@dpToPx.toPx() }

@Composable
fun Int.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }

suspend fun PointerInputScope.customDetectTransformGestures(
    panZoomLock: Boolean = false,
    onTransformGesture: (centroid: Offset, pan: Offset, zoom: Float) -> Unit,
    touchCanvas: (touchPoint: Offset) -> Boolean,
    onCanvasDrag: (position: Offset) -> Unit,
    onDrawModeChange: (draw: Boolean) -> Unit
) {
    awaitEachGesture {
        var rotation = 0f
        var zoom = 1f
        var pan = Offset.Zero
        var pastTouchSlop = false
        val touchSlop = viewConfiguration.touchSlop
        var lockedToPanZoom = false

        var drawMode = false
        var transformMode = false

        awaitFirstDown(requireUnconsumed = false)
        do {
            val event = awaitPointerEvent()
            val canceled = event.changes.any { it.isConsumed }
            if (!canceled) {
                val touchPoint = event.changes.first()
                if (drawMode) {
                    if (event.type == PointerEventType.Release || event.changes.size > 1) {
                        drawMode = false
                        onDrawModeChange(false)
                        continue
                    } else
                        onCanvasDrag(touchPoint.position)
                } else {
                    if (event.changes.size == 1) {
                        if (event.type == PointerEventType.Release) {
                            if (transformMode) transformMode = false
                            else {
                                onDrawModeChange(true)
                                onCanvasDrag(touchPoint.position)
                                onDrawModeChange(false)
                            }
                        } else if (!transformMode) {
                            drawMode = true
                            onDrawModeChange(true)
                            onCanvasDrag(touchPoint.position)
                        }
                    } else {
                        if (!transformMode) transformMode = true
                    }
                }

                if (transformMode)
                    calculateTransform(
                        TransformParams(event, zoom, pan, touchSlop, pastTouchSlop),
                        onTransformGesture
                    )?.let {
                        zoom = it.zoom
                        pan = it.pan
                        pastTouchSlop = it.pastTouchSlop
                    }
            }
        } while (!canceled && event.changes.any { it.pressed })
    }
}

private data class TransformParams(
    val event: PointerEvent,
    val zoom: Float,
    val pan: Offset,
    val touchSlop: Float,
    val pastTouchSlop: Boolean
)

private data class TransformResult(val zoom: Float, val pan: Offset, var pastTouchSlop: Boolean = false)

private fun calculateTransform(
    params: TransformParams,
    onTransformGesture: (centroid: Offset, pan: Offset, zoom: Float) -> Unit
): TransformResult? = with(params) {
    val zoomChange = event.calculateZoom()
    val panChange = event.calculatePan()
    var result: TransformResult? = null

    if (!pastTouchSlop) {
        result = TransformResult(zoom * zoomChange, pan + panChange)

        val centroidSize = event.calculateCentroidSize(useCurrent = false)
        val zoomMotion = abs(1 - zoom) * centroidSize
        val panMotion = pan.getDistance()

        if (zoomMotion > touchSlop || panMotion > touchSlop)
            result.pastTouchSlop = true
    }

    if (pastTouchSlop) {
        val centroid = event.calculateCentroid(useCurrent = false)
        if (zoomChange != 1f || panChange != Offset.Zero)
            onTransformGesture(centroid, panChange, zoomChange)

        event.changes.forEach {
            if (it.positionChanged()) {
                it.consume()
            }
        }
    }
    return@with result
}
