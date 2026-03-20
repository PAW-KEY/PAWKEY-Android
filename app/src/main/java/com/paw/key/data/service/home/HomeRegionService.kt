package com.paw.key.data.service.home

import com.paw.key.data.dto.request.home.HomeRegionRequest
import com.paw.key.data.dto.response.BaseResponse
import com.paw.key.data.dto.response.home.HomeInfoResponseDto
import com.paw.key.data.dto.response.home.HomeRouteResponseDto
import com.paw.key.data.dto.response.home.HomeWeatherResponseDto
import com.paw.key.data.dto.response.home.RegionCurrentResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH


interface HomeRegionService {
    @PATCH("users/me/regions")
    suspend fun patchRegion(
        @Header("X-USER-ID") userId: Int,
        @Body request: HomeRegionRequest,
    ): BaseResponse<Unit?>

    @GET("regions/current")
    suspend fun regionCurrent(
        @Header("X-USER-ID") userId: Int
    ): BaseResponse<RegionCurrentResponseDto>

    @GET("home/info")
    suspend fun getHomeInfo(

    ): BaseResponse<HomeInfoResponseDto>

    @GET("home/weather")
    suspend fun getHomeWeather(

    ): BaseResponse<HomeWeatherResponseDto>

    @GET("home/recommendation")
    suspend fun getHomeRecommended(

    ): BaseResponse<HomeRouteResponseDto>
}
