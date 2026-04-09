package com.paw.key.presentation.ui.login.state

import androidx.compose.runtime.Immutable

@Immutable
data class LoginState(
    val isLoading : Boolean = false
)

sealed interface LoginSideEffect {
    data class ShowSnackBar(val message: String) : LoginSideEffect
    data object NavigateUp : LoginSideEffect
    data object NavigateNext : LoginSideEffect

    data object NavigateToHome : LoginSideEffect
    data object NavigateToSignUp : LoginSideEffect
}