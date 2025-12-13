package com.paw.key.presentation.ui.mypage.petinfo.model

import android.net.Uri
import androidx.compose.runtime.Immutable

@Immutable
data class PetProfileState(
    val imageUrl: Uri? = null,
    val name: String = "까루",
    val gender: String = "남아",
    val birthday : String = "2020/01/01",
    val breed: String = "코리안 숏헤어",
    val age: String = "4세",
    val isNeutered: Boolean = true,
    val energyLevel: String = "활동적이에요",
    val socialLevel: String = "불편해해요"
)
sealed class PetProfileSideEffect {
    data class ShowSnackBar(val message: String) : PetProfileSideEffect()
    data object NavigateUp : PetProfileSideEffect()
    data object NavigateNext : PetProfileSideEffect()
}