package com.paw.key.presentation.ui.login.viewmodel

import androidx.lifecycle.ViewModel
import com.paw.key.domain.repository.login.AuthRepository
import com.paw.key.presentation.ui.login.state.LoginSideEffect
import com.paw.key.presentation.ui.login.state.LoginState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState>
        get() = _state.asStateFlow()

    private val _sideEffect = MutableStateFlow<LoginSideEffect?>(null)
    val sideEffect: StateFlow<LoginSideEffect?>
        get() = _sideEffect.asStateFlow()

    fun onEmailChanged(email: String) {
        _state.value = _state.value.copy(
            email = email
        )
    }

    fun onPasswordChanged(password: String) {
        _state.value = _state.value.copy(
            password = password
        )
    }

    fun onPasswordVisibilityChanged() {
        _state.value = _state.value.copy(
            isPasswordVisible = !_state.value.isPasswordVisible
        )
    }


}