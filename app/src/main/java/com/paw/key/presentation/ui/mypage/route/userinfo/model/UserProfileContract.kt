package com.paw.key.presentation.ui.mypage.route.userinfo.model

import androidx.compose.runtime.Immutable

@Immutable
data class UserProfileState(
    val name: String = "김도기",
    val gender: String = "여성",
    val email: String = "",
    val birth: String = ""
)

sealed class UserProfileSideEffect{
    data class ShowSnackBar(val message: String) : UserProfileSideEffect()
    data object NavigateUp : UserProfileSideEffect()
    data object NavigateNext : UserProfileSideEffect()
}
