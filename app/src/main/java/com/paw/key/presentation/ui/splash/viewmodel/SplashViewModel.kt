package com.paw.key.presentation.ui.splash.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paw.key.presentation.ui.splash.state.SplashContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(SplashContract.SplashState())
    val state: StateFlow<SplashContract.SplashState>
        get() = _state.asStateFlow()

    private val _sideeffect = MutableSharedFlow<SplashContract.SplashSideEffect>()
    val sideeffect: SharedFlow<SplashContract.SplashSideEffect>
        get() = _sideeffect.asSharedFlow()


    init {
        viewModelScope.launch {
            delay(1800)
            _sideeffect.emit(SplashContract.SplashSideEffect.NavigateToLogin)
        }
    }
}
