package com.paw.key.data.repositoryimpl.sharedwalk

import com.paw.key.data.remote.datasource.sharedwalk.SharedWalkDataSource
import com.paw.key.domain.model.entity.region.RegionDataEntity
import com.paw.key.domain.model.entity.sharedwalk.SharedWalkEntity
import com.paw.key.domain.repository.sharedwalk.SharedWalkRepository
import javax.inject.Inject

class SharedWalkRepositoryImpl @Inject constructor(
    private val sharedWalkDataSource: SharedWalkDataSource
) : SharedWalkRepository {
    override suspend fun getSharedWalkTrack(userId: Int, routeId: Int) : Result<SharedWalkEntity> = runCatching {
        sharedWalkDataSource.getSharedWalkTrack(userId, routeId).data.toEntity()
    }
}