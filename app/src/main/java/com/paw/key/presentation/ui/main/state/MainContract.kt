package com.paw.key.presentation.ui.main.state

import androidx.compose.runtime.Immutable
import androidx.compose.ui.geometry.Offset

import java.util.UUID

class MainContract {
    data class MainState(
        val footprint : List<Footprint> = emptyList()
    )

    @Immutable
    data class Footprint(
        val id: String = UUID.randomUUID().toString(),
        val position: Offset = Offset(0f, 0f),
        val isVisible: Boolean = true
    )
}