package com.paw.key.data.remote.datasource

import com.paw.key.data.dto.response.onboarding.OnboardingInfoResponse
import com.paw.key.data.service.PetProfileService
import com.paw.key.data.service.UserProfileService
import com.paw.key.data.service.onboarding.OnboardingInfoService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class UserProfileDataSource @Inject constructor(
    private val userprofileservice: UserProfileService
) {
    suspend fun getUserProfiles(userId: Int) = userprofileservice.getUserProfiles(userId)
}