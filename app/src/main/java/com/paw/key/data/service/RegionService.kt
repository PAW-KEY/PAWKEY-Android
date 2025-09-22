package com.paw.key.data.service

import DistrictDataDto
import com.paw.key.data.dto.response.BaseResponse
import com.paw.key.data.dto.response.region.RegionResponseDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface RegionService {
    @GET("regions/{regionId}/geometry")
    suspend fun getRegionGeometry(
        @Header("X-USER-ID") userId: Int,
        @Path("regionId") regionId: Int,
    ): BaseResponse<RegionResponseDto>

    @GET("regions")
    suspend fun getRegionsList(): BaseResponse<DistrictDataDto>
}