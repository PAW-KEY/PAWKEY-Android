package com.paw.key.presentation.ui.signup.model

import android.net.Uri
import androidx.compose.runtime.Immutable
import com.paw.key.presentation.ui.signup.state.Gender

@Immutable
data class SignUpPetInfo(
    val petImage : Uri? = null,
    val petName : String = "",
    val petBirthDate : String = "",
    val petGender : Gender = Gender.UNKNOWN,
    val petNeutered : Boolean = false,
    val petBreed : String = "",
)