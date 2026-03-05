package com.paw.key.presentation.ui.login.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paw.key.domain.usecase.auth.LoginUseCase
import com.paw.key.presentation.ui.login.state.LoginSideEffect
import com.paw.key.presentation.ui.login.state.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState>
        get() = _state.asStateFlow()

    private val _sideEffect = MutableStateFlow<LoginSideEffect?>(null)
    val sideEffect: StateFlow<LoginSideEffect?>
        get() = _sideEffect.asStateFlow()

    fun onGoogleSignIn(
        context: Context,
    ) {
        viewModelScope.launch {
            loginUseCase.invokeGoogleLogin(context)
                .onSuccess {
                    if (it) {
                        _sideEffect.emit(LoginSideEffect.NavigateToSignUp)
                    } else {
                        _sideEffect.emit(LoginSideEffect.NavigateToHome)
                    }
                }
                .onFailure { e ->
                    Timber.e(e, "Google sign-in failed")
                }
        }
    }

    fun onKakaoSignIn(
        context: Context,
    ) {
        viewModelScope.launch {
            loginUseCase.invokeKakaoLogin(context)
                .onSuccess {
                    // isNewUser가 true이면이니 signup false는 home
                    if (it) {
                        _sideEffect.emit(LoginSideEffect.NavigateToSignUp)
                    } else {
                        _sideEffect.emit(LoginSideEffect.NavigateToHome)
                    }
                }
        }
    }

    fun onEmailChanged(email: String) {
        _state.update { it.copy(email = email) }
    }

    fun onPasswordChanged(password: String) {
        _state.update { it.copy(password = password) }
    }

    fun onPasswordVisibilityChanged() {
        _state.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }
}
