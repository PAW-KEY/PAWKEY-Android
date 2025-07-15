package com.paw.key.presentation.ui.mypage.viewmodel

import androidx.lifecycle.ViewModel
import com.paw.key.presentation.ui.mypage.state.ArchivedListSideEffect
import com.paw.key.presentation.ui.mypage.state.ArchivedListState
import com.paw.key.presentation.ui.mypage.state.MyPageSideEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ArchivedListViewModel @Inject constructor(
    private val archivedListRepository: ArchivedListRepository
) : ViewModel() {
    private val _state = MutableStateFlow(ArchivedListState())
    val state: StateFlow<ArchivedListState>
        get() = _state.asStateFlow() //get할때마다 업데이트

    private val _sideEffect = MutableSharedFlow<ArchivedDetailSideEffect>()
    val sideEffect: MutableSharedFlow<ArchivedDetailSideEffect> = _sideEffect

}