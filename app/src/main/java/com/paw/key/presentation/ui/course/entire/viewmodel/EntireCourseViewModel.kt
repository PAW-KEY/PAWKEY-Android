package com.paw.key.presentation.ui.course.entire.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import com.paw.key.presentation.ui.course.entire.state.EntireCourseContract.EntireCourseState
import com.paw.key.presentation.ui.course.entire.state.EntireCourseContract.EntireCourseSideEffect
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class EntireCourseViewModel @Inject constructor(

) : ViewModel() {
    private val _state = MutableStateFlow(EntireCourseState())
    val state : StateFlow<EntireCourseState>
        get() = _state.asStateFlow()

    private val _sideEffect = MutableStateFlow<EntireCourseSideEffect?>(null)
    val sideEffect : StateFlow<EntireCourseSideEffect?>
        get() = _sideEffect.asStateFlow()

    fun updateState(reducer: EntireCourseState.() -> EntireCourseState) {
        _state.update {
            it.reducer()
        }
    }
}