package com.example.feature.canvas.impl.presentation.canvasparams

import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import com.example.copixel.core.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@Immutable
data class CanvasParamsState(
    val width: Int = 64,
    val height: Int = 64
)

sealed interface CanvasParamsSideEffect {
    data object NavigateCanvas : CanvasParamsSideEffect
}

sealed interface CanvasParamsEvent {
    data class OnWidthChange(val width: Int) : CanvasParamsEvent
    data class OnHeightChange(val height: Int) : CanvasParamsEvent
    data object OnCreateButtonClick : CanvasParamsEvent
}

class CanvasParamsViewModel : BaseViewModel<CanvasParamsState, CanvasParamsSideEffect, CanvasParamsEvent>() {
    override val internalState: MutableStateFlow<CanvasParamsState> = MutableStateFlow(CanvasParamsState())

    override fun event(event: CanvasParamsEvent) {
        when(event){
            CanvasParamsEvent.OnCreateButtonClick -> onCreateButtonClick()
            is CanvasParamsEvent.OnHeightChange -> onHeightChange(event.height)
            is CanvasParamsEvent.OnWidthChange -> onWidthChange(event.width)
        }
    }

    private fun onWidthChange(newWidth: Int) {
        viewModelScope.launch { internalState.emit(internalState.value.copy(width = newWidth)) }
    }

    private fun onHeightChange(newHeight: Int) {
        viewModelScope.launch { internalState.emit(internalState.value.copy(height = newHeight)) }
    }

    private fun onCreateButtonClick() {
        viewModelScope.launch { internalAction.emit(CanvasParamsSideEffect.NavigateCanvas) }
    }
}