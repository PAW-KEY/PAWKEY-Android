package com.paw.key.presentation.ui.mypage.userinfo.model

import androidx.compose.runtime.Immutable

@Immutable
data class UserProfileState(
    val name: String = "",
    val birth: String = "",
    val gender: String = "",
    val age: Int = 0,
    val activeRegion: String = "",
    val isLoading: Boolean = false,
)

sealed interface UserProfileSideEffect {
    data class ShowSnackBar(val message: String) : UserProfileSideEffect
    data object NavigateUp : UserProfileSideEffect
    data object NavigateNext : UserProfileSideEffect
}