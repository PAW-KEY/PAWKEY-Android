package com.paw.key.data.dto.response.onboarding

import com.paw.key.domain.model.entity.onboarding.OnboardingInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OnboardingInfoResponse (
    @SerialName("code")
    val code: String,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: OnboardingInfoDataDto
)

@Serializable
data class OnboardingInfoDataDto (
    @SerialName("userId")
    val userId: Int,
    @SerialName("token")
    val token: String
)


fun OnboardingInfoResponse.toDomain(): OnboardingInfo {
    return OnboardingInfo(
        userId = this.data.userId,
        token = this.data.token
    )
}