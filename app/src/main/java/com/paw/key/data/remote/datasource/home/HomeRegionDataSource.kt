package com.paw.key.data.remote.datasource.home

import com.paw.key.data.dto.request.home.HomeRegionRequest
import com.paw.key.data.service.home.HomeRegionService
import javax.inject.Inject

class HomeRegionDataSource @Inject constructor(
    private val service: HomeRegionService
) {
    suspend fun patchRegion(userId: Int, regionId: Int) =
        service.patchRegion(userId, HomeRegionRequest(regionId))
}

