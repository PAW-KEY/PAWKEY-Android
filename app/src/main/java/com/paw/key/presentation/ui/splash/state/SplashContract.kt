package com.paw.key.presentation.ui.splash.state

import androidx.compose.runtime.Immutable

class SplashContract {

    @Immutable
    data class SplashState(
        val isLoading: Boolean = true,
    )

    sealed class SplashSideEffect {
        data object NavigateToLogin : SplashSideEffect()
    }
}
