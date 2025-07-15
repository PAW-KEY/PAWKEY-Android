package com.paw.key.presentation.ui.mypage.viewmodel

import androidx.lifecycle.ViewModel
import com.paw.key.presentation.ui.mypage.state.SavedListContract
import com.paw.key.presentation.ui.mypage.state.SavedListContract.SavedListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SavedListViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(SavedListContract.SavedListState())
    val state: StateFlow<SavedListState>
        get() = _state.asStateFlow() //get할때마다 업데이트

}