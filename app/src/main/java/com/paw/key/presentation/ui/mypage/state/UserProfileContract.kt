package com.paw.key.presentation.ui.mypage.state

import androidx.compose.runtime.Immutable

@Immutable
data class UserProfileState(
    val name: String = "김도기",
    val gender: String = "여성",
    val age: Int = 24,
    val activeRegion: String = "강남구 역삼동"
)

sealed class UserProfileSideEffect{
    data class ShowSnackBar(val message: String) : UserProfileSideEffect()
    data object NavigateUp : UserProfileSideEffect()
    data object NavigateNext : UserProfileSideEffect()
}