package com.paw.key.presentation.ui.mypage.state

import androidx.compose.runtime.Immutable

object UserProfileContract {
    @Immutable
    data class UserProfileState(
        val id: String = "sgh1261",
        val name: String = "김도기",
        val gender: String = "여성",
        val age: String = "24세",
        val region: String = "강남구 역삼동"
    )
}