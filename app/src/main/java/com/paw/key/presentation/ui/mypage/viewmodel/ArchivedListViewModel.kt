package com.paw.key.presentation.ui.mypage.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paw.key.core.designsystem.component.CourseCard
import com.paw.key.domain.repository.ArchivedListRepository
import com.paw.key.domain.repository.SavedListRepository
import com.paw.key.presentation.ui.mypage.state.ArchivedListSideEffect
import com.paw.key.presentation.ui.mypage.state.ArchivedListState
import com.paw.key.presentation.ui.mypage.state.MyPageSideEffect
import com.paw.key.presentation.ui.mypage.state.PetProfileSideEffect.NavigateNext
import com.paw.key.presentation.ui.mypage.state.SavedListSideEffect
import com.paw.key.presentation.ui.mypage.state.SavedListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArchivedListViewModel @Inject constructor(
    private val archivedListRepository: ArchivedListRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ArchivedListState())
    val state: StateFlow<ArchivedListState> = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<ArchivedListSideEffect>()
    val sideEffect: MutableSharedFlow<ArchivedListSideEffect> = _sideEffect

    fun getArchivedList(userId: Int) {
        viewModelScope.launch {
            archivedListRepository.getArchivedList(userId)
                .onSuccess { result ->
                    _state.update {
                        it.copy(
                            courseList = result.posts
                        )
                    }
                }
                .onFailure { e ->
                    Log.e("ArchivedListViewModel", "저장한 게시물 불러오기 실패", e)
                    _sideEffect.emit(ArchivedListSideEffect.ShowSnackBar(e.message ?: "알 수 없는 오류"))
                }
        }
    }
    fun toggleLike(postId: Int, isLiked: Boolean) {
        viewModelScope.launch {
            _state.update { state ->
                state.copy(
                    courseList = state.courseList.map {
                        if (it.postId == postId) it.copy(isLiked = isLiked) else it
                    }
                )
            }
        }
    }
}