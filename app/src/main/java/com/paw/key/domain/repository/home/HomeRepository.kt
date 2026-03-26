package com.paw.key.domain.repository.home

import com.paw.key.domain.entity.home.HomeInfoEntity
import com.paw.key.domain.entity.home.HomeRegionDataEntity
import com.paw.key.domain.entity.home.HomeRouteEntity
import com.paw.key.domain.entity.home.HomeWeatherEntity

interface HomeRepository {
    suspend fun patchRegion(userId: Int, regionId: Int): Result<HomeRegionDataEntity>

    suspend fun getHomeInfo(): Result<HomeInfoEntity>

    suspend fun getHomeWeather(): Result<HomeWeatherEntity>

    suspend fun getHomeRecommended(): Result<HomeRouteEntity>
}
