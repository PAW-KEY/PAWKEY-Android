package com.paw.key.presentation.ui.mypage.courseinfo.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.paw.key.core.util.UiState
import com.paw.key.domain.repository.mypage.MypageRepository
import com.paw.key.presentation.ui.mypage.courseinfo.model.CourseInfoSideEffect
import com.paw.key.presentation.ui.mypage.courseinfo.model.CourseInfoSideEffect.ShowSnackBar
import com.paw.key.presentation.ui.mypage.courseinfo.model.CourseInfoState
import com.paw.key.presentation.ui.mypage.courseinfo.model.CourseType
import com.paw.key.presentation.ui.mypage.courseinfo.model.toCourseData
import com.paw.key.presentation.ui.mypage.courseinfo.navigation.CourseInfoNavRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourseInfoViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val mypageRepository: MypageRepository,
) : ViewModel() {

    val courseType: CourseType = savedStateHandle.toRoute<CourseInfoNavRoute>().courseType

    private val _state = MutableStateFlow(CourseInfoState())
    val state = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<CourseInfoSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    init {
        fetchCourses()
    }

    fun fetchCourses() {
        viewModelScope.launch {
            _state.update { it.copy(courses = UiState.Loading) }

            val result = when (courseType) {
                CourseType.MyCourse     -> mypageRepository.getMyRoutes().map { list -> list.map { it.toCourseData() } }
                CourseType.AllCourse    -> mypageRepository.getLikedPosts().map { list -> list.map { it.toCourseData() } }
                CourseType.ReviewCourse -> mypageRepository.getMyReviews().map { list -> list.map { it.toCourseData() } }
            }

            result
                .onSuccess { courses ->
                    _state.update {
                        it.copy(courses = if (courses.isEmpty()) UiState.Empty else UiState.Success(courses))
                    }
                }
                .onFailure { e ->
                    _state.update { it.copy(courses = UiState.Failure(e.message ?: "불러오기 실패")) }
                    _sideEffect.emit(ShowSnackBar(e.message ?: "불러오기 실패"))
                }
        }
    }
}