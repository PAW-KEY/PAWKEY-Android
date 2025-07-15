package com.paw.key.data.dto.response.onboarding

import com.paw.key.domain.model.entity.onboarding.OnboardingInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OnboardingInfoResponse(
    @SerialName("userId")
    val userId: Int,
    @SerialName("userName")
    val userName: String,
    @SerialName("loginId")
    val loginId: String,
    @SerialName("petId")
    val petId: Int,
    @SerialName("petName")
    val petName: String
)

fun OnboardingInfoResponse.toDomain(): OnboardingInfo {
    return OnboardingInfo(
        userId = this.userId,
        userName = this.userName,
        loginId = this.loginId,
        petId = this.petId,
        petName = this.petName
    )
}