package com.paw.key.core.extension

import com.paw.key.core.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

inline fun <T> MutableStateFlow<UiState<T>>.updateSuccess(
    crossinline onUpdate: (T) -> T
) {
    update { currentState ->
        if (currentState is UiState.Success) {
            currentState.copy(data = onUpdate(currentState.data))
        } else {
            currentState
        }
    }
}

inline fun <T> MutableStateFlow<UiState<T>>.updateOrCreate(
    default: () -> T,
    crossinline onUpdate: (T) -> T
) {
    update { currentState ->
        when (currentState) {
            is UiState.Loading -> UiState.Success(onUpdate(default()))
            is UiState.Success -> currentState.copy(data = onUpdate(currentState.data))
            else -> currentState
        }
    }
}