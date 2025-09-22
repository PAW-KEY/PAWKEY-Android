package com.paw.key.presentation.ui.signup.state

import androidx.compose.runtime.Immutable
import com.paw.key.presentation.ui.signup.model.SignUpLocationInfo
import com.paw.key.presentation.ui.signup.model.SignUpMapInfo
import com.paw.key.presentation.ui.signup.model.SignUpPetInfo
import com.paw.key.presentation.ui.signup.model.SignUpUserInfo

@Immutable
data class SignUpState(
    val userInfo: SignUpUserInfo = SignUpUserInfo(),
    val petInfo: SignUpPetInfo = SignUpPetInfo(),
    val locationInfo: SignUpLocationInfo = SignUpLocationInfo(),
    val mapInfo: SignUpMapInfo = SignUpMapInfo(),
    val signUpState: SignUpStateType = SignUpStateType.USER_INFO,
    val currentStep: Float = 1f,
    val isNextEnabled: Boolean = false,
    val isRegionComplete: Boolean = false,
)

sealed class SignUpSideEffect {
    data class ShowSnackBar(val message: String) : SignUpSideEffect()
    data object NavigateUp : SignUpSideEffect()
    data object NavigateNext : SignUpSideEffect()
    data object NavigateHome : SignUpSideEffect()
}

enum class SignUpStateType {
    USER_INFO,
    PET_INFO,
    LOCATION_INFO,
    REGION_MANAGEMENT,
}

enum class Gender {
    MALE,
    FEMALE,
    UNKNOWN
}
