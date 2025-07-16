package com.paw.key.data.repositoryimpl

import com.paw.key.data.remote.datasource.UserProfileDataSource
import com.paw.key.domain.model.entity.uerprofile.UserProfileEntity
import com.paw.key.domain.repository.userprofile.UserProfileRepository
import javax.inject.Inject

class UserProfileRepositoryImpl @Inject constructor(
    private val dataSource: UserProfileDataSource,
) : UserProfileRepository {

    override suspend fun getUserProfiles(userId: Int): Result<UserProfileEntity> = runCatching {
        dataSource.getUserProfiles(userId).data.toEntity()
    }
}