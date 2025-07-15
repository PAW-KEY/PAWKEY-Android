package com.paw.key.data.remote.datasource

import com.paw.key.data.dto.response.onboarding.OnboardingInfoResponse
import com.paw.key.data.service.PetProfileService
import com.paw.key.data.service.onboarding.OnboardingInfoService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class PetProfileDataSource @Inject constructor(
    private val petprofileservice: PetProfileService
) {
    suspend fun getPetProfiles(userId: Int) = petprofileservice.getPetProfiles(userId)
}