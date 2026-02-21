package com.paw.key.presentation.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import com.paw.key.presentation.ui.main.state.MainContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@Deprecated("발자국 안쓸거임")
@HiltViewModel
class MainViewModel @Inject constructor(
) : ViewModel() {
    private val _state = MutableStateFlow(MainContract.MainState())
    val state : StateFlow<MainContract.MainState>
        get() = _state.asStateFlow()

    fun addFootprint(footprint: MainContract.Footprint) {
        _state.value = _state.value.copy(
            footprint = _state.value.footprint + footprint
        )
    }

    fun removeFootprint(footprint: MainContract.Footprint) {
        _state.value = _state.value.copy(
            footprint = _state.value.footprint - footprint
        )
    }
}