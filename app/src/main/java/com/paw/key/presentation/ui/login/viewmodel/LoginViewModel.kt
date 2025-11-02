package com.paw.key.presentation.ui.login.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paw.key.domain.repository.login.AuthRepository
import com.paw.key.presentation.ui.login.state.LoginSideEffect
import com.paw.key.presentation.ui.login.state.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState>
        get() = _state.asStateFlow()

    private val _sideEffect = MutableStateFlow<LoginSideEffect?>(null)
    val sideEffect: StateFlow<LoginSideEffect?>
        get() = _sideEffect.asStateFlow()

    fun onGoogleSignIn(
        context: Context,
        onSuccess: () -> Unit,
    ) {
        viewModelScope.launch {
            authRepository.signInWithGoogle(context)
                .onSuccess { idToken ->
                    val deviceId = getDeviceId(context)

                    authRepository.login(idToken, deviceId)
                        .onSuccess { response ->
                            onSuccess()
                        }
                        .onFailure { e ->
                            Timber.e(e, "Backend login failed")
                        }
                }
                .onFailure { e ->
                    Timber.e(e, "Google sign-in failed")
                }
        }
    }

    private fun getDeviceId(context: Context): String {
        return android.provider.Settings.Secure.getString(
            context.contentResolver,
            android.provider.Settings.Secure.ANDROID_ID
        )
    }

    fun onEmailChanged(email: String) {
        _state.value = _state.value.copy(email = email)
    }

    fun onPasswordChanged(password: String) {
        _state.value = _state.value.copy(password = password)
    }

    fun onPasswordVisibilityChanged() {
        _state.value = _state.value.copy(
            isPasswordVisible = !_state.value.isPasswordVisible
        )
    }
}