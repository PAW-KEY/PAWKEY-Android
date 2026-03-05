package com.paw.key.presentation.ui.mypage.main.model

import androidx.compose.runtime.Immutable
import com.paw.key.presentation.ui.mypage.model.PetInfoModel

@Immutable
data class MyPageState(
    val ownerName: String = "김도기님",

    val petInfo : PetInfoModel = PetInfoModel(),
    val petTags: List<String> = listOf("조금 느긋해요", "#오토바이소리", "#대형견"),
    val walkCount: Int = 0,
    val totalDistance: String = "14km"
)

sealed interface MyPageSideEffect {
    data class ShowSnackBar(val message: String) : MyPageSideEffect
    data object NavigateUp : MyPageSideEffect
    data object NavigateNext : MyPageSideEffect
    data object NavigateToLogin : MyPageSideEffect
}
