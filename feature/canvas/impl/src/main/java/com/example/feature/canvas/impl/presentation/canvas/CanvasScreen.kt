package com.example.feature.canvas.impl.presentation.canvas

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.copixel.feature.profile.api.destinations.ProfileDestination
import org.koin.androidx.compose.koinViewModel

const val defaultPixelSize = 4f

@Composable
fun CanvasScreen(navController: NavController, viewModel: CanvasViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val action by viewModel.action.collectAsStateWithLifecycle(null)
    val eventHandler = viewModel.eventHandler

    viewModel.pixelSizeBorders = Pair(50.dp.dpToPx(), 100.dp.dpToPx())

    LaunchedEffect(action) {
        when (action) {
            CanvasSideEffect.NavigateBack -> navController.navigateUp()
            CanvasSideEffect.NavigateProfile -> navController.navigate(ProfileDestination.defaultRoute)
            null -> Unit
        }
    }
    val canvasSize = Size(state.gridWidth * defaultPixelSize, state.gridHeight * defaultPixelSize)
    val topLeft by remember { derivedStateOf { Offset.Zero.toScreen(state.offset, state.scale) } }
    val bottomEnd by remember {
        derivedStateOf { Offset(canvasSize.width, canvasSize.height).toScreen(state.offset, state.scale) }
    }
    var drawMode by remember { mutableStateOf(false) }
    Box {
        Canvas(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
                .pointerInput(Unit) {
                    customDetectTransformGestures(
                        true,
                        onTransformGesture = { centroid, pan, zoom ->
                            Log.i("test", "${state.scale} ${state.offset}")
                            val newScale = (state.scale * zoom).coerceIn(0.2f, 32f)
                            eventHandler.invoke(
                                CanvasEvent.OnLayoutParamsChange(
                                    newScale,
                                    state.offset + (pan - centroid) / state.scale + centroid / newScale
                                )
                            )

                        },
                        touchCanvas = { touchOffset ->
                            val result = touchOffset.x > topLeft.x && touchOffset.y > topLeft.y &&
                                    touchOffset.x < bottomEnd.x && touchOffset.y < bottomEnd.y
                            result
                        },
                        onCanvasDrag = { position ->
                            val pixel = calculatePixelFromWorldPosition(position.toWorld(state.offset, state.scale))
                            eventHandler.invoke(CanvasEvent.OnPixelColorChange(pixel.x, pixel.y, Color.Black))
                        },
                        onDrawModeChange = {
                            drawMode = it
                            eventHandler.invoke(CanvasEvent.OnDrawModeChange)
                        }
                    )
                }
        ) {
            val gridStep = Offset(
                (bottomEnd.x - topLeft.x - 1) / state.gridWidth,
                (bottomEnd.y - topLeft.y - 1) / state.gridHeight
            )

            val imageOffset = topLeft.toIntOffset()
            val imageSize = getSize(topLeft, bottomEnd).toIntSize()
            drawImage(
                image = state.backgroundPattern.asImageBitmap(),
                dstOffset = imageOffset,
                dstSize = imageSize,
                filterQuality = FilterQuality.None
            )

            drawImage(
                image = state.bitmap.asImageBitmap(),
                dstOffset = imageOffset,
                dstSize = imageSize,
                filterQuality = FilterQuality.None
            )
            if (state.scale > 3f) {
                val gridPath = Path().apply {
                    var left = topLeft.x
                    while (left <= bottomEnd.x) {
                        moveTo(Offset(left, topLeft.y))
                        lineTo(Offset(left, bottomEnd.y))
                        left += gridStep.x
                    }

                    var top = topLeft.y
                    while (top <= bottomEnd.y) {
                        moveTo(Offset(topLeft.x, top))
                        lineTo(Offset(bottomEnd.x, top))
                        top += gridStep.y
                    }
                }
                drawPath(gridPath, Color.Gray, style = Stroke(1f))
            }
            drawCircle(Color.Cyan, radius = 50 * if (drawMode) 1f else 0f, center = Offset.Zero)
        }
    }
}
