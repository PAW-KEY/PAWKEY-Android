package com.paw.key.data.service.region

import com.paw.key.data.dto.request.region.RegionRequestDto
import com.paw.key.data.dto.response.BaseResponse
import com.paw.key.data.dto.response.region.DistrictDataDto
import com.paw.key.data.dto.response.region.RegionResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface RegionService {
    @GET("regions/{regionId}/geometry")
    suspend fun getRegionGeometry(
        @Path("regionId") regionId: Int,
    ): BaseResponse<RegionResponseDto>

    @GET("regions")
    suspend fun getRegionsList(): BaseResponse<DistrictDataDto>

    @PATCH("users/me/regions")
    suspend fun patchUserRegions(
        @Body body : RegionRequestDto
    ) : Response<Unit>
}