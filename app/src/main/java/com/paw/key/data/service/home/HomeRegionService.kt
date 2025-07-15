package com.paw.key.data.service.home

import com.paw.key.data.dto.request.home.HomeRegionRequest
import com.paw.key.data.dto.response.home.HomeRegionResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PATCH


interface HomeRegionService {
    @PATCH("users/me/regions")
    suspend fun patchRegion(
        @Header("X-USER-ID") userId: Int,
        @Body request: HomeRegionRequest,
    ): HomeRegionResponse<Unit?>

}
