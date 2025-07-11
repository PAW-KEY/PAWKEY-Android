package com.paw.key.presentation.ui.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paw.key.presentation.ui.login.state.LoginContract
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class LoginViewModel @Inject constructor(

) : ViewModel() {
    private val _state = MutableStateFlow(LoginContract.LoginState())
    val state : StateFlow<LoginContract.LoginState>
        get() = _state.asStateFlow()

    private val _sideEffect = MutableStateFlow<LoginContract.LoginSideEffect?>(null)
    val sideEffect : StateFlow<LoginContract.LoginSideEffect?>
        get() = _sideEffect.asStateFlow()

    val isLoginFormValid: StateFlow<Boolean> = state.map { state ->
        state.email.isNotBlank() && state.password.isNotBlank()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(3000),
        false
    )

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