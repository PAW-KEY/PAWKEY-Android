package com.paw.key.data.remote.datasource

import com.paw.key.data.service.UserProfileService
import javax.inject.Inject

class UserProfileDataSource @Inject constructor(
    private val service: UserProfileService
) {
    suspend fun getUserProfiles(userId: Int) = service.getUserProfiles(userId)
}