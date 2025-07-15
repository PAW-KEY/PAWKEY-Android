package com.paw.key.presentation.ui.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.paw.key.presentation.ui.login.state.LoginContract
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class LoginViewModel @Inject constructor(

) : ViewModel() {
    private val _state = MutableStateFlow(LoginContract.LoginState())
    val state : StateFlow<LoginContract.LoginState>
        get() = _state.asStateFlow()

    private val _sideEffect = MutableStateFlow<LoginContract.LoginSideEffect?>(null)
    val sideEffect : StateFlow<LoginContract.LoginSideEffect?>
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


    fun onClickSignUp(navController: NavController, email: String, password: String) {
        val encodedEmail = java.net.URLEncoder.encode(email, "UTF-8")
        val encodedPassword = java.net.URLEncoder.encode(password, "UTF-8")
        navController.navigate("signup/$encodedEmail/$encodedPassword")
    }

}