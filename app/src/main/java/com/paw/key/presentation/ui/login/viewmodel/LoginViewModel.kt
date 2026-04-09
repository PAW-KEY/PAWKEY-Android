package com.paw.key.presentation.ui.login.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paw.key.domain.repository.localstorage.LocalStorageRepository
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
    private val loginUseCase: LoginUseCase,
    private val localStorageRepository: LocalStorageRepository,
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
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            loginUseCase.invokeGoogleLogin(context)
                .onSuccess {
                    localStorageRepository.saveUserProvider("GOOGLE")

                    if (it) {
                        _sideEffect.emit(LoginSideEffect.NavigateToSignUp)
                    } else {
                        _sideEffect.emit(LoginSideEffect.NavigateToHome)
                    }

                    _state.update { it.copy(isLoading = false) }
                }
                .onFailure { e ->
                    Timber.e(e, "Google sign-in failed")
                }
        }
    }

    fun onKakaoSignIn(
        context: Context,
    ) {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            loginUseCase.invokeKakaoLogin(context)
                .onSuccess {
                    localStorageRepository.saveUserProvider("KAKAO")
                    // isNewUser가 true이면이니 signup false는 home
                    if (it) {
                        _sideEffect.emit(LoginSideEffect.NavigateToSignUp)
                    } else {
                        _sideEffect.emit(LoginSideEffect.NavigateToHome)
                    }

                    _state.update { it.copy(isLoading = false) }
                }
                .onFailure { e ->
                    Timber.e(e, "Kakao sign-in failed")
                }
        }
    }
}
