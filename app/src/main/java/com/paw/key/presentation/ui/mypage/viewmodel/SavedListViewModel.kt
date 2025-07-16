package com.paw.key.presentation.ui.mypage.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paw.key.core.designsystem.component.CourseCard
import com.paw.key.domain.repository.SavedListRepository
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
class SavedListViewModel @Inject constructor(
    private val savedListRepository: SavedListRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SavedListState())
    val state: StateFlow<SavedListState> = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<SavedListSideEffect>()
    val sideEffect: MutableSharedFlow<SavedListSideEffect> = _sideEffect

    fun getSavedList(userId: Int) {
        viewModelScope.launch {
            savedListRepository.getSavedList(userId)
                .onSuccess { result ->
                    Log.d("SavedListViewModel", "저장한 게시물 불러오기 성공: $result")
                    Log.d("SavedListViewModel", "총 ${result.size}개")

                    _sideEffect.emit(SavedListSideEffect.ShowSnackBar("SavedList 불러오기 성공 (${result.size}개)"))

                    _state.update {
                        it.copy(courseList = result)
                    }
                }
                .onFailure { e ->
                    Log.e("SavedListViewModel", "저장한 게시물 불러오기 실패", e)
                    _sideEffect.emit(SavedListSideEffect.ShowSnackBar(e.message ?: "알 수 없는 오류"))
                }
        }
    }
}