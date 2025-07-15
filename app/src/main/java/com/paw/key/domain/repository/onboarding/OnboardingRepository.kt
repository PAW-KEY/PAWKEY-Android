package com.paw.key.domain.repository.onboarding

import com.paw.key.data.dto.response.OnboardingPetsResponse

interface OnboardingRepository {
    suspend fun getOnboardingPets(userId: Int): Result<OnboardingPetsResponse>
}