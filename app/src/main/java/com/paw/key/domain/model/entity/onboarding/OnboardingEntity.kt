package com.paw.key.domain.model.entity.onboarding

data class OnboardingInfo(
    val userId: Int,
    val userName: String,
    val loginId: String,
    val petId: Int,
    val petName: String
)

data class OnboardingPets(
    val petTraitCategoryList: List<PetTraitCategory>
)

data class PetTraitCategory(
    val petTraitCategoryId: Int,
    val petTraitCategoryName: String,
    val petTraitCategoryOptions: List<PetTraitCategoryOption>
)

data class PetTraitCategoryOption(
    val petTraitCategoryOptionId: Int,
    val petTraitCategoryOptionText: String
)

data class OnboardingRegion(
    val districtList: List<District>
)

data class District(
    val gu: Gu,
    val dongs: List<Dong>
)

data class Gu(
    val id: Int,
    val name: String
)

data class Dong(
    val id: Int,
    val name: String
)

data class PetTraitDto(
    val traitCategoryId: Int,
    val traitOptionId: List<Int>
)
