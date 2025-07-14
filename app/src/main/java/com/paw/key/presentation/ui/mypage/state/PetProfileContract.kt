package com.paw.key.presentation.ui.mypage.state

import androidx.compose.runtime.Immutable

object PetProfileContract {
    @Immutable
    data class PetProfileState(
        val imageUrl: String? = null,
        val name: String = "까루",
        val gender: String = "남아",
        val breed: String = "코리안 숏헤어",
        val age: String = "4세",
        val energyLevel: String = "활동적이에요",
        val socialLevel: String = "불편해해요"
    )
}