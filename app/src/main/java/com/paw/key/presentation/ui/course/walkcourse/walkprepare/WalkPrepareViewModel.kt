package com.paw.key.presentation.ui.course.walkcourse.walkprepare

import androidx.lifecycle.ViewModel
import com.paw.key.presentation.ui.course.walkcourse.walkprepare.state.WalkPrepareState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class WalkPrepareViewModel @Inject constructor(

) : ViewModel() {
    private val _state = MutableStateFlow(WalkPrepareState())
    val state = _state.asStateFlow()


}