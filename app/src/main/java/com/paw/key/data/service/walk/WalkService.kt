package com.paw.key.data.service.walk

import com.paw.key.data.dto.request.walk.WalkFinishRequestDto
import com.paw.key.data.dto.request.walk.WalkPointRequestDto
import com.paw.key.data.dto.request.walk.WalkStartRequestDto
import com.paw.key.data.dto.response.BaseResponse
import com.paw.key.data.dto.response.walk.WalkCompleteResponseDto
import com.paw.key.data.dto.response.walk.WalkFinishResponseDto
import com.paw.key.data.dto.response.walk.WalkStartResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface WalkService {
    @POST("walks/stream/start")
    suspend fun startWalk(
        @Body body : WalkStartRequestDto
    ) : BaseResponse<WalkStartResponseDto>

    @POST("walks/stream/point")
    suspend fun pointWalk(
        @Body body : WalkPointRequestDto
    ) : BaseResponse<Unit>

    @POST("routes/{routeId}/finish")
    suspend fun finishWalk(
        @Path("routeId") routeId : String,
        @Body body : WalkFinishRequestDto
    ) : BaseResponse<WalkFinishResponseDto>

    // 산책 완료 후 complete용 좌표
    @GET("routes/{routeId}/geometry")
    suspend fun getRouteGeometry(
        @Path("routeId") routeId : String
    ) : BaseResponse<WalkCompleteResponseDto>


}
