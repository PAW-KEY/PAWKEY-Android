package com.paw.key.domain.repository.userprofile

import com.paw.key.domain.model.entity.uerprofile.UserProfileEntity

interface UserProfileRepository {
    suspend fun getUserProfiles(userId: Int): Result<UserProfileEntity>
}