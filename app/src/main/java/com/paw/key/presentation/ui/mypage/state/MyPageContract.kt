package com.paw.key.presentation.ui.mypage.state

import androidx.compose.runtime.Immutable

    @Immutable
    data class MyPageState(
        val ownerName: String = "김도기님",
        val petName: String = "포비",
        val petAge: String = "12세",
        val petGender: String = "여아",
        val petImageUrl: String = "",
        val petTags: List<String> = listOf("조금 느긋해요", "#오토바이소리", "#대형견"),
        val walkCount: String = "7회",
        val totalDistance: String = "14km"
    )

sealed class MyPageSideEffect {
    data class ShowSnackBar(val message: String) : MyPageSideEffect()
    data object NavigateUp : MyPageSideEffect()
    data object NavigateNext : MyPageSideEffect()
}
