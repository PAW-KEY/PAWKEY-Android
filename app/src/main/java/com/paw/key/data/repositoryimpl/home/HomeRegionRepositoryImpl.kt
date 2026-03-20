package com.paw.key.data.repositoryimpl.home

import com.paw.key.core.util.suspendRunCatching
import com.paw.key.data.remote.datasource.home.HomeRegionDataSource
import com.paw.key.domain.entity.home.HomeInfoEntity
import com.paw.key.domain.entity.home.HomeRegionDataEntity
import com.paw.key.domain.entity.home.HomeRouteEntity
import com.paw.key.domain.entity.home.HomeWeatherEntity
import com.paw.key.domain.repository.home.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val dataSource: HomeRegionDataSource,
) : HomeRepository {

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

    override suspend fun getHomeInfo(): Result<HomeInfoEntity> = suspendRunCatching {
        dataSource.getHomeInfo().data.toEntity()
    }

    override suspend fun getHomeWeather(): Result<HomeWeatherEntity> = suspendRunCatching {
        dataSource.getHomeWeather().data.toEntity()
    }

    override suspend fun getHomeRecommended(): Result<HomeRouteEntity> = suspendRunCatching {
        dataSource.getHomeRecommended().data.toEntity()
    }
}
