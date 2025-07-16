package com.paw.key.data.service.home

import com.paw.key.data.dto.response.BaseResponse
import com.paw.key.data.dto.response.home.RegionCurrentResponseDto
import retrofit2.http.GET
import retrofit2.http.Header

interface RegionCurrentService {
    @GET("regions/current")
    suspend fun RegionCurrent(
        @Header("X-USER-ID") userId: Int
    ):BaseResponse<RegionCurrentResponseDto>
}