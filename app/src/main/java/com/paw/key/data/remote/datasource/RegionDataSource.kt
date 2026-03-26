package com.paw.key.data.remote.datasource

import com.paw.key.data.service.region.RegionService
import javax.inject.Inject

class RegionDataSource @Inject constructor (
    private val regionService: RegionService
) {
    suspend fun getRegionGeometry(regionId: Int) = regionService.getRegionGeometry(regionId)

    suspend fun getRegionsList() = regionService.getRegionsList()
}