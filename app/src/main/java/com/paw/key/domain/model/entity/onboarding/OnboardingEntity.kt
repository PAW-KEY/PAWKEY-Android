package com.paw.key.domain.model.entity.onboarding

// 온보딩에서 사용하는 사용자 및 반려견 정보
data class OnboardingInfo(
    val userId: Int,
    val token: String
)

// 반려견 성향 정보
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

// 지역 정보 (구, 동 포함)
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

data class DistrictResponse(
    val code: String,
    val message: String,
    val data: OnboardingRegion
)
