package com.paw.key.presentation.ui.course.walkcourse.walkcomplete

import androidx.lifecycle.ViewModel
import com.paw.key.presentation.ui.course.walkcourse.walkcomplete.state.WalkCompleteState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class WalkCompleteViewModel @Inject constructor(
) : ViewModel() {
    private val _state = MutableStateFlow(WalkCompleteState())
    val state: StateFlow<WalkCompleteState> = _state.asStateFlow()


}
