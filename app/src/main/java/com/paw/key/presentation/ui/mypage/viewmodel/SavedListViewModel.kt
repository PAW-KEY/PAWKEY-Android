package com.paw.key.presentation.ui.mypage.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paw.key.domain.repository.SavedListRepository
import com.paw.key.presentation.ui.mypage.state.CourseCardData
import com.paw.key.presentation.ui.mypage.state.PetProfileSideEffect
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
import kotlin.onFailure

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
                    Log.d("SavedListViewModel", "SavedList 불러오기 성공: ${result}")
                    Log.d("SavedListViewModel", "SavedList 불러오기 성공: ${result.size}개")
                    _sideEffect.emit(SavedListSideEffect.ShowSnackBar("SavedList 불러오기 성공 (${result.size}개)"))
                    _state.update { it ->
                        it.copy(
                            courseList = result.map { item ->
                                CourseCardData(
                                    petName = item.petName,
                                )
                            }
                        )
                    }
                }
        }
    }
}