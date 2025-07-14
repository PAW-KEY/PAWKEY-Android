package com.paw.key.presentation.ui.login.state

import androidx.compose.runtime.Immutable

class LoginContract {
    @Immutable
    data class LoginState(
        val email: String = "",
        val password: String = "",

        val isPasswordVisible: Boolean = false
    ) {
        val isLoginValid get() = email.isNotBlank() && password.isNotBlank()
    }

    sealed class LoginSideEffect {
        data class ShowSnackBar(val message: String) : LoginSideEffect()
        data object NavigateUp: LoginSideEffect()
        data object NavigateNext: LoginSideEffect()

        data object SignInSucceed : LoginSideEffect()
        data object SignInFailed : LoginSideEffect()
    }
}