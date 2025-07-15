package com.paw.key.data.repositoryimpl.home

import com.paw.key.data.remote.datasource.home.HomeRegionDataSource
import com.paw.key.domain.model.entity.home.HomeRegionDataEntity
import com.paw.key.domain.repository.home.HomeRegionRepository
import javax.inject.Inject

class HomeRegionRepositoryImpl @Inject constructor(
    private val dataSource: HomeRegionDataSource,
) : HomeRegionRepository {

    override suspend fun patchRegion(userId: Int, regionId: Int): Result<HomeRegionDataEntity> {
        return runCatching {
            val response = dataSource.patchRegion(userId, regionId)
            if (response.code == "S000") {
                HomeRegionDataEntity(success = true)
            } else {
                throw Exception(response.message)
            }
        }
    }
}
