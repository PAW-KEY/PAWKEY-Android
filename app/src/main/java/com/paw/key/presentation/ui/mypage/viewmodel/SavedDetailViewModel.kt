package com.paw.key.presentation.ui.mypage.viewmodel

import androidx.lifecycle.ViewModel
import com.paw.key.presentation.ui.mypage.state.SavedDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SavedDetailViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(SavedDetailState())
    val state: StateFlow<SavedDetailState>
        get() = _state.asStateFlow()
}