package com.paw.key.data.remote.datasource

import com.paw.key.data.service.UserProfileService
import javax.inject.Inject

class UserProfileDataSource @Inject constructor(
    private val userprofileservice: UserProfileService
) {
    suspend fun getUserProfiles(userId: Int) = userprofileservice.getUserProfiles(userId)
}