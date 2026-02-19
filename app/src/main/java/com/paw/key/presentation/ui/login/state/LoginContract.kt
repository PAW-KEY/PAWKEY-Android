package com.paw.key.presentation.ui.login.state

import androidx.compose.runtime.Immutable

@Immutable
data class LoginState(
    val email: String = "",
    val password: String = "",

    val isPasswordVisible: Boolean = false,
) {
    val isLoginValid get() = email.isNotBlank() && password.isNotBlank()
}

sealed interface LoginSideEffect {
    data class ShowSnackBar(val message: String) : LoginSideEffect
    data object NavigateUp : LoginSideEffect
    data object NavigateNext : LoginSideEffect

    data object NavigateToHome : LoginSideEffect
    data object NavigateToSignUp : LoginSideEffect
}