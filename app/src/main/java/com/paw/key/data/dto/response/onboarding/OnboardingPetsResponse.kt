package com.paw.key.data.dto.response

import com.paw.key.domain.model.entity.onboarding.OnboardingPets
import com.paw.key.domain.model.entity.onboarding.PetTraitCategory
import com.paw.key.domain.model.entity.onboarding.PetTraitCategoryOption
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OnboardingPetsResponse(
    @SerialName("code")
    val code: String,

    @SerialName("message")
    val message: String,

    @SerialName("data")
    val data: PetTraitCategoryDataDto
)

@Serializable
data class PetTraitCategoryDataDto(
    @SerialName("petTraitCategoryList")
    val petTraitCategoryList: List<PetTraitCategoryDto>
)

@Serializable
data class PetTraitCategoryDto(
    @SerialName("petTraitCategoryId")
    val petTraitCategoryId: Int,

    @SerialName("petTraitCategoryName")
    val petTraitCategoryName: String,

    @SerialName("petTraitCategoryOptions")
    val petTraitCategoryOptions: List<PetTraitCategoryOptionDto>
)

@Serializable
data class PetTraitCategoryOptionDto(
    @SerialName("petTraitCategoryOptionId")
    val petTraitCategoryOptionId: Int,

    @SerialName("petTraitCategoryOptionText")
    val petTraitCategoryOptionText: String
)


fun OnboardingPetsResponse.toDomain(): OnboardingPets {
    return OnboardingPets(
        petTraitCategoryList = this.data.petTraitCategoryList.map { it.toDomain() }
    )
}

fun PetTraitCategoryDto.toDomain(): PetTraitCategory {
    return PetTraitCategory(
        petTraitCategoryId = this.petTraitCategoryId,
        petTraitCategoryName = this.petTraitCategoryName,
        petTraitCategoryOptions = this.petTraitCategoryOptions.map { it.toDomain() }
    )
}

fun PetTraitCategoryOptionDto.toDomain(): PetTraitCategoryOption {
    return PetTraitCategoryOption(
        petTraitCategoryOptionId = this.petTraitCategoryOptionId,
        petTraitCategoryOptionText = this.petTraitCategoryOptionText
    )
}