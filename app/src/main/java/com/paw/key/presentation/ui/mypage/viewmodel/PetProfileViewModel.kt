package com.paw.key.presentation.ui.mypage.viewmodel

import androidx.lifecycle.ViewModel
import com.paw.key.presentation.ui.mypage.state.PetProfileContract
import com.paw.key.presentation.ui.mypage.state.PetProfileContract.PetProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PetProfileViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(PetProfileState())
    val state: StateFlow<PetProfileState>
        get() = _state.asStateFlow() // get할 때마다 업데이트
}