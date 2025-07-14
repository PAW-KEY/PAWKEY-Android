package com.paw.key.presentation.ui.mypage.viewmodel

import androidx.lifecycle.ViewModel
import com.paw.key.presentation.ui.mypage.state.ArchivedDetailContract.ArchivedDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ArchivedDetailViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(
        ArchivedDetailState(
            title = "한강 산책로",
            petName = "후추",
            date = "2025/06/02",
            location = "뚝섬유원지",
            distance = "4.5km",
            time = "1시간 30분 소요",
            option = listOf("풍경이 좋아요", "조용해요", "길이 깨끗해요"),
            imageUrl = "https://pawkey-server.com/image.jpg"
        )
    )
    val state: StateFlow<ArchivedDetailState> get() = _state.asStateFlow()
}