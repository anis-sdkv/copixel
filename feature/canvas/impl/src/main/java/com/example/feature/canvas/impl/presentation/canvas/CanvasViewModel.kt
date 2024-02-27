package com.example.feature.canvas.impl.presentation.canvas

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.lifecycle.viewModelScope
import com.example.copixel.core.base.BaseViewModel
import com.example.copixel.feature.canvas.api.usecase.CreateArtUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


@Immutable
data class CanvasState(
    val gridWidth: Int = 64,
    val gridHeight: Int = 64,
    val scale: Float = 1f,
    val offset: Offset = Offset.Zero,
    val backgroundPattern: Bitmap = Bitmap.createBitmap(gridWidth, gridHeight, Bitmap.Config.ARGB_8888),
    val patternStep: Int = 1,
    val bitmap: Bitmap = Bitmap.createBitmap(gridWidth, gridHeight, Bitmap.Config.ARGB_8888),
    val showCellStrokes: Boolean = false,
)

sealed interface CanvasSideEffect {
    data object NavigateBack : CanvasSideEffect
    data object NavigateProfile : CanvasSideEffect
}

sealed interface CanvasEvent {
    data object OnBackButtonClick : CanvasEvent
    data class OnLayoutParamsChange(val scale: Float, val offset: Offset) : CanvasEvent
    data class OnPixelColorChange(val x: Int, val y: Int, val col: Color) : CanvasEvent
    data object OnDrawModeChange : CanvasEvent
    data object OnSaveButtonClick : CanvasEvent
}

class CanvasViewModel(val save: CreateArtUseCase) : BaseViewModel<CanvasState, CanvasSideEffect, CanvasEvent>() {
    override val internalState: MutableStateFlow<CanvasState> = MutableStateFlow(CanvasState())
    var pixelSizeBorders: Pair<Float, Float>? = null
    private var prevPoint: IntOffset? = null
    private val minSize = minOf(state.value.gridWidth, state.value.gridHeight)

    init {
        val newPatternStep = minSize / 4
        updateState(state.value.copy(patternStep = newPatternStep, backgroundPattern = with(state.value) {
            getBackgroundPattern(gridWidth, gridHeight, newPatternStep)
        }))
    }

    override fun event(event: CanvasEvent) {
        try {
            when (event) {
                CanvasEvent.OnBackButtonClick -> onBackButtonClick()
                is CanvasEvent.OnLayoutParamsChange -> onLayoutParamsChange(event.scale, event.offset)
                is CanvasEvent.OnPixelColorChange -> setPixelColor(event.x, event.y, event.col)
                CanvasEvent.OnDrawModeChange -> clearPrev()
                CanvasEvent.OnSaveButtonClick -> onSaveButtonClick()
            }
        } catch (e: Exception) {
            Log.e("test", e.stackTraceToString() + e.message.toString())
        }
    }

    private fun onLayoutParamsChange(scale: Float, offset: Offset) {
        updateState(state.value.copy(scale = scale, offset = offset))
    }

    private fun setPixelColor(x: Int, y: Int, color: Color) {
        updateState(state.value.copy(bitmap = state.value.bitmap.setPixel(x, y, color, prevPoint)))
        prevPoint = IntOffset(x, y)
    }

    private fun clearPrev() {
        prevPoint = null
    }

    private fun onBackButtonClick() {
        emitAction(CanvasSideEffect.NavigateBack)
    }

    private fun onSaveButtonClick() {
        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            try {
                save("no_id", state.value.bitmap)
                emitAction(CanvasSideEffect.NavigateProfile)
            } catch (e: Exception) {
                Log.e("test", e.message.toString())
            }
        }
    }
}
