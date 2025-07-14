package com.paw.key.data.dto.request.onboarding

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OnboardingInfoRequest(
    @SerialName("loginId")
    val loginId: String,
    @SerialName("password")
    val password: String,
    @SerialName("name")
    val name: String,
    @SerialName("gender")
    val gender: String,
    @SerialName("age")
    val age: Int,
    @SerialName("regionId")
    val regionId: Int,
    @SerialName("pet")
    val pet: PetInfoDto
)

@Serializable
data class PetInfoDto(
    @SerialName("name")
    val name: String,
    @SerialName("gender")
    val gender: String,
    @SerialName("age")
    val age: Int,
    @SerialName("isAgeKnown")
    val isAgeKnown: Boolean,
    @SerialName("isNeutered")
    val isNeutered: Boolean,
    @SerialName("breed")
    val breed: String,
    @SerialName("petTraits")
    val petTraits: List<PetTraitDto>
)

@Serializable
data class PetTraitDto(
    @SerialName("traitCategoryId")
    val traitCategoryId: Int,
    @SerialName("traitOptionId")
    val traitOptionId: Int
)
