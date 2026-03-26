package com.paw.key.presentation.ui.mypage.route.petinfo.model

import android.net.Uri
import androidx.compose.runtime.Immutable
import com.paw.key.presentation.ui.signup.state.Gender

@Immutable
data class PetProfileState(
    val name: String = "",
    val birthday: String = "",
    val gender: Gender = Gender.MALE,
    val isNeutered: Boolean = false,
    val breed: String = "",
    val breedId: Int = 0,
    val imageUrl: Uri? = null,
    val imageId: Int = 0,
    val age: String = "",
    val energyLevel: String = "",
    val socialLevel: String = "",
    val isLoading: Boolean = false,
)

sealed interface PetProfileSideEffect {
    data class ShowSnackBar(val message: String) : PetProfileSideEffect
    data object NavigateUp : PetProfileSideEffect
}