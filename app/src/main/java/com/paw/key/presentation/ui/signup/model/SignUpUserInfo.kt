package com.paw.key.presentation.ui.signup.model

import androidx.compose.runtime.Immutable
import com.paw.key.presentation.ui.signup.state.Gender

@Immutable
data class SignUpUserInfo(
    val nickName : String = "",
    val birthDate : String = "",
    val gender : Gender = Gender.UNKNOWN,
    val isDuplicate : Boolean = false
)
