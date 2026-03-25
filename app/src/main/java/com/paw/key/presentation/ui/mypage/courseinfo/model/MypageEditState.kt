package com.paw.key.presentation.ui.mypage.courseinfo.model

import androidx.compose.runtime.Immutable

@Immutable
data class EditUserState(
    val name: String = "",
    val birth: String = "",
    val gender: String = "",
    val isLoading: Boolean = false,
)

@Immutable
data class EditPetState(
    val name: String = "",
    val birth: String = "",
    val gender: String = "",
    val isNeutered: Boolean = false,
    val breedId: Int = 0,
    val imageId: Int = 0,
    val isLoading: Boolean = false,
)

sealed interface EditSideEffect {
    data class ShowSnackBar(val message: String) : EditSideEffect
    data object NavigateUp : EditSideEffect
}