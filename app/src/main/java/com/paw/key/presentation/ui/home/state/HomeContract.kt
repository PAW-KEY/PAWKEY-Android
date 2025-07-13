package com.paw.key.presentation.ui.home.state

import androidx.compose.runtime.Immutable


class HomeContract {
    @Immutable
    data class HomeState(
        val isLocationMenuVisible: Boolean = false,
        val isVisible: Boolean = false,
        val selectedLocation: String = "",

        )
}
