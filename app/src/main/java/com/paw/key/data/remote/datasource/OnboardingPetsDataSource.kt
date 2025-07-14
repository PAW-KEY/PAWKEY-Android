package com.paw.key.data.remote.datasource

import com.paw.key.data.service.OnboardingPetsService
import javax.inject.Inject

class OnboardingPetsDataSource @Inject constructor(
    private val service: OnboardingPetsService
) {
    suspend fun getOnboardingPets(userId: Int) = service.getPetsCaegories(userId)
}
