package com.paw.key.data.service.region

import com.paw.key.data.dto.response.BaseResponse
import com.paw.key.data.dto.response.region.DistrictDataDto
import com.paw.key.data.dto.response.region.RegionResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RegionService {
    @GET("regions/{regionId}/geometry")
    suspend fun getRegionGeometry(
        @Query("userId") userId: Int,
        @Path("regionId") regionId: Int,
    ): BaseResponse<RegionResponseDto>

    @GET("regions")
    suspend fun getRegionsList(): BaseResponse<DistrictDataDto>
}