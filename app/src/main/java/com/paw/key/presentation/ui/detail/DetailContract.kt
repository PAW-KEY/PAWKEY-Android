package com.paw.key.presentation.ui.detail

data class DetailState(
    val isMine: Boolean = false
)

sealed interface DetailSideEffect {

}